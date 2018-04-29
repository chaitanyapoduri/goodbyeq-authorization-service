package com.goodbyeq.rest.api; 

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.codehaus.jackson.JsonEncoding;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.type.CollectionType;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;


public class BaseJsonHelper {
	private static final Logger logger = LoggerFactory.getLogger(BaseJsonHelper.class);

    public static final String SUCCESS = "200";

    public static final String FAILURE = "500";

    protected static final ObjectMapper objectMapper = new ObjectMapper();
    protected static final JsonEncoding encoding = JsonEncoding.UTF8;

    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    public static JsonEncoding getEncoding() {
        return encoding;
    }

    /**
     * serialize
     * @param o
     * @return
     */
    public static String getJsonString(final Object o) {
        try {
            final StringWriter w = new StringWriter();
            objectMapper.writeValue(w, o);
            return w.toString();
        } catch (final IOException e) {
            logger.error("getJsonString() failed:", e);
            return null;

        }
    }

    public static String getJsonStringPretty(final Object o) {
        try {
            final StringWriter w = new StringWriter();
            objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, true);
            objectMapper.writeValue(w, o);
            objectMapper.configure(SerializationConfig.Feature.INDENT_OUTPUT, false);
            return w.toString();
        } catch (final IOException e) {
            logger.error("getJsonStringPretty() failed:", e);
            return null;

        }
    }

    /**
     * Deserialize the JSON string into object of type T
     * 
     * @param s the JSON string
     * @param c the class type.
     * @param <T>
     * @return
     */
    public static <T> T fromJsonString(final String s, final Class<T> c) {
        try {
            return objectMapper.readValue(s, c);

        } catch (final IOException e) {
            logger.error("fromJsonString() failed:", e);
            return null;

        }
    }

    /**
     * Deserialize the json inputstream data into object of type T
     *
     * @param in imput stream
     * @param c the class type.
     * @param <T>
     * @return
     */
    public static <T> T fromJsonInputStream(final InputStream in, final Class<T> c) {
        try {
            return objectMapper.readValue(in, c);

        } catch (final IOException e) {
            logger.error("fromJsonString() failed:", e);
            return null;

        }
    }
    
    /**
     * Deserializes the JSON string into an object of the type indicated by the provided type refernce.
     * 
     * @param s The JSON string to be deserialized
     * @param typeReference Indicates the type into which the JSON string is to be deserialized
     * @return An instance of the specified type representing the JSON string
     */
    public static <T> T fromJsonString(final String s, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(s, typeReference);

        } catch (final IOException e) {
            logger.error("fromJsonString() failed:", e);
            return null;
        }
    }

    /**
     * Deserializes the JSON string into a list of objects of the specified custom type.
     * 
     * @param s The JSON string to be deserialized
     * @param c The type of the items in the list
     * @return A list of objects of the specified custom type
     */
    public static <T> List<T> fromJsonStringToList(final String s, final Class<T> c) {
        try {
        	final CollectionType javaType =  objectMapper.getTypeFactory().constructCollectionType(List.class, c);
        	
            return objectMapper.readValue(s, javaType);

        } catch (final IOException e) {
            logger.error("fromJsonStringToListOf() failed:", e);
            return null;
        }
    }

    public void handleException (final Exception e, final HttpServletResponse response) throws IOException {
        StringBuilder b = new StringBuilder();
        Throwable internalE = e;
        while ( internalE!=null ) {
            if (b.length()>0)
                b.append("\n");
            b.append(internalE.getMessage());
            if ( internalE.getCause()==null || internalE==internalE.getCause())
                break;
            internalE = internalE.getCause();
        }
        handleException(b.toString(), response);
    }

    /**
     * Return RestOperationStatusVOX in the JSON format
     * 
     * @param errorMessage the error message to be added to the response.
     * @param response
     * @throws IOException
     */
    public void handleException(final String errorMessage, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(BaseJsonHelper.getEncoding().getJavaName());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        final RestOperationStatusVOX statusVOX = new RestOperationStatusVOX();
        statusVOX.setStatus(RestOperationStatusVOX.FAILURE_OPERATION);

        final List<JsonErrorVOX> jasonErrors = new ArrayList<JsonErrorVOX>();
        jasonErrors.add(new JsonErrorVOX(FAILURE, errorMessage));
        statusVOX.setErrors(jasonErrors);

        final JsonGenerator generator = getObjectMapper().getJsonFactory().createJsonGenerator(response.getOutputStream(), BaseJsonHelper.getEncoding());
        getObjectMapper().writeValue(generator, statusVOX);
    }

    /**
     * return RestOperationStatusVOX in the JSON format
     * @param statusVOX pass in the RestOperationStatusVOX
     * @param response
     * @throws IOException
     */
    public void handleException(final RestOperationStatusVOX statusVOX, final HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding(BaseJsonHelper.getEncoding().getJavaName());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());

        statusVOX.setStatus(RestOperationStatusVOX.FAILURE_OPERATION);

        final JsonGenerator generator = getObjectMapper().getJsonFactory().createJsonGenerator(response.getOutputStream(), BaseJsonHelper.getEncoding());
        getObjectMapper().writeValue(generator, statusVOX);
    }

}
