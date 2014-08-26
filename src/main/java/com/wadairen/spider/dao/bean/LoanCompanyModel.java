package com.wadairen.spider.dao.bean;

public class LoanCompanyModel {
	
	public LoanCompanyModel(){
		
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCom_intro() {
		return com_intro;
	}

	public void setCom_intro(String com_intro) {
		this.com_intro = com_intro;
	}

	public String getLoan_intro() {
		return loan_intro;
	}

	public void setLoan_intro(String loan_intro) {
		this.loan_intro = loan_intro;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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
	
	private String name;
	
	private String com_intro;
	
	private String loan_intro;
	
	private String purpose;
	
	private String risk_control;
	
	private String risk_control_company;
	
	private String risk_control_product;
	
	private String extra_img;
	
	private String created_at;
	
	private String updated_at;
}
