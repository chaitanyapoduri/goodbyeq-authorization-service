package com.goodbyeq.authorization.framework;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.util.CollectionUtils;

import com.goodbyeq.authoriation.api.impl.JWTTokenValidator;
import com.goodbyeq.authorize.api.SecretService;
import com.goodbyeq.authorize.api.TokenValidator;
import com.goodbyeq.exception.GBQTokenException;
import com.goodbyeq.exception.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@EnableWebSecurity
public class OAuthTokenAuthenticationProcessingFilter extends OAuth2AuthenticationProcessingFilter {

	private static final Logger logger = LoggerFactory.getLogger(OAuthTokenAuthenticationProcessingFilter.class);

	public static final String defaultFilterProcessesUrl = "/api/";

	@Value("${filter.escape.Urls}")
	private Map<String, String> filterProcessPathExclusionMap;

	@Autowired
	private GBQAuthenticationProvider authenticationProvider;

	@Autowired(required = true)
	private SecretService secretService;

	private List<TokenValidator> tokenValidators;

	private String[] filterProcessURLs = { defaultFilterProcessesUrl };

	public Map<String, String> getFilterProcessPathExclusionMap() {
		if (null == filterProcessPathExclusionMap) {
			filterProcessPathExclusionMap = new HashMap<String, String>();
			filterProcessPathExclusionMap.put("add", "add");
			filterProcessPathExclusionMap.put("create", "create");
		}
		return filterProcessPathExclusionMap;
	}

	@Autowired
	public void setFilterProcessPathExclusionMap(Map<String, String> filterProcessPathExclusionMap) {
		this.filterProcessPathExclusionMap = filterProcessPathExclusionMap;
	}

	public GBQAuthenticationProvider getAuthenticationProvider() {
		if (null == authenticationProvider) {
			authenticationProvider = BeanUtil.getBean(GBQAuthenticationProvider.class);
		}
		return authenticationProvider;
	}

	public void setAuthenticationProvider(GBQAuthenticationProvider authenticationProvider) {
		this.authenticationProvider = authenticationProvider;
	}

	public List<TokenValidator> getTokenValidators() {
		if (null == tokenValidators) {
			tokenValidators = new ArrayList<TokenValidator>();
			tokenValidators.add(BeanUtil.getBean(JWTTokenValidator.class));
		}
		return tokenValidators;
	}

	public void setTokenValidators(List<TokenValidator> tokenValidators) {
		this.tokenValidators = tokenValidators;
	}

	protected boolean shouldFilterOn(final HttpServletRequest request) {
		boolean result = false;
		final String servletPath = request.getServletPath();
		for (final String filterProcessURL : filterProcessURLs) {
			result = servletPath.startsWith(filterProcessURL)
					&& !doesRequestPathInExclusionMap(servletPath, request.getPathInfo());
			if (result) {
				break;
			}
		}
		return result;
	}

	private boolean doesRequestPathInExclusionMap(final String servletPath, final String requestPath) {
		final String exclusionURLs = getFilterProcessPathExclusionMap().get(servletPath);
		if (exclusionURLs == null || exclusionURLs.isEmpty())
			return false;

		final String[] pathes = exclusionURLs.split(";");
		for (final String eachPath : pathes) {
			if (requestPath.startsWith(eachPath))
				return true;
		}
		return false;
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException, JwtTokenMissingException {
		String header = request.getHeader("Authorization");
		if (header == null || !header.startsWith("Bearer ")) {
			throw new JwtTokenMissingException("No JWT token found in request headers");
		}
		String authToken = header.substring(7);
		Jws<Claims> claims = Jwts.parser().setSigningKey(BeanUtil.getBean(SecretService.class).getHS256SecretBytes())
				.parseClaimsJws(authToken);
		Claims claim = claims.getBody();
		final Authentication authentication = new UsernamePasswordAuthenticationToken(claim.getSubject(),
				claim.get("password"));
		Authentication auth = getAuthenticationProvider().authenticate(authentication);
		return auth;
	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res, final FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		boolean checkTokenFilter = shouldFilterOn(request);
		boolean isTokenValid = false;
		if (checkTokenFilter && !CollectionUtils.isEmpty(getTokenValidators())) {
			try {
				for (TokenValidator validator : getTokenValidators()) {
					isTokenValid = validator.validateToken(request.getRequestURI(), request.getHeader("Authorization"));
				}
				// Authenticate token
				if (checkTokenFilter && isTokenValid) {
					Authentication auth = attemptAuthentication(request, response);
					SecurityContextHolder.getContext().setAuthentication(auth);
				}
			} catch (JwtTokenMissingException | GBQTokenException | AuthenticationException ex) {
				// authentication failed, log the exception and continue processing the request
				throw new ServletException(ex);
			}
		}
		// Continue processing request
		chain.doFilter(request, response);

	}
}
