package com.wadairen.spider.core.controller;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.item.BaseItem;
import com.wadairen.spider.core.multitask.CommonWorker;

public abstract class MultiControllerWorker extends CommonWorker {
	
	protected MultiController controller;
	
	protected  Browser selfBrowser;
	
	protected  BaseItem selfItem;
	
	protected  BrowserResult selfResult;
	
	public abstract void afterFetch(BaseItem item,BrowserResult result);
	
	public MultiControllerWorker(MultiController controller,Browser browser,BaseItem item){
		//TODO generate a hash name for thread
		super(item.getUrl());
		selfBrowser = browser;
		selfItem = item;
		this.controller = controller;
	}

	@Override
	public boolean start() {
		if(selfItem.isHttpGet()){
			selfResult = selfBrowser.get(selfItem.getUrl());
		}else{
			selfResult = selfBrowser.post(selfItem.getUrl(), selfItem.getPostParams());
		}
		
		afterFetch(selfItem, selfResult);
		
		return true;
	}

}
