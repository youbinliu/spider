package com.wadairen.spider.sites.renrendai;

import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.LoanItem;

public class RrdLoanItem extends LoanItem {
	
	public final static String CREDIT_TYPE_XIN = "信用认证标";
	public final static String CREDIT_TYPE_SHI = "实地认证标";
	public final static String CREDIT_TYPE_JI = "机构担保标";
	public final static String CREDIT_TYPE_ZHI = "智能理财标";
	
	public RrdLoanItem(String url) {
		super(url);
		setSiteId(2);
		setSite("人人贷");
		setBorrowerType(Constant.BORROWER_TYPE_PERSON);
		
	}
	
	private RrdLoanPersonItem borrower;

	public RrdLoanPersonItem getBorrower() {
		return borrower;
	}

	public void setBorrower(RrdLoanPersonItem borrower) {
		this.borrower = borrower;
	}
	
	
	public int storeWithPerson(){
		if(borrower == null)return 0;
		this.setBorrowerId(this.borrower.store());
		return super.store();
	}
	
	public String toString(){
		return String.format("[siteId:%s] [site:%s] [sourceUrl:%s] [sourceUrlHash:%s] [title:%s] [creditType:%s] "+
					"[interest:%s] [interestType:%s] [amount:%s] [period:%s] [periodType:%s] [progress:%s] "+
					"[status:%s] [payWay:%s] [reward:%s] [minInvest:%s] [intro:%s] [company:%s]",
				getSiteId(),getSite(),getSourceUrl(),getSourceUrlHash(),getTitle(),getCreditType(),
				getInterest(),getInterestType(),getAmount(),getPeriod(),getPeriodType(),getProgress(),
				getStatus(),getPayWay(),getReward(),getMinInvest(),getIntro(),getBorrower());
	}
}
