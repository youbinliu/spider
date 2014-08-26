package com.wadairen.spider.sites.itouzi;

import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.LoanItem;

public class ItouziLoanItem extends LoanItem {
	
	public final static String CREDIT_TYPE_ZHI = "企业直投";
	public final static String CREDIT_TYPE_ZHAI = "债权转让";
	
	public ItouziLoanItem(String url) {
		super(url);
		setSiteId(1);
		setSite("爱投资");
		setBorrowerType(Constant.BORROWER_TYPE_COMPANY);
		
	}
	
	private ItouziLoanCompanyItem borrower;

	public ItouziLoanCompanyItem getBorrower() {
		return borrower;
	}

	public void setBorrower(ItouziLoanCompanyItem borrower) {
		this.borrower = borrower;
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
