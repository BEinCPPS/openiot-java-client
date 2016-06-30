package com.fincons.util;

/**
 * Represent a support class interface to manage and store information about the operative broker configuration parameters.
 * 
 * @author Fincons Group AG
 *
 */
public interface BrokerParameterList {
	public String getSubjectID();
	public String getBrokerHost();
	public int getBrokerPort();
	public String getDestinationName();
	public String getPattern();
	public String getVirtualHost();
	public boolean isTlsEnabled();
	public int getMgmtPort();
}
