package com.wadairen.spider.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.wadairen.spider.dao.bean.UserModel;

public class User {
	
	private static SqlSessionFactory factory;
	
	private static final String ID_insertOne = "insertOne";
	
	public int create(String username,String password){
		UserModel one = new UserModel(username,password);
		
		factory = SessionFactory.getInstance();
		SqlSession session = factory.openSession();
		
		session.insert(sql(ID_insertOne),one);
		
		session.commit();
		session.close();
		return 0;
	}
	
	private static String sql(String id){
		return "";//SessionFactory.mapper(User.class.getSimpleName())+"."+id;
	}
	
	public static void main(String[] argv){
		User user = new User();
		user.create("oreo", "123");
	}
}
