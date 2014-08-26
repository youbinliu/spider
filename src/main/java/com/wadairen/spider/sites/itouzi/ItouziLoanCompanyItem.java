package com.wadairen.spider.sites.itouzi;

import com.wadairen.spider.sites.item.LoanCompanyItem;

public class ItouziLoanCompanyItem extends LoanCompanyItem {

	public ItouziLoanCompanyItem(String url) {
		super(url);
		setSiteId(1);
		setSite("爱投资");
	}
}
