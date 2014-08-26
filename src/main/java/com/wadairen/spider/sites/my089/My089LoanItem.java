package com.wadairen.spider.sites.my089;

import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.LoanItem;

public class My089LoanItem extends LoanItem {
	
	public static int TYPE_DEFAULT_LIST = 0;
	public static int TYPE_DEFAULT_INFO = 1;
	public static int TYPE_SUCC_LIST = 2;
	public static int TYPE_SUCC_INFO = 3;
	
	public My089LoanItem(String url) {
		super(url);
		setSiteId(5);
		setSite("红岭创投");
		setBorrowerType(Constant.BORROWER_TYPE_PERSON);
		
	}
	
	private My089LoanPersonItem borrower;

	public My089LoanPersonItem getBorrower() {
		return borrower;
	}

	public void setBorrower(My089LoanPersonItem borrower) {
		this.borrower = borrower;
	}
	
	
	public int storeWithPerson(){
		if(borrower == null)return 0;
		this.setBorrowerId(this.borrower.store());
		return super.store();
	}
	

}
