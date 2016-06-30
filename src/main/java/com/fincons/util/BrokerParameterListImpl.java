package com.fincons.util;

import org.apache.log4j.Logger;

import com.fincons.rabbitmq.client.BasicRabbitMqClient;

/**
 * Defines the implementation of the {@link BrokerParameterLis} interface, a class to manage and pass structured configuration setting<br/>
 * 
 * @author Fincons Group AG
 */
public class BrokerParameterListImpl implements BrokerParameterList{
	
	private final String subjectID;
	private final String accessToken;
	private final String brokerHost;
    private final int brokerPort;
    private final String destinationName; //exchange param
    private final String pattern;
    private final String virtualHost;
    private final boolean tlsEnabled;
    private final int mgmtPort;
    
	public BrokerParameterListImpl(String subjectID, String accessToken, String brokerHost,
			int brokerPort, String destinationName, String pattern,
			String virtualHost, boolean tlsEnabled,
			int mgmtPort) {
		this.subjectID = subjectID;
		this.accessToken = accessToken;
		this.brokerHost = brokerHost;
		this.brokerPort = brokerPort;
		this.destinationName = destinationName;
		this.pattern = pattern;
		this.virtualHost = virtualHost;
		this.tlsEnabled = tlsEnabled;
		this.mgmtPort = mgmtPort;
	}

	public String getSubjectID() {
		return subjectID;
	}
	
    public String getAccessToken() {
		return accessToken;
	}

	public String getBrokerHost() {
		return brokerHost;
	}

	public int getBrokerPort() {
		return brokerPort;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public String getPattern() {
		return pattern;
	}

	public String getVirtualHost() {
		return virtualHost;
	}

	public boolean isTlsEnabled() {
		return tlsEnabled;
	}

	public int getMgmtPort() {
		return mgmtPort;
	}

}
