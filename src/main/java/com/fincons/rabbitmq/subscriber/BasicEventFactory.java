package com.fincons.rabbitmq.subscriber;

import java.util.Date;
import java.util.Map;

import com.fincons.rabbitmq.event.Event;

/**
 * Provides the default implementation of {@link EventFactory}.<br/>
 * 
 * @author Fincons Group AG
 *
 */
public class BasicEventFactory implements EventFactory {
    
    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.subscriber.EventFactory#create(java.lang.String, java.util.Map, byte[], java.lang.String, java.lang.String, int, java.util.Date, boolean, java.lang.String)
     */
    @Override
    public Event create(String pattern, Map<String, Object> headers,
            byte[] payload, String contentType, String contentEncoding,
            int priority, Date timestamp, boolean isPersistent,
            String applicationID) {
        return new EventToBeConsumed(pattern, headers, payload, contentType, contentEncoding,
                priority, timestamp, isPersistent, applicationID);
    }

}
