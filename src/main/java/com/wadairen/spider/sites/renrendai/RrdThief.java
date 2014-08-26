package com.wadairen.spider.sites.renrendai;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.sites.AutoLogin;

public class RrdThief extends AutoLogin{
	
	private final static String POST_URL = "https://www.renrendai.com/j_spring_security_check";
	
	public RrdThief(Browser browser) {
		super(POST_URL, browser);
		this.fillform();
	}
	
	private void fillform(){
		this.addParam("j_username", "13240037200");
		this.addParam("j_password", "aaaaaa");
		this.addParam("targetUrl", "http://www.renrendai.com/");
		this.addParam("returnUrl", "");		
	}
	
}
