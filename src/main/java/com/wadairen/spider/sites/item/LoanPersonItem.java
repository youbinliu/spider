package com.wadairen.spider.sites.item;

import org.joda.time.DateTime;

import com.wadairen.spider.dao.LoanPerson;
import com.wadairen.spider.dao.bean.LoanPersonModel;

public class LoanPersonItem extends BaseLoanItem {

	public LoanPersonItem(String url) {
		super(url);
	}
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	public String getAsset() {
		return asset;
	}

	public void setAsset(String asset) {
		this.asset = asset;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getCreditCard() {
		return creditCard;
	}

	public void setCreditCard(String creditCard) {
		this.creditCard = creditCard;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getBorrowTimes() {
		return borrowTimes;
	}

	public void setBorrowTimes(int borrowTimes) {
		this.borrowTimes = borrowTimes;
	}

	public int getBorrowAmount() {
		return borrowAmount;
	}

	public void setBorrowAmount(int borrowAmount) {
		this.borrowAmount = borrowAmount;
	}

	public int getBreakTimes() {
		return breakTimes;
	}

	public void setBreakTimes(int breakTimes) {
		this.breakTimes = breakTimes;
	}

	public int getBreakAmount() {
		return breakAmount;
	}

	public void setBreakAmount(int breakAmount) {
		this.breakAmount = breakAmount;
	}

	public String getRiskControl() {
		return riskControl;
	}

	public void setRiskControl(String riskControl) {
		this.riskControl = riskControl;
	}

	public String getRiskControlCompany() {
		return riskControlCompany;
	}

	public void setRiskControlCompany(String riskControlCompany) {
		this.riskControlCompany = riskControlCompany;
	}

	public String getRiskControlProudct() {
		return riskControlProudct;
	}

	public void setRiskControlProudct(String riskControlProudct) {
		this.riskControlProudct = riskControlProudct;
	}

	public String getExtraImg() {
		return extraImg;
	}

	public void setExtraImg(String extraImg) {
		this.extraImg = extraImg;
	}

	private int age;
	
	private String degree;
	
	private String marriage;
	
	private String asset;
	
	private String city;
	
	private String income;
	
	private String job;
	
	private String creditCard;
	
	private String intro;
	
	private int borrowTimes;
	
	private int borrowAmount;
	
	private int breakTimes;
	
	private int breakAmount;
	
	private String riskControl;
	
	private String riskControlCompany;
	
	private String riskControlProudct;
	
	private String extraImg;
	
	public int store(){
		LoanPerson person = new LoanPerson();
		LoanPersonModel one = new LoanPersonModel();
		
		one.setAge(getAge());
		one.setAsset(getAsset());
		one.setBorrow_amount(getBorrowAmount());
		one.setBorrow_times(getBorrowTimes());
		one.setBreak_amount(getBreakAmount());
		one.setBreak_times(getBorrowTimes());
		one.setCity(getCity());
		one.setCreated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		one.setCredit_card(getCreditCard());
		one.setDegree(getDegree());
		one.setExtra_img(getExtraImg());
		one.setIncome(getIncome());
		one.setIntro(getIntro());
		one.setJob(getJob());
		one.setMarriage(getMarriage());
		one.setRisk_control(getRiskControl());
		one.setRisk_control_company(getRiskControlCompany());
		one.setRisk_control_product(getRiskControlProudct());
		one.setUpdated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		one.setSite(getSite());
		one.setSite_id(getSiteId());
		one.setSource_url(getSourceUrl());
		one.setSource_url_hash(getSourceUrlHash());
		
		person.create(one);
		
		return one.getId();
	}
	
	public String toString(){
		return String.format("person:[age=%s] [degree=%s] [marriage=%s] [asset=%s] [city=%s] [income=%s] [job=%s] [creditCard=%s] "
				+ "[intro=%s] [borrowTimes=%s] [borrowAmount=%s] [breakTimes=%s] [breakAmount=%s] [riskControl=%s] [riskControlCompany=%s] "
				+ "[riskControlProduct=%s] [extraImg=%s]", getAge(),getDegree(),getMarriage(),getAsset(),getCity(),getIncome(),getJob(),getCreditCard(),
				getIntro(),getBorrowTimes(),getBorrowAmount(),getBreakTimes(),getBreakAmount(),getRiskControl(),getRiskControlCompany(),
				getRiskControlProudct(),getExtraImg());
	}
}
