package com.wadairen.spider.sites.yooli;

import com.wadairen.spider.sites.item.LoanPersonItem;

public class YooliLoanPersonItem extends LoanPersonItem {

	public YooliLoanPersonItem(String url) {
		super(url);
		setSiteId(3);
		setSite("有利网");
	}
}
