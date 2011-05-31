package kr.lesaautomaton.utils;

import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Config {
	
	private static final String PROP_PATH = "/config.properties";
	private static final String CHARSET = "UTF-8";
	private static Properties properties = null;
	
	static{		
		Logger.getLogger(Config.class).debug("Load messages.properties");
		try{
			properties = new Properties();
			properties.load(new InputStreamReader(Config.class.getResourceAsStream(PROP_PATH), CHARSET));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static String get(String propertyName){
		return properties.getProperty(propertyName);
	}

}
