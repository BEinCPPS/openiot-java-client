package com.fincons.rabbitmq.subscriber;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.fincons.rabbitmq.client.BasicRabbitMqClient;
import com.fincons.rabbitmq.event.Event;
import com.fincons.rabbitmq.event.EventListener;
import com.fincons.rabbitmq.publisher.BasicPublisher;
import com.fincons.util.AMQPConstants;
import com.fincons.util.BrokerParameterList;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;
import com.rabbitmq.client.AMQP.BasicProperties;

/**
 * Provides the default implementation of {@link Subscriber}. 
 * 
 * @author Fincons Group AG
 *
 */
public class BasicSubscriber extends BasicRabbitMqClient implements Subscriber {

	final static Logger logger = Logger.getLogger(BasicSubscriber.class);
	
	private String queueName;

	private boolean isSubscribed;

	private DefaultConsumer basicConsumer;
	private final EventFactory eventFactory;
	
    private Map<String, EventListener> listeners = new HashMap<String, EventListener>();
	
	public BasicSubscriber(EventFactory eventFactory) {
		super();
		this.eventFactory = eventFactory;
	}
	
    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.client.BasicRabbitMqClient#useENSBrokerConnectionParameters
     */
	@Override
	protected void useENSBrokerConnectionParameters(
			BrokerParameterList parameters) {
		this.queueName = parameters.getDestinationName();
	}

	/* (non-Javadoc)
     * @see com.fincons.rabbitmq.subscriber.Subscriber#subscribe()
     */
    @Override
    public void subscribe() throws IllegalStateException{
    	
		logger.info("Calling the subscribe() method...");
    	
        if (isSubscribed){
    		logger.error("A subscription is already active");
            throw new IllegalStateException ("A subscription is already active");
        }
        try {
        	
            if (null == channel || !channel.isOpen())
                channel = connection.createChannel();
                        
            basicConsumer = new BasicConsumer(channel);
            
            String consumerTag = channel.basicConsume(queueName, true, basicConsumer);

            isSubscribed = true;
            
            logger.debug("Basic consumer created: consumerTag=" + consumerTag);
        } catch (IOException e) {
			logger.error("Error during event subscribing", e);
        	e.printStackTrace();
        }
        
    }	
	
	public void unsubscribe() throws IllegalStateException {

		logger.info("Calling the unsubscribe() method...");
		
		if (!isSubscribed){
			logger.error("Error during event unsubscribing. No subscription active");
			throw new IllegalStateException ("No subscription active");
		}
		this.isSubscribed = false;

		try {
			channel.basicCancel(basicConsumer.getConsumerTag());
			channel.close();

		} catch (IOException e) {
			logger.error("Error during event unsubscribing", e);
			e.printStackTrace();
			closeConnection();
		} catch (TimeoutException e) {
			logger.error("Timeout during event unsubscribing", e);
			e.printStackTrace();
		}


	}

	@Override
	public boolean isSubscribed() {
		return isSubscribed;
	}
	
    @Override
    public String registerEventListener(EventListener listener) {
        if (listener == null){
			logger.error("The listener cannot be null");
            throw new IllegalArgumentException ("The listener cannot be null");
        }
        UUID listenerID = UUID.randomUUID();
        String listenerID_STR = listenerID.toString();
        listeners.put(listenerID.toString(), listener);
        return listenerID_STR;
    }

	@Override
	public void unregisteredEventListener(String listenerID) {
        if (listeners.remove(listenerID) == null){
			logger.error("No listener associated to ID" + listenerID);
            throw new IllegalArgumentException("No listener associated to ID " + listenerID);
        }
	}	
	
	
    private class BasicConsumer extends DefaultConsumer{
        /**
         * @param channel
         */
        public BasicConsumer(Channel channel) {
            super(channel);
        }

        /* (non-Javadoc)
         * @see com.rabbitmq.client.DefaultConsumer#handleDelivery(java.lang.String, com.rabbitmq.client.Envelope, com.rabbitmq.client.AMQP.BasicProperties, byte[])
         */
        @Override
        public void handleDelivery(String consumerTag,
                Envelope envelope, BasicProperties properties,
                byte[] body) throws IOException {
            Event event = eventFactory.create(envelope.getRoutingKey(), 
                properties.getHeaders(), body, properties.getContentType(), properties.getContentEncoding(),
                properties.getPriority(), properties.getTimestamp(), 
                properties.getDeliveryMode() == AMQPConstants.NOT_PERSISTENT_DELIVERY ? false : true,
                properties.getAppId());
            for (Entry<String, EventListener> listenerEntry : listeners.entrySet())
                listenerEntry.getValue().onEvent(event);
        }

        /* (non-Javadoc)
         * @see com.rabbitmq.client.DefaultConsumer#handleShutdownSignal(java.lang.String, com.rabbitmq.client.ShutdownSignalException)
         */
        @Override
        public void handleShutdownSignal(String consumerTag,
                ShutdownSignalException sig) {
            try {
                //cannot unsubscribe because the channel is not working
                isSubscribed = true;
                disconnect();
            } catch (IllegalStateException e) {
                closeConnection();
            }
        }
    }


}