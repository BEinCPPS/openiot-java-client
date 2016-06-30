package com.fincons.rabbitmq.event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.fincons.rabbitmq.client.factory.RequestFactory;


/**
 * Provides an abstract implementation of the {@link Event} interface because the
 * construction of an Event instance by publishers and subscribers is different 
 *
 * @author Fincons Group AG
 *
 */
public abstract class AbstractEvent implements Event {
    
	final static Logger logger = Logger.getLogger(AbstractEvent.class);
	
    private final Map<String,Object> headers;
    private final byte[] payload;
    private final String contentType;
    private final String contentEncoding;
    private final int priority;
    private final Date timestamp;
    private final String applicationID;
    private final boolean isPersistent;
    private final String pattern;
    
    /**
     * Sets all fields with the given parameters.
     * 
     * @param pattern
     * @param headers
     * @param payload
     * @param contentType
     * @param contentEncoding
     * @param priority
     * @param timestamp
     * @param isPersistent
     * @param applicationID
     * @throws IllegalArgumentException if at least one parameter is invalid.
     * @see #validate(String, Map, byte[], String, String, int, Date, boolean, String)
     */
    protected AbstractEvent (String pattern, Map<String, Object> headers, byte[] payload, String contentType,
            String contentEncoding, int priority, Date timestamp, boolean isPersistent, String applicationID) throws IllegalArgumentException {
    	
		logger.info("Calling the AbstractEvent constructor...");
    	
        this.headers = headers;
        this.payload = payload;
        this.contentType = contentType;
        this.contentEncoding = contentEncoding;
        this.priority = priority;
        this.timestamp = timestamp;
        this.applicationID = applicationID;
        this.isPersistent = isPersistent;
        this.pattern = pattern;
    }
    
    /**
     * Validates the given parameters.<br/> This method is called by 
     * {@link #AbstractENSEvent(String, Map, byte[], String, String, int, Date, boolean, String)}
     * before setting the fields with the parameters.
     * 
     * @param pattern
     * @param headers
     * @param payload
     * @param contentType
     * @param contentEncoding
     * @param priority
     * @param timestamp
     * @param isPersistent
     * @param applicationID
     * @throws IllegalArgumentException if at least one parameter is invalid.
     */
    protected abstract void validate (String pattern, Map<String, Object> headers, byte[] payload, String contentType,
            String contentEncoding, int priority, Date timestamp, boolean isPersistent, String applicationID) 
    throws IllegalArgumentException;

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getHeaders()
     */
    public Map<String, Object> getHeaders() {
        return headers;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getPayload()
     */
    public byte[] getPayload() {
        return payload;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getContentType()
     */
    public String getContentType() {
        return contentType;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getContentEncoding()
     */
    public String getContentEncoding() {
        return contentEncoding;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getPriority()
     */
    public int getPriority() {
        return priority;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getTimestamp()
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getApplicationID()
     */
    public String getApplicationID() {
        return applicationID;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#isPersistent()
     */
    public boolean isPersistent() {
        return isPersistent;
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.Event#getPattern()
     */
    public String getPattern() {
        return pattern;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ENS Event: {");
        builder.append("timestamp: ");
        if (timestamp != null) {
            builder.append(new SimpleDateFormat(DATE_TIME_FORMAT).format(timestamp));
        } else
            builder.append((String)null);
        builder.append("\", applicationID: \"");
        builder.append(applicationID);
        builder.append("\", contentType: \"");
        builder.append(contentType);
        builder.append("\", contentEncoding: \"");
        builder.append(contentEncoding);
        builder.append("\", pattern: \"");
        builder.append(pattern);
        builder.append("\", priority: ");
        builder.append(priority);
        builder.append(", ");
        if (payload == null) {
            builder.append("body: null");
        } else {
            builder.append("bodySize: ");
            builder.append(payload.length);
            builder.append(" byte");
        }
        
        builder.append(", headers: ");
        if (headers == null) {
            builder.append("null");
        } else {
            builder.append("{");
            boolean first = true;
            for (Entry<String,Object> entry : headers.entrySet()) {
                if (first)
                    first = false;
                else
                    builder.append(",");
                builder.append(entry.getKey());
                builder.append(": \"");
                builder.append(entry.getValue());
                builder.append("\"");
            }
            builder.append("}");
        }
        builder.append("}");
        return builder.toString();
    }
}