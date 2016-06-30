package com.fincons.rabbitmq.publisher;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.log4j.Logger;

import com.fincons.rabbitmq.client.BasicRabbitMqClient;
import com.fincons.rabbitmq.event.Event;
import com.fincons.util.AMQPConstants;
import com.fincons.util.BrokerParameterList;
import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.AMQP.BasicProperties.Builder;

/**
 * Provides the default implementation of {@link Publisher}.
 * 
 * @author Fincons Group AG
 *
 */
public class BasicPublisher extends BasicRabbitMqClient implements Publisher {
    	
	final static Logger logger = Logger.getLogger(BasicPublisher.class);
	
	public static final String APPLICATION_ID = BasicPublisher.class.getName() + "V0.1";
	
	private String pattern;
    private String exchange;
    private String userID;
    
    public BasicPublisher () {
		super();	
	}

    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.client.BasicRabbitMqClient#useENSBrokerConnectionParameters
     */
    @Override
    protected void useENSBrokerConnectionParameters(BrokerParameterList parameters) {
    	
		logger.info("Calling the useENSBrokerConnectionParameters() method...");
    	
        pattern = parameters.getPattern();
        exchange = parameters.getDestinationName();
        userID = parameters.getSubjectID();
    }
    
    /* (non-Javadoc)
     * @see com.fincons.rabbitmq.publisher.Publisher#publish()
     */
    @Override
    public void publish(Event event) throws IllegalArgumentException, IllegalStateException {
    	
		logger.info("Calling the publish() method...");

        if (!this.isConnected()){
        	logger.error("The publisher is not connected");
            throw new IllegalStateException("The publisher is not connected");
        }
        if (event == null){
        	logger.error("The event to be published cannot be null");
            throw new IllegalArgumentException("The event to be published cannot be null");
        }
        //build the AMQP message properties
        Builder messagePropertiesBuilder = new Builder();
        messagePropertiesBuilder.contentEncoding(event.getContentEncoding())
            .contentType(event.getContentType())
            .deliveryMode(event.isPersistent() ? AMQPConstants.PERSISTENT_DELIVERY : AMQPConstants.NOT_PERSISTENT_DELIVERY)
            .timestamp(event.getTimestamp())
            .userId(userID)
            .appId(APPLICATION_ID)
            .priority(event.getPriority())
            .headers(event.getHeaders()); 
        BasicProperties properties = messagePropertiesBuilder.build();
        
        try {
            
            channel.basicPublish(exchange, pattern, properties, event.getPayload());
            
            logger.debug("Published an event on the exchange '" + exchange + "'" +
                    " (pattern: '" + pattern + "') with properties " + properties  + " and a " +
                    MessageFormat.format("{0,number,integer} {0,choice,0#bytes|1#byte|1<bytes}",
                        event.getPayload() == null? 0 : event.getPayload().length) + " payload");
        } catch (IOException e) {
			logger.error("Error during event publishing", e);
            e.printStackTrace();
            closeConnection();
        }
    }
}
