package com.goodbyeq.authorization.framework;

import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

import com.goodbyeq.authorize.api.SecureHashCheckCommand;
import com.goodbyeq.authorize.api.SecureHashCommand;
import com.goodbyeq.exception.JceSecureHashException;

/**
 * This class provides an easy to use wrapper around JCE, the JavaTM
 * Cryptography Extension, message digests for secure hashing.
 */

public class SecureHasher implements SecureHashCommand, SecureHashCheckCommand {

	private static final String DEFAULT_ALGORITHM = "SHA-256"; // SHA-2 256-bit hash
	private static final String DEFAULT_ALGORITHM_ID = "5";
	private static final int DEFAULT_SALT_LENGTH = 8;
	private static final int DEFAULT_ITERATIONS = 64000;

	/**
	 * Output encrypted strings as hexadecimal. This is the default.
	 */
	public static final String StringOutputType_Hexadecimal = "hexadecimal";
	/**
	 * Output encrypted strings as base64.
	 */
	public static final String StringOutputType_Base64 = "base64";

	private String provider;
	private String algorithm;
	private String algorithmID;
	private int iterations;
	private String salt;
	private int saltLength;
	private String stringOutputType;
	private transient MessageDigest digester;
	private transient SecureRandom random;

	/**
	 * This default constructor does not initialize the instance, and will require
	 * the caller to explicitly set properties (e.g. setAlgorithm() ;
	 * setAlgorithmID() ; ...) and then to call init() prior to using the hashign
	 * for hash/check() ops.
	 */
	public SecureHasher() {
		super();
		setAlgorithm(DEFAULT_ALGORITHM);
		setStringOutputType(StringOutputType_Hexadecimal);
	}

	/**
	 * This constructor initializes the instance with the given algorithm.
	 */
	public SecureHasher(final String algorithm) throws Exception {
		this(null, algorithm, null);
	}

	/**
	 * This constructor initializes the instance with the given algorithm, provider
	 * and algorithm ID.
	 */
	public SecureHasher(final String provider, final String algorithm, final String algorithmID) throws Exception {
		this();
		init(provider, algorithm, algorithmID);
	}

	private static String getDefaultAlgorithmID(final String algorithm) {
		final String result;
		if (algorithm == null || algorithm.length() == 0 || algorithm.equals(DEFAULT_ALGORITHM)) {
			result = DEFAULT_ALGORITHM_ID;
		} else if (algorithm.equals("MD5")) {
			result = "1";
		} else {
			result = "0";
		}
		return result;
	}

	private static int getDefaultSaltLength(final String algorithm) {
		final int result = DEFAULT_SALT_LENGTH;
		return result;
	}

	private static int getDefaultIterations(final String algorithm) {
		final int result = DEFAULT_ITERATIONS;
		return result;
	}

	/**
	 * The default engine: A 256-bit SHA-2 digest.
	 */
	public static SecureHasher getDefaultEngine() throws Exception {
		final SecureHasher result = new SecureHasher(DEFAULT_ALGORITHM);
		return result;
	}

	/**
	 * Get an engine by ID.
	 */
	public static SecureHasher getEngine(final String algorithmID) throws Exception {
		final SecureHasher result;
		if (algorithmID == null || algorithmID.length() == 0 || algorithmID.equals(DEFAULT_ALGORITHM_ID)) {
			result = getDefaultEngine();
		} else if (algorithmID.equals("1")) {
			result = new SecureHasher("MD5");
		} else {
			throw new Exception("Unknown algorithm ID: " + algorithmID);
		}
		return result;
	}

	/**
	 * Re-initialize the digest engine with new parameters.
	 * 
	 * @throws Exception
	 *             if something goes wrong initializing.
	 */
	public void init(final String jceProvider, final String algoName, final String algoID) throws Exception {
		setProvider(jceProvider);
		setAlgorithm(algoName);
		setAlgorithmID(algoID);
		setIterations(getDefaultIterations(algoName));
		setSaltLength(getDefaultSaltLength(algoName));
		init();
	}

