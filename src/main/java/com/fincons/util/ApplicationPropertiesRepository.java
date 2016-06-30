package com.fincons.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * This class load  in memory the properties configuration file called conf.properties.
 * 
 * @author Fincons Group AG
 *
 */
public class ApplicationPropertiesRepository {

	final static Logger logger = Logger.getLogger(ApplicationPropertiesRepository.class);

	public static final String PUBLISH = "publish";
	public static final String SUBSCRIBE = "subscribe";

	private static final String PROPERTY_NAME_PREFIX = "client.";

	public static final String SUBJECT_ID = PROPERTY_NAME_PREFIX + "subjectID";
	public static final String SUBJECT_PWD = PROPERTY_NAME_PREFIX + "accessToken";
	public static final String PORT_NUMBER = PROPERTY_NAME_PREFIX + "portNumber";
	public static final String MGM_PORT_NUMBER = PROPERTY_NAME_PREFIX + "mgmPortNumber";
	public static final String HOST_NAME = PROPERTY_NAME_PREFIX + "host";
	public static final String VIRTUAL_HOST = PROPERTY_NAME_PREFIX + "vhost";
	public static final String TLS_ENABLED =  PROPERTY_NAME_PREFIX + "tls";
	public static final String DEST_NAME = PROPERTY_NAME_PREFIX + "destinationName";
	public static final String PATTERN = PROPERTY_NAME_PREFIX + "pattern";


	private static final String CER_PROPERTY_NAME_PREFIX = PROPERTY_NAME_PREFIX + "cer.";
	private static final String RESOURCE_URI_PROPERTY_NAME_PREFIX = PROPERTY_NAME_PREFIX + "resourceURI.";

	public static final String EXCHANGE_NAME = PROPERTY_NAME_PREFIX + "exchange";
	public static final String QUEUE_NAME = PROPERTY_NAME_PREFIX + "queue";
	public static final String ROUTING_KEY = PROPERTY_NAME_PREFIX + "routingKey";

	public static final String USERNAME = PROPERTY_NAME_PREFIX + "usr";
	public static final String PASSWORD = PROPERTY_NAME_PREFIX + "pwd";

	public static final String SCHEMAS_DIR = PROPERTY_NAME_PREFIX + "schemasDir";
	public static final String KEYSTORE = PROPERTY_NAME_PREFIX + "keystore";

	public static final String KEYSTORE_PWD = PROPERTY_NAME_PREFIX + "keystore.pwd";
	public static final String PRIVATE_KEY_PWD = CER_PROPERTY_NAME_PREFIX + "privateKey.pwd";
	public static final String CAPABILITY_DIR = PROPERTY_NAME_PREFIX + "capability.dir";

	public static final String RESOURCE_URI_SCHEME = RESOURCE_URI_PROPERTY_NAME_PREFIX + "scheme";
	public static final String RESOURCE_URI_AUTHORITY = RESOURCE_URI_PROPERTY_NAME_PREFIX + "authority";
	public static final String RESOURCE_URI_SERVICE = RESOURCE_URI_PROPERTY_NAME_PREFIX + "service";

	public static final String DEFAULT_DATA_URI = PROPERTY_NAME_PREFIX + "defaultDataURIs";

	public static Properties APPLICATION_PROPERTIES = new Properties();

	static {

		InputStream input = null;

		try {

			String config_file_vm_var = System.getProperty("rabbitmq_config_file"); 
						
			if(config_file_vm_var == null){
				throw new EnvVariableNotFound();
			}else{

				input = new FileInputStream(config_file_vm_var);

				APPLICATION_PROPERTIES.load(input);
			}

			validateConfigurationProperties();
			
		} catch (IOException e) {
			logger.error("Error loading the file configuration...",e);
			e.printStackTrace();
			System.exit(0);
		} catch (EnvVariableNotFound ex) {
			logger.error("Error loading the <<rabbitmq_config_file>> environment variable...",ex);
			ex.printStackTrace();
			System.exit(0);
		}catch (NullPointerException exc) {
			logger.error("Mandatory configuration properties missing or file not found...",exc);
			exc.printStackTrace();
			System.exit(0);
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					logger.error("Error closing the input stream...",e);
					e.printStackTrace();
				}
		}
	}

	/**
	 * Validate the presence of all mandatory configuration properties in conf.properties file. 
	 * The set of mandatory configuration properties are client.host, client.portNumber, client.subjectID,
	 * client.accessToken, client.vhost, client.destinationName, client.pattern.
	 * 
	 * @throws NullPointerException
	 */
	private static void validateConfigurationProperties() throws NullPointerException{
		if(APPLICATION_PROPERTIES.getProperty(ApplicationPropertiesRepository.HOST_NAME) == null ||
				APPLICATION_PROPERTIES.getProperty(ApplicationPropertiesRepository.PORT_NUMBER) == null ||
				APPLICATION_PROPERTIES.getProperty(ApplicationPropertiesRepository.SUBJECT_ID) == null ||
				APPLICATION_PROPERTIES.getProperty(ApplicationPropertiesRepository.SUBJECT_PWD) == null ||
				APPLICATION_PROPERTIES.getProperty(ApplicationPropertiesRepository.VIRTUAL_HOST) == null ||
				APPLICATION_PROPERTIES.getProperty(ApplicationPropertiesRepository.DEST_NAME) == null ||
				APPLICATION_PROPERTIES.getProperty(ApplicationPropertiesRepository.PATTERN) == null){
			
			String errorMessage = ("Mandatory configuration properties missing. Check if the following properties exists in the conf.properties file: "+
					ApplicationPropertiesRepository.HOST_NAME+", "+
					ApplicationPropertiesRepository.PORT_NUMBER+", "+
					ApplicationPropertiesRepository.SUBJECT_ID+", "+
					ApplicationPropertiesRepository.SUBJECT_PWD+", "+
					ApplicationPropertiesRepository.VIRTUAL_HOST+", "+
					ApplicationPropertiesRepository.DEST_NAME+", "+
					ApplicationPropertiesRepository.PATTERN
					);
			
			logger.error(errorMessage);

			throw new NullPointerException(errorMessage);
		}
	}


}
