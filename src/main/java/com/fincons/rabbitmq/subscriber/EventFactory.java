package com.fincons.rabbitmq.subscriber;

import java.util.Date;
import java.util.Map;

import com.fincons.rabbitmq.event.Event;

/**
 * This interface defines methods to create {@link Event} objects to be consumed.
 * 
 * @author Fincons Group AG
 *
 */
public interface EventFactory {
    
    /**
     * Creates a consumable event.
     * @param pattern the pattern that identifies the namespace node under which the event has been published.<br/>
     * @param headers the event headers
     * @param payload the informative content of the event
     * @param contentType the media type of the payload
     * @param contentEncoding the encoding of the payload
     * @param priority the event priority
     * @param timestamp the time when the event has been issued
     * @param isPersistent <code>true</code> if the event is <i>persistent</i>, <code>false</code> otherwise
     * @param applicationID the unique identifier of the event publisher.
     * @return an event
     */
    public Event create (String pattern, Map<String, Object> headers, byte[] payload,
            String contentType, String contentEncoding, int priority, 
            Date timestamp, boolean isPersistent, String applicationID);
}
