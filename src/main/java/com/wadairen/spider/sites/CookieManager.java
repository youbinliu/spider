package com.wadairen.spider.sites;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class CookieManager {
	
	private final static Logger logger = Logger.getLogger(CookieManager.class);
	
	private static Properties properties;
	
	private static CookieManager instance;
	
	private static Object lock = new Object();
	
	private static String cookiefile = "cookie.properties";
	
	private CookieManager(){
		properties = new Properties();
		try {
			InputStream input = getClass().getResourceAsStream(cookiefile);
			properties.load(input);
			input.close();
		} catch (FileNotFoundException e) {
			logger.error("fileinputstream cookie file not exist");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("properties cookie file not exist");
			e.printStackTrace();
		}
		
	}
	
	public static CookieManager getInstace(){
		if(null == instance){
			synchronized (lock) {
				instance = new CookieManager();
			}
		}
		
		return instance;
	}
	
	public String getValue(String key){
		if(properties.containsKey(key)){
			return properties.getProperty(key);
		}
		return null;
	}
	
	public void setValue(String key,String value){
		properties.setProperty(key, value);
	}
	
	public void save(){
		FileOutputStream output;
		try {
			output = new FileOutputStream(cookiefile);
			properties.store(output, "");
		} catch (FileNotFoundException e) {
			logger.error("fileinputstream cookie file not exist");
			e.printStackTrace();
		} catch (IOException e) {
			logger.error("properties cookie file not exist");
			e.printStackTrace();
		}
	}
}
