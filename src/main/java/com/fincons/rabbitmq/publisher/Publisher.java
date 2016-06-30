package com.fincons.rabbitmq.publisher;

import com.fincons.rabbitmq.client.RabbitMqClient;
import com.fincons.rabbitmq.event.Event;

/**
 * This interface represents a producer of events collected by the RabbitMQ Server.
 * 
 * @author Fincons Group AG
 *
 */
public interface Publisher extends RabbitMqClient{
	
    /**
     * Publishes the given event.<br/>In order to be able to publish events, this client MUST be connected
     * to the RabbitMQ Server. After calling {@code #disconnect()}, this client will not
     * be able to publish events anymore.
     * 
     * @param event the event to be published
     */
    public void publish (Event event);

}
