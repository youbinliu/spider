package com.wadairen.spider.sites.item;

import org.joda.time.DateTime;

import com.wadairen.spider.dao.LoanInfo;
import com.wadairen.spider.dao.bean.LoanModel;

public class LoanItem extends BaseLoanItem {
	
	public LoanItem(String url) {
		super(url);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public int getInterest() {
		return interest;
	}

	public void setInterest(int interest) {
		this.interest = interest;
	}

	public int getInterestType() {
		return interestType;
	}

	public void setInterestType(int interestType) {
		this.interestType = interestType;
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

	public int getPeriodType() {
		return periodType;
	}

	public void setPeriodType(int periodType) {
		this.periodType = periodType;
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

	public int getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}

	public int getBorrowerType() {
		return borrowerType;
	}

	public void setBorrowerType(int borrowerType) {
		this.borrowerType = borrowerType;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public int getReward() {
		return reward;
	}

	public void setReward(int reward) {
		this.reward = reward;
	}

	public int getMinInvest() {
		return minInvest;
	}

	public void setMinInvest(int minInvest) {
		this.minInvest = minInvest;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * 标名
	 */
	protected String title;
	/**
	 * 标类型,净值标，抵押标等
	 */
	protected String creditType;
	/**
	 * 利率
	 */
	protected int interest;
	/**
	 * 利率类型 年：1，月：2，周：3，日：4
	 */
	protected int interestType;
	/**
	 * 借贷金额 
	 */
	protected int amount;
	/**
	 * 借贷期限
	 */
	protected int period;
	/**
	 * 期限单位 年：1，月：2，周：3，日：4
	 */
	protected int periodType;
	/**
	 * 进度
	 */
	protected int progress;
	/**
	 * 状态 1:open,2:close,3:preview,4:fail
	 */
	protected int status;
	/**
	 * 借贷对象ID
	 */
	protected int borrowerId;
	/**
	 * 借贷类型 1:个人，2:企业
	 */
	protected int borrowerType;
	/**
	 * 还款方式
	 */
	protected String payWay;
	/**
	 * 奖励
	 */
	protected int reward;
	/**
	 * 最低投资金额
	 */
	protected int minInvest;
	/**
	 * 标的说明
	 */
	protected String intro;
	
	
	public int store(){
		LoanInfo loanInfo = new LoanInfo();
		LoanModel loan = new LoanModel();
		
		loan.setSite_id(getSiteId());
		loan.setSite(getSite());
		loan.setSource_url(getSourceUrl());
		loan.setSource_url_hash(getSourceUrlHash());
		loan.setAmount(getAmount());
		loan.setBorrower_id(getBorrowerId());
		loan.setBorrower_type(getBorrowerType());
		loan.setCreated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		loan.setCredit_type(getCreditType());
		loan.setInterest(getInterest());
		loan.setInterest_type(getInterestType());
		loan.setIntro(getIntro());
		loan.setMin_invest(getMinInvest());
		loan.setPay_way(getPayWay());
		loan.setPeriod(getPeriod());
		loan.setPeriod_type(getPeriodType());
		loan.setProgress(getProgress());
		loan.setReward(getReward());
		loan.setStatus(getStatus());
		loan.setTitle(getTitle());
		loan.setUpdated_at(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
		
		loanInfo.create(loan);
		
		return loan.getId();
	}
	
	public String toString(){
		return String.format("[siteId:%s] [site:%s] [sourceUrl:%s] [sourceUrlHash:%s] [title:%s] [creditType:%s] "+
					"[interest:%s] [interestType:%s] [amount:%s] [period:%s] [periodType:%s] [progress:%s] "+
					"[status:%s] [payWay:%s] [reward:%s] [minInvest:%s] [intro:%s]",
				getSiteId(),getSite(),getSourceUrl(),getSourceUrlHash(),getTitle(),getCreditType(),
				getInterest(),getInterestType(),getAmount(),getPeriod(),getPeriodType(),getProgress(),
				getStatus(),getPayWay(),getReward(),getMinInvest(),getIntro());
	}
	
}
