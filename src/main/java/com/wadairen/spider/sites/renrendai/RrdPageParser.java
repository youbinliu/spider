package com.wadairen.spider.sites.renrendai;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.utils.Utils;

public class RrdPageParser {

	private final static Logger logger = Logger.getLogger(RrdPageParser.class);
	
	
	
	public static RrdLoanItem infopage(RrdLoanItem item,BrowserResult result){
		
		Document doc = Jsoup.parse(result.getResponseString());
		Element eleInfo = doc.select("#loan-tab-content").select("[data-name=info]").get(0);
		
		//分析左边的信息
		Element eleLeftPart = eleInfo.child(0);
		
		//分析用户信息
		Element eleUserInfo = eleLeftPart.child(1);
		//第一行
		String job = "";
		//String username = eleUserInfo.child(0).child(1).text();
		String gshy = eleUserInfo.child(0).child(3).text();
		job += "公司行业："+gshy;
		//第二行
		String age = eleUserInfo.child(1).child(1).text();
		String gsgm = eleUserInfo.child(1).child(3).text();
		job += " 公司规模："+gsgm;
		//第三行
		String degree = eleUserInfo.child(2).child(1).text();
		String city = eleUserInfo.child(2).child(3).text();
		//第五行
		String marrage = eleUserInfo.child(4).child(1).text();
		String positon = eleUserInfo.child(4).child(3).text();
		job += " 岗位职位："+positon;
		//第六行
		String assets = "";
		Element eleAsset = eleUserInfo.child(5).child(1);
		
		if(eleAsset.child(0).child(0).hasClass("i-icon-check-checked")){
			assets += "房产,";
		}
		if(eleAsset.child(1).child(0).hasClass("i-icon-check-checked")){
			assets += "房贷,";
		}
		String income = eleUserInfo.child(5).child(3).text();
		//第七行
		
		eleAsset = eleUserInfo.child(6).child(1);
		if(eleAsset.child(0).child(0).hasClass("i-icon-check-checked")){
			assets += "车产,";
		}
		if(eleAsset.child(0).child(1).hasClass("i-icon-check-checked")){
			assets += "车贷,";
		}
		//分析介绍说明
		Element eleIntro = eleLeftPart.child(2);
		String intro = eleIntro.child(1).text();
		//分析右边的信息
		Element eleRightPart = eleInfo.child(1);
		//分析信用记录
		Element eleCredit = eleRightPart.child(0).child(1);//定位到ul
		
		String borrowTimes = eleCredit.child(3).child(0).child(0).text();
		String borrowAmount = eleCredit.child(5).child(0).child(1).text();
		String breakAmount = eleCredit.child(5).child(2).child(1).text();
		String breakTimes = eleCredit.child(7).child(0).child(0).text();
		
		RrdLoanPersonItem person = new RrdLoanPersonItem(result.getUrl());
		
		if(Utils.isNullOrEmpty(age)){
			age = "0";
		}
		person.setAge(Integer.valueOf(age));
		person.setAsset(assets);
		if(Utils.isNullOrEmpty(borrowAmount)){
			borrowAmount = "0";
		}
		person.setBorrowAmount(Integer.valueOf(borrowAmount));
		if(Utils.isNullOrEmpty(borrowTimes)){
			borrowTimes = "0";
		}
		person.setBorrowTimes(Integer.valueOf(borrowTimes));
		if(Utils.isNullOrEmpty(breakAmount)){
			breakAmount = "0";
		}
		person.setBreakAmount(Integer.valueOf(breakAmount));
		if(Utils.isNullOrEmpty(breakTimes)){
			breakTimes = "0";
		}
		person.setBreakTimes(Integer.valueOf(breakTimes));
		person.setCity(city);
		person.setDegree(degree);
		person.setIncome(income);
		person.setJob(job);
		person.setMarriage(marrage);
		person.setRiskControl(intro);
		person.setIntro(intro);
		
		item.setBorrower(person);
		item.setMinInvest(50);
		item.setReward(0);
		
		return item;		
	}
	
