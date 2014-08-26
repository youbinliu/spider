package com.wadairen.spider.sites.yirendai;

import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.LoanItem;

public class YrdLoanItem extends LoanItem {
	
	public final static String CREDIT_TYPE_JING = "精英标";
	public final static String CREDIT_TYPE_ZHAI = "债权转让";
	
	public static int TYPE_SUCC_LIST = 1;
	public static int TYPE_SUCC_INFO = 2;
	public static int TYPE_JING_LIST = 3;
	public static int TYPE_JING_INFO = 4;
	public static int TYPE_ZHAI_LIST = 5;
	public static int TYPE_ZHAI_INFO = 6;
	
	public YrdLoanItem(String url) {
		super(url);
		setSiteId(4);
		setSite("宜人贷");
		setBorrowerType(Constant.BORROWER_TYPE_PERSON);
		
	}
	
	private YrdLoanPersonItem borrower;

	public YrdLoanPersonItem getBorrower() {
		return borrower;
	}

	public void setBorrower(YrdLoanPersonItem borrower) {
		this.borrower = borrower;
	}
	
}
