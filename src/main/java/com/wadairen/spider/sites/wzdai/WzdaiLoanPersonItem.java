package com.wadairen.spider.sites.wzdai;

import com.wadairen.spider.sites.item.LoanPersonItem;

public class WzdaiLoanPersonItem extends LoanPersonItem {

	public WzdaiLoanPersonItem(String url) {
		super(url);
		setSiteId(7);
		setSite("温州贷");
	}
}
