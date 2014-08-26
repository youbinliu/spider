package com.wadairen.spider.core.controller;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.timetask.TimeTaskCrawller;

public abstract class SecondController extends TimeTaskCrawller {
	
	protected static Browser selfbrowser;
	
	protected static Config selfconfig;
	
	protected synchronized Browser browser(Config config){
		if(selfbrowser == null){
			selfconfig = config;
			selfbrowser = new Browser(selfconfig);
		}
		
		return selfbrowser;
	}
	
	public void setBrowser(Browser browser){
		selfbrowser = browser;
	}
	
	public Config getConfig(){
		return selfconfig;
	}
	
	public int executeTimes(){
		return times();
	}
	
	protected void sleep(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (Exception e) {
			
		}
	}
}
