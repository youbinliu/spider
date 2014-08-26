package com.wadairen.spider.dao.bean;

public class LoanPersonModel {
	
	public LoanPersonModel(){
		
	}
	
	public int getSite_id() {
		return site_id;
	}

	public void setSite_id(int site_id) {
		this.site_id = site_id;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSource_url() {
		return source_url;
	}

	public void setSource_url(String source_url) {
		this.source_url = source_url;
	}

	public String getSource_url_hash() {
		return source_url_hash;
	}

	public void setSource_url_hash(String source_url_hash) {
		this.source_url_hash = source_url_hash;
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

	public String getCredit_card() {
		return credit_card;
	}

	public void setCredit_card(String credit_card) {
		this.credit_card = credit_card;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public int getBorrow_times() {
		return borrow_times;
	}

	public void setBorrow_times(int borrow_times) {
		this.borrow_times = borrow_times;
	}

	public int getBorrow_amount() {
		return borrow_amount;
	}

	public void setBorrow_amount(int borrow_amount) {
		this.borrow_amount = borrow_amount;
	}

	public int getBreak_times() {
		return break_times;
	}

	public void setBreak_times(int break_times) {
		this.break_times = break_times;
	}

	public int getBreak_amount() {
		return break_amount;
	}

	public void setBreak_amount(int break_amount) {
		this.break_amount = break_amount;
	}

	public String getRisk_control() {
		return risk_control;
	}

	public void setRisk_control(String risk_control) {
		this.risk_control = risk_control;
	}

	public String getRisk_control_company() {
		return risk_control_company;
	}

	public void setRisk_control_company(String risk_control_company) {
		this.risk_control_company = risk_control_company;
	}

	public String getRisk_control_product() {
		return risk_control_product;
	}

	public void setRisk_control_product(String risk_control_product) {
		this.risk_control_product = risk_control_product;
	}

	public String getExtra_img() {
		return extra_img;
	}

	public void setExtra_img(String extra_img) {
		this.extra_img = extra_img;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	private int id;
	
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	private int site_id;
	
	private String site;
	
	private String source_url;
	
	private String source_url_hash;
	
	private int age;
	
	private String degree;
	
	private String marriage;
	
	private String asset;
	
	private String city;
	
	private String income;
	
	private String job;
	
	private String credit_card;
	
	private String intro;
	
	private int borrow_times;
	
	private int borrow_amount;
	
	private int break_times;
	
	private int break_amount;
	
	private String risk_control;
	
	private String risk_control_company;
	
	private String risk_control_product;
	
	private String extra_img;
	
	private String created_at;
	
	private String updated_at;
}
