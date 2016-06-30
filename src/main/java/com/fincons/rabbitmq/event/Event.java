package com.fincons.rabbitmq.event;

import java.util.Date;
import java.util.Map;

/**
 * This interface represents an event dispatched by the RabbitMQ Server.<br/>
 * An Event is made up by the following parts:
 * <ul><li><b>headers</b> - a set of (key, value) pairs the publisher can use to
 * indicate some data useful enabling subscribers to correctly understand the payload
 * of the event and/or to put the informative content of the event</li>
 * <li><b>payload</b> - an opaque list of bytes that represent the informative content of the event.
 * NOTE: the informative content carried by an event may be put in the headers hence the message
 * payload may be empty</li></ul>
 * 
 * @author Fincons Group AG
 *
 */
public interface Event {
    
	/**
	 * The default MIME type of the event payload.
	 */
    public static final String DEFAULT_CONTENT_TYPE = "text/plain";
    
    /**
     * The default encoding of the event payload.
     */
    public static final String DEFAULT_CONTENT_ENCODING = "ISO-8859-1";
    
    /**
     * The default event priority (no priority).
     */
    public static final int DEFAULT_PRIORITY = 0;
    
    /**
     * Format of the string representation of date time values.
     */
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS Z";
    
    /**
     * Returns the event headers.
     * 
     * @return the event headers.
     */
    public Map<String,Object> getHeaders ();
    
    /**
     * Returns the informative content of the event (i.e. its payload).
     * 
     * @return the event payload.
     */
    public byte[] getPayload ();
    
    /**
     * Returns the <a href="http://www.iana.org/assignments/media-types/index.html">media type</a> of the event payload.<br/>
     * 
     * @return IANA's media type of the event payload.
     */
    public String getContentType ();

    /**
     * Returns the type of encoding used on the event payload.
     * 
     * @return event payload's encoding.
     */
    public String getContentEncoding ();
    
    /**
     * Returns the priority assigned by the event publisher.<br/>Such value do not change the delivery mechanism adopted
     * by the Server because it is just a way for the publisher to tell the subscribers how much important is this event.
     * 
     * @return a number from 0 (lowest priority) to 9 (highest priority) or -1 if the message has no priority.
     */
    public int getPriority ();
        
    /**
     * Returns the time when the event has been issued.
     * 
     * @return the time when the event has been issued.
     */
    public Date getTimestamp ();
    
    
    /**
     * States whether the event should be stored or not on Server side.<br/>
     * Marking the event as <i>persistent</i> does not mean that it will not be lost but ensures just that the Server
     * makes a best-effort to store it until the subscribers consume it. 
     * 
     * @return <code>true</code> if the event is <i>persistent</i>, <code>false</code> otherwise
     */
    public boolean isPersistent ();
    
    /**
     * Returns the unique identifier of the event publisher.<br/>
     * This field <b>MUST BE</b> automatically set by the publisher.
     * 
     * @return The identifier of the publisher
     */
    public String getApplicationID ();
    
    /**
     * Returns the pattern that identifies the namespace node under which the event has been published.<br/>
     * This field <b>MUST BE</b> automatically set by the publisher.
     * 
     * @return the pattern that identifies the namespace's node under which the event has been published
     */
    public String getPattern ();
    
}
