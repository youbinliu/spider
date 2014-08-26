package com.wadairen.spider.sites.wzdai;

import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.LoanItem;

public class WzdaiLoanItem extends LoanItem {

	public static String CREDIT_TYPE_DAY = "温州贷天标";
	public static String CREDIT_TYPE_LIU = "流转标";
	public static String CREDIT_TYPE_KUAI = "快速借款标";
	public static String CREDIT_TYPE_MIAO = "秒还借款标";
	public static String CREDIT_TYPE_GEI = "给力借款标";
	public static String CREDIT_TYPE_JING = "净值借款标";
	public static String CREDIT_TYPE_XIN = "信用借款标";
	
	public WzdaiLoanItem(String url) {
		super(url);
		setSiteId(7);
		setSite("温州贷");
		setBorrowerType(Constant.BORROWER_TYPE_PERSON);
		
	}
	
	private WzdaiLoanPersonItem borrower;

	public WzdaiLoanPersonItem getBorrower() {
		return borrower;
	}

	public void setBorrower(WzdaiLoanPersonItem borrower) {
		this.borrower = borrower;
	}
	
}
