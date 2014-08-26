package com.wadairen.spider.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;

import com.wadairen.spider.dao.bean.LoanPersonModel;
import com.wadairen.spider.utils.Utils;

public class LoanPerson {
	
	private static SqlSessionFactory factory;
	
	private final static String mapperPre = "com.wadairen.spider.dao.mapper.LoanPersonMapper.";
	
	private static final String ID_insert4update = "insert4update";
	private static final String ID_selectByHash = "selectByHash";
	
	public void create(LoanPersonModel person){
		
		factory = SessionFactory.getInstance();
		SqlSession session = factory.openSession();
		
		session.insert(sql(ID_insert4update),person);
		
		session.commit(true);
		session.close();
	}
	
	public LoanPersonModel getByHashurl(String hashUrl){
		factory = SessionFactory.getInstance();
		SqlSession session = factory.openSession();
		
		LoanPersonModel one = session.selectOne(sql(ID_selectByHash), hashUrl);
		
		return one;
	}
	
	private static String sql(String id){
		return mapperPre+id;
	}
	
	public static void main(String[] argv){
		LoanPerson person = new LoanPerson();
		LoanPersonModel one = new LoanPersonModel();
		one.setAge(18);
		one.setAsset("身无分文");
		one.setBorrow_amount(90);
		one.setBorrow_times(1);
		one.setBreak_amount(0);
		one.setBreak_times(1);
		one.setCity("北京");
		one.setCreated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		one.setCredit_card("23423423423423");
		one.setDegree("1");
		one.setExtra_img("");
		one.setIncome("111");
		one.setIntro("who me");
		one.setJob("it");
		one.setMarriage("1");
		one.setRisk_control("担保方式");
		one.setRisk_control_company("担保公司");
		one.setRisk_control_product("抵押物");
		one.setUpdated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		one.setSite("爱投资");
		one.setSite_id(1);
		one.setSource_url("http://baidu.com");
		one.setSource_url_hash(Utils.urlHash("http://baidu.com"));
		
		person.create(one);
		System.out.println(one.getId());
		
		LoanPersonModel model = person.getByHashurl(one.getSource_url_hash());
		System.out.println(model.getAge());
		
	}
}
