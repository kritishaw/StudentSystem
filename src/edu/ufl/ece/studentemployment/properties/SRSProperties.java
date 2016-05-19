package edu.ufl.ece.studentemployment.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SRSProperties {
	
	public static String DBCredentials = "DBCredential.properties";
	public static String DBQueries = "queries.properties";
	public static String WebProps = "WebProperties.properties";
	public static String EmailProps = "EmailProperties.properties";
	public static final String INSERT_OPERATION = "INSERT OPERATION";
	public static final String UPDATE_OPERATION = "CSTAT UPDATE";
	public static final String BULK_UPDATE_OPERATION = "BULK UPDATE";
	private static String propLoc = "edu/ufl/ece/srs/properties/";
	
	private static Map<String, Properties> propMap = new HashMap<String, Properties>();
	
	public static String getProperty(String propertyFile, String key) throws IOException{
	   	Properties props = propMap.get(propertyFile);
	   	if(props ==null ){
	   		props = new Properties();
	   		InputStream is = SRSProperties.class.getClassLoader().
                                    getResourceAsStream(propLoc+propertyFile);
	   		if(is == null)
	   			throw new IOException();
	   		props.load(is);	   		
	   	}
	   	return props.getProperty(key);
	}
}
