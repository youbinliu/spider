package com.wadairen.spider.sites.yirendai;

import com.wadairen.spider.sites.item.LoanPersonItem;

public class YrdLoanPersonItem extends LoanPersonItem {

	public YrdLoanPersonItem(String url) {
		super(url);
		setSiteId(4);
		setSite("宜人贷");
	}
}