	public static ArrayList<RrdLoanItem> listpage(BrowserResult result){
		ArrayList<RrdLoanItem> list = new ArrayList<>();
		
		Gson gson = new Gson();
		Type dataType = new TypeToken<ResponseData>(){}.getType();
		ResponseData response = gson.fromJson(result.getResponseString(),dataType);
		
		if(Utils.isNullOrEmpty(response) || 
				Utils.isNullOrEmpty(response.getData()) ||
				Utils.isNullOrEmpty(response.getData().getLoans())||
				response.getData().getLoans().isEmpty()){
			logger.error("parse json error or result is empty:"+result.getUrl());
			return list;
		} 
		
		String infoSeed = "http://www.renrendai.com/lend/detailPage.action?loanId=";
		String curSeed;
		for (Loan loan : response.getData().getLoans()) {
			
			curSeed = infoSeed+loan.getLoanId();
			RrdLoanItem item = new RrdLoanItem(curSeed);
			
			item.setAmount(loan.getAmount());
			item.setTitle(loan.getTitle());
			item.setCreditType(loan.getDisplayLoanType());
			item.setInterest(loan.getInterest());
			item.setInterestType(Constant.INTEREST_TYPE_YEAR);
			item.setPeriodType(Constant.PERIOD_TYPE_MONTH);
			item.setPeriod(loan.getMonths());
			item.setProgress(loan.getFinishedRatio());
			item.setStatus(loan.getStatus());
			item.setPayWay(loan.getLoanType());
			
			item.setType(Constant.TYPE_INFO);
			
			list.add(item);
		}
		
		return list;
	}
	
	public class ResponseData{
		private Data data;

		public Data getData() {
			return data;
		}

		public void setData(Data data) {
			this.data = data;
		}
	}
	
	public class Data{
		private ArrayList<Loan> loans;

		public ArrayList<Loan> getLoans() {
			return loans;
		}

		public void setLoans(ArrayList<Loan> loans) {
			this.loans = loans;
		}
	}
	
	public class Loan{
		
		public int getLoanId() {
			return loanId;
		}

		public void setLoanId(int loanId) {
			this.loanId = loanId;
		}

		public int getAmount() {
			return amount;
		}

		public void setAmount(int amount) {
			this.amount = amount;
		}

		public int getBorrowerId() {
			return borrowerId;
		}

		public void setBorrowerId(int borrowerId) {
			this.borrowerId = borrowerId;
		}

		public String getDisplayLoanType() {
			if(displayLoanType == null)return null;
			if("XYRZ".equals(displayLoanType)){
				return "信用认证";
			}else if("SDRZ".equals(displayLoanType)){
				return "实地认证";
			}else if("JGDB".equals(displayLoanType)){
				return "机构担保";
			}else if("ZNLC".equals(displayLoanType)){
				return "智能理财";
			}
			
			return displayLoanType;
		}

		public void setDisplayLoanType(String displayLoanType) {
			this.displayLoanType = displayLoanType;
		}

		public int getFinishedRatio() {
			Float r = Float.valueOf(finishedRatio)*100;
			return r.intValue();
		}

		public void setFinishedRatio(float finishedRatio) {
			this.finishedRatio = finishedRatio;
		}

		public int getInterest() {
			Float r = Float.valueOf(interest)*100;
			return r.intValue();
		}

		public void setInterest(float interest) {
			this.interest = interest;
		}

		public String getNickName() {
			return nickName;
		}

		public void setNickName(String nickName) {
			this.nickName = nickName;
		}

		public int getStatus() {
			if(status == "OPEN"){
				return Constant.STATUS_OPEN;
			}else{
				return Constant.STATUS_CLOSE;
			}
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		private int loanId;
		
		private int amount;
		
		private int borrowerId;
		
		private String displayLoanType;
		
		private float finishedRatio;
		
		private float interest;
		
		private String nickName;
		
		private String status;
		
		private String title;
		
		private int months;
		
		private String loanType;
		
		public String getLoanType() {
			if(loanType == "DEBX"){
				return "按月还款/等额本息";
			}
			return loanType;
		}

		public void setLoanType(String loanType) {
			this.loanType = loanType;
		}

		public int getMonths() {
			return months;
		}

		public void setMonths(int months) {
			this.months = months;
		}
	}
}
