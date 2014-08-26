package com.wadairen.spider.core.controller;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.multitask.CommonExecutor;
import com.wadairen.spider.core.multitask.CommonWorker;


public abstract class MultiController {
	  
	private Browser selfbrowser;
	
	private Config globalConfig;
	
	private CommonExecutor executor;
	
	public abstract void start();
	
	public MultiController(Config config){
		globalConfig = config;
		executor = new CommonExecutor(globalConfig);
	}
	
	public void debug(boolean isdebug){
		executor.setDebug(isdebug);
	}
	
	protected synchronized Browser browser(){
		if(selfbrowser == null){
			selfbrowser = new Browser(globalConfig);
		}
		
		return selfbrowser;
	}
	
	public void setBrowser(Browser browser){
		selfbrowser = browser;
	}
	
	public void addWorker(CommonWorker worker){
		executor.add(worker);
	}
	
	public Config getConfig(){
		return globalConfig;
	}
	
}
