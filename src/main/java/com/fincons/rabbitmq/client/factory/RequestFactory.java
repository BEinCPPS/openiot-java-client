package com.fincons.rabbitmq.client.factory;

import org.apache.log4j.Logger;

import com.fincons.rabbitmq.client.BasicRabbitMqClient;
import com.fincons.rabbitmq.client.RabbitMqClient;
import com.fincons.rabbitmq.publisher.BasicPublisher;
import com.fincons.rabbitmq.subscriber.BasicEventFactory;
import com.fincons.rabbitmq.subscriber.BasicSubscriber;
import com.fincons.rabbitmq.subscriber.EventFactory;
import com.fincons.util.ApplicationPropertiesRepository;

/**
 * This class provides the functionality for creating a RabbitMQ client to perform Publish or Subscribe AMQP operation
 * 
 * @author Fincons Group AG
 */
public class RequestFactory {

	final static Logger logger = Logger.getLogger(RequestFactory.class);
	
    /**
     *	Provide extra setting properties
     */
    public static class ExtraSettings4Publisher {
        public static final String ENTITY_DATA_URI = "edURI";
        public static final String ENTITY_URI = "eURI";
        public static final String MAX_RANDOM = "maxRnd";
        public static final String MIN_RANDOM = "minRnd";
        public static final String DELAY = "delay";
        public static final int DEFAULT_MAX_RANDOM = 10;
        public static final int DEFAULT_MIN_RANDOM = 1;
    }
	
    
	/**
	 * Return a RabbitMQ Client to be used as subscriber or publisher  
	 * 
	 * @param opName The type of client that you want to create. It accept only {@link ApplicationPropertiesRepository.PUBLISH} 
	 * or {@link ApplicationPropertiesRepository.SUBSCRIBE} as valid values.
	 * @return A {@link RabbitMqClient} interface to be used as a Publish or Subscribe Client
	 * @throws IllegalArgumentException if the parameter is invalid
	 */
	public static RabbitMqClient startGuestApplication (String opName){

		logger.info("Calling the startGuestApplication() method...");
		
		RabbitMqClient client;
		
		if (opName.startsWith(ApplicationPropertiesRepository.PUBLISH)) {

			logger.info("Creating a new Publisher instance...");
			client = new BasicPublisher();
			
		}
		else if (opName.startsWith(ApplicationPropertiesRepository.SUBSCRIBE)) {
	        
			logger.info("Creating a new Subscriber and EventFactory instance...");
			
			EventFactory eventFactory = new BasicEventFactory();
			client = new BasicSubscriber(eventFactory);
			
		} else{
			logger.error("No operation available for label '" + opName + "'");
			throw new IllegalArgumentException("No operation available for label '" + opName + "'");
		}
		
		client.connect();
		
		return client;
	}
	
}
