package com.wadairen.spider.man;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.core.controller.MultiControllerWorker;
import com.wadairen.spider.core.item.BaseItem;

public class BasicMultiControllerWorker extends MultiControllerWorker {

	private static int count = 0;
	public BasicMultiControllerWorker(MultiController controller,Browser browser, BaseItem item) {
		super(controller,browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		count++;
		if(result.isOk()){
			System.out.println(count+" "+result.statusLine()+" succ finished "+item.getUrl());
		}else{
			System.out.println(count+" "+result.statusLine()+" fail finished "+item.getUrl());
		}
		System.out.println(count+" "+result.getResponseString());
		
		if(item.getRetryTimes() < 3){
			item.setRetryTimes(item.getRetryTimes()+1);
			
			controller.addWorker(new BasicMultiControllerWorker(controller, selfBrowser, item));
		}
	}

}
