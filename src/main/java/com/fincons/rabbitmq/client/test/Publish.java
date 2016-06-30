package com.fincons.rabbitmq.client.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Provide a test about the connect, channel creation and publishing operations on an AMQP protocol
 * 
 * @author Fincons Group AG
 *
 */
public class Publish {
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
		String message = "Hello World!";
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		System.out.println(" [x] Sent '" + message + "'");

		channel.close();
		connection.close();
	}

}
