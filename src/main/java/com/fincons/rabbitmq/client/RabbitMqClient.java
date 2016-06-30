package com.fincons.rabbitmq.client;
/**
 * This interface represents a RabbitMQ Client Service. It defines the two operations
 * commons to all kinds of client (i.e. publisher and subscriber):
 * <ul><li>{@link #connect()}</li><li>{@link #disconnect()}</li></ul>.
 * 
 * @author Fincons Group AG
 *
 */
public interface RabbitMqClient {
    /**
     * Sets up a communication session between this client and RabbitMQ Server.<br/>
     * If no exception has been thrown, the client is authorised to connect to the operative broker
     * and a session is started.
	**/
    public void connect ();

    /**
     * Quits the communication session between this client and the RabbitMQ Server.<br/>
     */    
    public void disconnect ();
    
    /**
     * States if this client is connected to the RabbitMQ server (i.e. the session is alive).
     * 
     * @return <code>true</code> is the client is connected to the RabbitMQ Server,
     * <code>false</code> otherwise.
     */    
    public boolean isConnected ();
}
