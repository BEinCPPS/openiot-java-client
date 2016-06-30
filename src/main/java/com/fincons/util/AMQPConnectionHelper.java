package com.fincons.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.apache.log4j.Logger;

import com.fincons.rabbitmq.client.BasicRabbitMqClient;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 *  This class encapsulates the definition of AMQP connections and channels.
 * 
 * @author Fincons Group AG
 */
public class AMQPConnectionHelper {

	final static Logger logger = Logger.getLogger(AMQPConnectionHelper.class);

	public AMQPConnectionHelper(){}

	private Channel channel;

	public Channel getChannel() {
		return channel;
	}

	/**	
	 * Create an AMQP connection and a channel relating to that connection.
	 */
	public Connection connection(String user, String password, String vHost, String Host, Integer port) throws IOException {

		logger.info("Calling the connection() method...");

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(user);
		factory.setPassword(password);
		factory.setVirtualHost(vHost);
		factory.setHost(Host);
		factory.setPort(port);
		Connection conn = null;
		try {
			conn = factory.newConnection();
			final Channel c = conn.createChannel();
			channel = c;
			return conn;
		} catch (Exception e) {
			logger.error("Exception during RabbitMQ Connection...", e);
			e.printStackTrace();

			if (channel != null){
				try {
					channel.close();
				} catch (IOException e1) {
					logger.error("Error during the channel creation", e);
					e.printStackTrace();
				} catch (TimeoutException e1) {
					logger.error("Timeout during the channel closing", e);
					e.printStackTrace();
				}
			}
			if (conn != null){
				try {
					conn.close();
				} catch (IOException e1) {
					logger.error("Error during the connection closing", e1);
					e.printStackTrace();
				}
			}

			return null;
		}
	}
}
