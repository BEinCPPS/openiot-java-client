package com.fincons.rabbitmq.client.test;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * Provide a test about the connect, channel creation and subscribe operations on an AMQP protocol
 * 
 * @author Fincons Group AG
 *
 */
public class Subscribe {

	private final static String QUEUE_NAME = "hello";

	public static void main(String[] argv) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();

		factory.setHost("172.25.13.206");
		factory.setUsername("ENS-Client");
		factory.setPassword("IoT@Work-ENS-Client-2012");
		factory.setVirtualHost("Raw_Events_Broker_Service");
		factory.setPort(5273);    

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println(" [x] Received '" + message + "'");
			}
		};
		channel.basicConsume(QUEUE_NAME, true, consumer);
	}
}