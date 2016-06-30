package com.fincons.rabbitmq.event;


/**
 * This interface defines a method for receiving the events consumed by the
 * {@linkplain Subscriber} object this listener is registered with.
 * 
 * @author Fincons Group AG
 * @see Subscriber
 */
public interface EventListener {
    
    /**
     * Performs an action on the given event.<br/>We strongly recommend that implementations of
     * this method <b>do not perform intensive tasks</b> on the same thread because the event consumption
     * is blocked until the execution of <code>onEvent(Event)</code> for each registered event listener 
     * ends.
     * 
     * @param event the event to be processed
     */
    public void onEvent (Event event);

}
