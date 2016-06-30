package com.fincons.rabbitmq.subscriber;

import java.util.Date;
import java.util.Map;

import com.fincons.rabbitmq.event.AbstractEvent;

/**Represent the subscriber event with the specialized methods
 * 
 * 
 * @author Fincons Group AG
 *
 */
public class EventToBeConsumed extends AbstractEvent {
    public EventToBeConsumed(String pattern,
            Map<String, Object> headers, byte[] payload,
            String contentType, String contentEncoding, int priority,
            Date timestamp, boolean isPersistent, String applicationID) {
        super(pattern, headers, payload, contentType, contentEncoding, priority,
                timestamp, isPersistent, applicationID);
    }

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.event.AbstractENSEvent#validate(java.lang.String, java.util.Map, byte[], java.lang.String, java.lang.String, int, java.util.Date, boolean, java.lang.String)
     */
    @Override
    protected void validate(String pattern, Map<String, Object> headers,
            byte[] payload, String contentType, String contentEncoding,
            int priority, Date timestamp, boolean isPersistent,
            String applicationID) throws IllegalArgumentException {
        // TODO Auto-generated method stub
        
    }        
}