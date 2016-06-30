package com.fincons.rabbitmq.client.test;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.fincons.rabbitmq.client.RabbitMqClient;
import com.fincons.rabbitmq.client.factory.RequestFactory;
import com.fincons.rabbitmq.event.Event;
import com.fincons.rabbitmq.publisher.BasicEventFactory;
import com.fincons.rabbitmq.publisher.Publisher;
import com.fincons.util.ApplicationPropertiesRepository;
/**
 * Provide a test case about the use of RabbitMQ Client API Library publish functionalities 
 * 
 * @author Fincons Group AG
 *
 */
public class TestPublish {

	/**
	 * @param args
	 */
    private static Thread publishingThread;
    private static PublishingRunnable publishingRunnable;
	
	public static void main(String[] args) {

		RabbitMqClient client =  RequestFactory.startGuestApplication("publish");
		
		if (client instanceof Publisher) {
            publishingRunnable = new PublishingRunnable((Publisher)client, "publish");
            publishingThread = new Thread(publishingRunnable);
            
            if(client.isConnected())
            	publishingThread.start();			
		}

	}
	

	private static class PublishingRunnable implements Runnable{
		private static final long DELAY = Long.parseLong(
				ApplicationPropertiesRepository.APPLICATION_PROPERTIES
				.getProperty(RequestFactory.ExtraSettings4Publisher.DELAY));

		private Publisher pubApp;
		private volatile boolean stop;
		private int minRandom;
		private int maxRandom;
		private Random randomGenerator;
		private String requestedOperation;

		public PublishingRunnable (Publisher pubApp, String requestedOperation) {
			this.pubApp = pubApp;
			this.stop = true;

			randomGenerator = new Random();
			maxRandom = Integer.parseInt(ApplicationPropertiesRepository.APPLICATION_PROPERTIES.getProperty(
					RequestFactory.ExtraSettings4Publisher.MAX_RANDOM,
					RequestFactory.ExtraSettings4Publisher.DEFAULT_MAX_RANDOM + ""));
			minRandom = Integer.parseInt(ApplicationPropertiesRepository.APPLICATION_PROPERTIES.getProperty(
					RequestFactory.ExtraSettings4Publisher.MIN_RANDOM,
					RequestFactory.ExtraSettings4Publisher.DEFAULT_MIN_RANDOM + ""));
			this.requestedOperation = requestedOperation;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			Event ensE;
			try {
				stop = false;
				while (!stop && pubApp.isConnected()) {

					ensE = createEvent();
					pubApp.publish(ensE);
					Thread.sleep(DELAY);
					System.out.println("Event published: " + ensE.toString());
				}
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalStateException e1) {
				e1.printStackTrace();
			} catch (InterruptedException e2) {            
				e2.printStackTrace();
			}
		}

		private Event createEvent () {

			StringBuffer payload = new StringBuffer();
			Map<String,Object> headers = new HashMap<String,Object>();

			float value = getRandomFloat();
			headers.put("Power", value + "");
			payload.append(value);

			value = getRandomFloat();
			headers.put("Voltage", value + "");
			payload.append(", ");
			payload.append(value);

			value = getRandomFloat();
			headers.put("Current", value + "");
			payload.append(", ");
			payload.append(value);

			try {
				return new BasicEventFactory().create(
						headers,payload.toString().getBytes(Event.DEFAULT_CONTENT_ENCODING), false);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}

		private float getRandomFloat () {
			return minRandom + (randomGenerator.nextFloat() * (maxRandom-minRandom) + 1);
		}

		public void stop () {
			this.stop = true;
		}

	}

}
