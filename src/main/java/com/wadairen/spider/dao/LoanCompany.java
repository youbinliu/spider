package com.wadairen.spider.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;

import com.wadairen.spider.dao.bean.LoanCompanyModel;
import com.wadairen.spider.utils.Utils;

public class LoanCompany {

	private static SqlSessionFactory factory;
	private final static String mapperPre = "com.wadairen.spider.dao.mapper.LoanCompanyMapper.";
	
	private static final String ID_insert4update = "insert4update";
	private static final String ID_selectByHash = "selectByHash";
	
	public void create(LoanCompanyModel company){
		
		factory = SessionFactory.getInstance();
		SqlSession session = factory.openSession();
		
		session.insert(sql(ID_insert4update),company);
		
		session.commit(true);
		session.close();
	}
	
	public LoanCompanyModel getByHashurl(String hashUrl){
		factory = SessionFactory.getInstance();
		SqlSession session = factory.openSession();
		
		LoanCompanyModel one = session.selectOne(sql(ID_selectByHash), hashUrl);
		
		return one;
	}
	
	private static String sql(String id){
		return mapperPre+id;
	}
	
	public static void main(String[] argv){
		LoanCompany company = new LoanCompany();
		
		LoanCompanyModel one = new LoanCompanyModel();
		
		one.setName("测试企业");
		one.setCom_intro("企业介绍");
		one.setCreated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		one.setExtra_img("");
		one.setLoan_intro("借贷介绍");
		one.setPurpose("借贷目标");
		one.setRisk_control("风险控制策略");
		one.setRisk_control_company("担保公司名称");
		one.setRisk_control_product("抵押资产");
		one.setSite_id(1);
		one.setSource_url("http://baidu.com");
		one.setSource_url_hash(Utils.urlHash("http://baidu.com"));
		one.setSite("爱投资");
		one.setUpdated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		
		company.create(one);
		
		System.out.println(one.getId());
	}
}
