package com.wadairen.spider.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

public class SessionFactory {
	
	private final static Logger logger = Logger.getLogger(SessionFactory.class);
	
	private static SqlSessionFactory instance;
	
	private final static String resource = "com/wadairen/spider/dao/config.xml";
	
	
	
	private static Object obj = new Object();
	
	private SessionFactory(){}
	
	public static SqlSessionFactory getInstance(){
		if(null == instance){
			synchronized (obj) {
				InputStream inputStream = null;
				try {
					inputStream = Resources.getResourceAsStream(resource);
				} catch (IOException e) {
					logger.error("ibatis config resource file error");
					e.printStackTrace();
				}
				instance = new SqlSessionFactoryBuilder().build(inputStream);
			}			
		}
		
		return instance;
	}
	
	
}
