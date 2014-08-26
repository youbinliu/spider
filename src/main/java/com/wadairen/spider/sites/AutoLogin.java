package com.wadairen.spider.sites;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.browser.BrowserStatus;

public class AutoLogin {
	protected static final Logger logger = Logger.getLogger(AutoLogin.class);
	
	private List<BasicNameValuePair> form;
	
	private String postUrl;
	
	private Browser browser;
	
	
	public AutoLogin(String postUrl,Browser browser){
		this.postUrl = postUrl;
		this.browser = browser;
		this.form = new ArrayList<>();
	}
	
	public void addParam(String name,String value){
		BasicNameValuePair tmp = new BasicNameValuePair(name, value);
		form.add(tmp);
	}
	
	public boolean breakdoor(){
		BrowserResult result = browser.post(postUrl, form);
		if(!result.isOk()){
			logger.error("http post error code:"+result.getStatusCode()+
					" error msg:"+BrowserStatus.getStatusDescription(result.getStatusCode()));
			return false;
		}
		return true;
	}
	
	public boolean verify(String url,String containStr){
		BrowserResult result = browser.get(url);
		return result.isOk() && result.getResponseString().contains(containStr);
	}
	
}
