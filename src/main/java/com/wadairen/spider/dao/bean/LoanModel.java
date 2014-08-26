package com.wadairen.spider.dao.bean;

public class LoanModel {
	
	public LoanModel(){
		
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCredit_type() {
		return credit_type;
	}

	public void setCredit_type(String credit_type) {
		this.credit_type = credit_type;
	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public int getInterest_type() {
		return interest_type;
	}

	public void setInterest_type(int interest_type) {
		this.interest_type = interest_type;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getPeriod_type() {
		return period_type;
	}

	public void setPeriod_type(int period_type) {
		this.period_type = period_type;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getBorrower_id() {
		return borrower_id;
	}

	public void setBorrower_id(int borrower_id) {
		this.borrower_id = borrower_id;
	}

	public int getBorrow_type() {
		return borrower_type;
	}

	public void setBorrower_type(int borrower_type) {
		this.borrower_type = borrower_type;
	}

	public String getPay_way() {
		return pay_way;
	}

	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getMin_invest() {
		return min_invest;
	}

	public void setMin_invest(int min_invest) {
		this.min_invest = min_invest;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
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
	
	private String title;
	
	private String credit_type;
	
	private int interest;
	
	private int interest_type;
	
	private int amount;
	
	private int period;
	
	private int period_type;
	
	private int progress;
	
	private int status;
	
	private int borrower_id;
	
	private int borrower_type;
	
	private String pay_way;
	
	private int reward;
	
	private int min_invest;
	
	private String intro;
	
	private String created_at;
	
	private String updated_at;
}
