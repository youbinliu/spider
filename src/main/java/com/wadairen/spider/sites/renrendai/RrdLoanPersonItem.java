package com.wadairen.spider.sites.renrendai;

import com.wadairen.spider.sites.item.LoanPersonItem;

public class RrdLoanPersonItem extends LoanPersonItem {

	public RrdLoanPersonItem(String url) {
		super(url);
		setSiteId(2);
		setSite("人人贷");
	}
}