	/**
	 * Initialize (or re-initialize with new parameters) the digest engine.
	 * 
	 * @throws Exception
	 *             if something goes wrong initializing.
	 */
	public void init() throws Exception {
		try {
			if (provider == null) {
				digester = MessageDigest.getInstance(algorithm);
			} else {
				digester = MessageDigest.getInstance(algorithm, provider);
			}
			if (random == null) // no need to recreate
				random = new SecureRandom();
		} catch (final GeneralSecurityException e) {
			throw new Exception(
					"Problem while initializing JceSecureHasher: provider: " + provider + " algorithm: " + algorithm,
					e);
		}
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(final String provider) {
		this.provider = provider;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(final String algorithm) {
		this.algorithm = algorithm;
	}

	public String getAlgorithmID() {
		return algorithmID;
	}

	public void setAlgorithmID(final String algorithmID) {
		if (algorithmID == null || algorithmID.length() == 0) {
			this.algorithmID = getDefaultAlgorithmID(algorithm);
		} else {
			this.algorithmID = algorithmID;
		}
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(final int iterations) {
		if (iterations == 0)
			this.iterations = getDefaultIterations(algorithm);
		else
			this.iterations = iterations;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(final String salt) throws Exception {
		this.salt = salt;
	}

	public int getSaltLength() {
		return saltLength;
	}

	public void setSaltLength(final int saltLength) {
		if (saltLength == 0)
			this.saltLength = getDefaultSaltLength(algorithm);
		else
			this.saltLength = saltLength;
	}

	public String getStringOutputType() {
		return stringOutputType;
	}

	public void setStringOutputType(final String stringOutputType) {
		this.stringOutputType = stringOutputType;
	}

	/**
	 * Generate hash for the secret
	 */
	@Override
	public String hash(final String secret) throws JceSecureHashException {
		if (random == null)
			throw new JceSecureHashException("SecureHash Engine has not been initialized");
		final byte[] saltBytes;
		String result = null;
		if (salt == null) {
			saltBytes = new byte[saltLength];
			random.nextBytes(saltBytes);
		}else {
			saltBytes=salt.getBytes(Charset.forName("UTF-8"));
		}
		result = hash(saltBytes, secret);
		return result;
	}

	/**
	 * Generate hash for the secret
	 */
	private String hash(final byte[] saltBytes, final String secret) throws JceSecureHashException {
		if (digester == null)
			throw new JceSecureHashException("SecureHash Engine has not been initialized");
		final String result;
		try {
			final byte[] secretBytes = (secret == null ? new byte[0] : secret.getBytes(Charset.forName("UTF-8")));
			byte[] bytes = new byte[saltBytes.length + secretBytes.length];
			System.arraycopy(saltBytes, 0, bytes, 0, saltBytes.length);
			System.arraycopy(secretBytes, 0, bytes, saltBytes.length, secretBytes.length);
			for (int i = 0; i < iterations; i++) {
				digester.update(bytes);
				bytes = digester.digest();
			}
			final byte[] raw = bytes;
			result = "$" + algorithmID + "$" + Base64.getEncoder().encodeToString(saltBytes) + "$"
					+ Base64.getEncoder().encodeToString(raw);
		} catch (final Exception e) {
			throw new JceSecureHashException("Problem hashing secret", e);
		}
		return result;
	}

	/**
	 * Check hash value with the secret sent
	 */
	@Override
	public boolean check(final String secret, final String hash) throws JceSecureHashException {
		if (digester == null)
			throw new JceSecureHashException("SecureHash Engine has not been initialized");
		final boolean result;
		try {
			final String[] parts = hash.split("\\$");
			if (parts.length != 4)
				throw new JceSecureHashException("Unable to parse hash: " + hash);
			final SecureHasher hasher = parts[1].equals(algorithmID) ? this : getEngine(algorithmID);
			final byte[] saltBytes = Base64.getDecoder().decode(parts[2]);
			final String hashCheck = hasher.hash(saltBytes, secret);
			result = hashCheck.equals(hash);
		} catch (final Exception e) {
			throw new JceSecureHashException("Unable to check secret against hash: " + hash, e);
		}
		return result;
	}

}
