package com.wadairen.spider.sites.jimubox;

import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.LoanItem;

public class JimuLoanItem extends LoanItem {

	public static String CREDIT_TYPE_QI = "企业经营贷";
	public static String CREDIT_TYPE_FANG = "购房周转贷";
	
	public JimuLoanItem(String url) {
		super(url);
		setSiteId(6);
		setSite("积木盒子");
		setBorrowerType(Constant.BORROWER_TYPE_COMPANY);
		
	}
	
	private JimuLoanCompanyItem borrower;

	public JimuLoanCompanyItem getBorrower() {
		return borrower;
	}

	public void setBorrower(JimuLoanCompanyItem borrower) {
		this.borrower = borrower;
	}
	
}
