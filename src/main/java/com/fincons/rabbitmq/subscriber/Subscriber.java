package com.fincons.rabbitmq.subscriber;

import com.fincons.rabbitmq.client.RabbitMqClient;
import com.fincons.rabbitmq.event.EventListener;

/**
 * This interface models a consumer of a specific set of events dispatched by another 
 * RabbitMQ Server client.<br/>The actual processing of the events is performed by the
 * registered instances of {@link EventListener}: the method {@link EventListener#onEvent(ENSEvent)}
 *  is called every time a new event has been received by the subscriber.
 *  
 * @author Fincons Group AG
 * 
 * @see EventListener
 */
public interface Subscriber extends RabbitMqClient {

    /**
     * Starts the subscription to a specific branch, subset or node of a specific resource pattern.
     */
    public void subscribe();
    
    /**
     * Ends the subscription.<br/>After calling this method, the registered event listeners
     * will no longer receive events.
     */
    public void unsubscribe();
    
    /**
     * States whether the subscription is active.
     */
    public boolean isSubscribed();

    /**
     * Adds an event listener.
     * 
     * @param listener an event listener.
     * @return the ID of the event listener. This is the ID that must be used to unregister
     * the given event listener.
     */
    public String registerEventListener(EventListener listener);

    /**
     * Removes the event listener identified by <code>listenerID</code>
     * 
     * @param listenerID the ID of the event listener to delete
     */
    public void unregisteredEventListener (String listenerID);
    	
}
