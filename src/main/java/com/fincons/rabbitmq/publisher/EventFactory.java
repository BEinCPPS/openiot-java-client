package com.fincons.rabbitmq.publisher;

import java.util.Map;

import com.fincons.rabbitmq.event.Event;

/**
 * This interface defines methods to create {@link Event} objects to be published.
 * 
 * @author Fincons Group AG
 */
public interface EventFactory {

	/**
     * Creates a new event to be published with the default content type, content encoding, and
     * priority.
     * @param headers the event headers
     * @param payload the event payload
     * @param isPersistent <code>true</code> if the event must be stored, <code>false</code> otherwise
     * @return a new <code>Event</code> object
     */
    public Event create (Map<String, Object> headers, byte[] payload, boolean isPersistent);
    
    /**
     * Creates a new event.
     * @param headers the event headers
     * @param payload the event payload
     * @param contentType the media type of the payload
     * @param contentEncoding the encoding of the payload
     * @param priority the event priority
     * @param isPersistent <code>true</code> if the event must be stored, <code>false</code> otherwise
     * @return a new <code>Event</code> object
     */
    public Event create (Map<String, Object> headers, byte[] payload,
            String contentType, String contentEncoding, int priority, boolean isPersistent);
}

