package com.wadairen.spider.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.joda.time.DateTime;

import com.wadairen.spider.dao.bean.LoanModel;
import com.wadairen.spider.utils.Utils;

public class LoanInfo {
	private static SqlSessionFactory factory;
	
	private final static String mapperPre = "com.wadairen.spider.dao.mapper.LoanMapper.";
	
	private static final String ID_insert4update = "insert4update";
	private static final String ID_selectByHash = "selectByHash";
	
	public void create(LoanModel loan){
		
		factory = SessionFactory.getInstance();
		SqlSession session = factory.openSession();
		
		session.insert(sql(ID_insert4update),loan);
		
		session.commit(true);
	
		session.close();
	}
	
	public LoanModel getByHashurl(String hashUrl){
		factory = SessionFactory.getInstance();
		SqlSession session = factory.openSession();
		
		LoanModel one = session.selectOne(sql(ID_selectByHash), hashUrl);
		
		return one;
	}
	
	private static String sql(String id){
		return mapperPre+id;
	}
	
	public static void main(String[] argv){
		LoanInfo loanInfo = new LoanInfo();
		LoanModel loan = new LoanModel();
		loan.setSite_id(1);
		loan.setSite("爱投资");
		loan.setSource_url("http://baidu.com");
		loan.setSource_url_hash(Utils.urlHash("http://baidu.com"));
		loan.setAmount(1);
		loan.setBorrower_id(1);
		loan.setBorrower_type(1);
		loan.setCreated_at("2013-11-26 00:50:00");
		loan.setCredit_type("企业直投");
		loan.setInterest(1);
		loan.setInterest_type(1);
		loan.setIntro("简介");
		loan.setMin_invest(1);
		loan.setPay_way("到期还款");
		loan.setPeriod(1);
		loan.setProgress(91);
		loan.setReward(1);
		loan.setStatus(1);
		loan.setTitle("test");
		DateTime date = DateTime.now();
		loan.setUpdated_at(date.toString("yyyy-MM-dd HH:mm:ss"));
		loanInfo.create(loan);
		System.out.println(loan.getId());
		
		LoanModel one = loanInfo.getByHashurl(loan.getSource_url_hash());
		
		System.out.println(one.getId());
	}
}
