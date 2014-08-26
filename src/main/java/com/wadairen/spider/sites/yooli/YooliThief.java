package com.wadairen.spider.sites.yooli;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.sites.AutoLogin;

public class YooliThief extends AutoLogin{
	
	private final static String POST_URL = "https://www.yooli.com/secure/newLogin.action";
	
	public YooliThief(Browser browser) {
		super(POST_URL, browser);
		this.fillform();
	}
	
	private void fillform(){
		this.addParam("nickName", "oreoliu");
		this.addParam("password", "aaaaaa");
		this.addParam("verifycode", "");
		this.addParam("chkboxautologin", "true");		
	}
	
}
