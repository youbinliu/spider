package com.wadairen.spider.sites.item;

import org.joda.time.DateTime;

import com.wadairen.spider.dao.LoanCompany;
import com.wadairen.spider.dao.bean.LoanCompanyModel;

public class LoanCompanyItem extends  BaseLoanItem {

	public LoanCompanyItem(String url) {
		super(url);
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComIntro() {
		return comIntro;
	}

	public void setComIntro(String comIntro) {
		this.comIntro = comIntro;
	}

	public String getLoanIntro() {
		return loanIntro;
	}

	public void setLoanIntro(String loanIntro) {
		this.loanIntro = loanIntro;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
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

	public String getRiskControlProduct() {
		return riskControlProduct;
	}

	public void setRiskControlProduct(String riskControlProduct) {
		this.riskControlProduct = riskControlProduct;
	}

	public String getExtraImg() {
		return extraImg;
	}

	public void setExtraImg(String extraImg) {
		this.extraImg = extraImg;
	}
	
	private String name;
	
	private String comIntro;
	
	private String loanIntro;
	
	private String purpose;
	
	private String riskControl;
	
	private String riskControlCompany;
	
	private String riskControlProduct;
	
	private String extraImg;	
	
	public String toString(){
		return String.format("[name:%s] [comInro:%s] [loanIntro:%s] [purpose:%s] [riskControl:%s] "+
				" [riskControlCompany:%s] [riskControlProduct:%s] [extraImg:%s]",getName(),getComIntro(),getLoanIntro(),
				getPurpose(),getRiskControl(),getRiskControlCompany(),getRiskControlProduct(),getExtraImg());
	}
	
	public int store(){
		LoanCompany company = new LoanCompany();
		LoanCompanyModel one = new LoanCompanyModel();
		
		one.setName(getName());
		one.setCom_intro(getComIntro());
		one.setCreated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		one.setExtra_img(getExtraImg());
		one.setLoan_intro(getLoanIntro());
		one.setPurpose(getPurpose());
		one.setRisk_control(getRiskControl());
		one.setRisk_control_company(getRiskControlCompany());
		one.setRisk_control_product(getRiskControlProduct());
		one.setSite_id(getSiteId());
		one.setSource_url(getSourceUrl());
		one.setSource_url_hash(getSourceUrlHash());
		one.setSite(getSite());
		one.setUpdated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		
		company.create(one);
		
		return one.getId();
	}
}
