package com.wadairen.spider.sites.yooli;

import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.LoanItem;

public class YooliLoanItem extends LoanItem {
	
	public YooliLoanItem(String url) {
		super(url);
		setSiteId(3);
		setSite("有利网");
		setBorrowerType(Constant.BORROWER_TYPE_PERSON);
		
	}
	
	private YooliLoanPersonItem borrower;

	public YooliLoanPersonItem getBorrower() {
		return borrower;
	}

	public void setBorrower(YooliLoanPersonItem borrower) {
		this.borrower = borrower;
	}
	
	
	public int storeWithPerson(){
		if(borrower == null)return 0;
		this.setBorrowerId(this.borrower.store());
		return super.store();
	}
	

}
