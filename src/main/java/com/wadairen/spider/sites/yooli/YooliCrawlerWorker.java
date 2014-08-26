package com.wadairen.spider.sites.yooli;

import java.util.ArrayList;

import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.core.controller.MultiControllerWorker;
import com.wadairen.spider.core.item.BaseItem;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.utils.Utils;

public class YooliCrawlerWorker extends MultiControllerWorker {

	private final static Logger logger = Logger.getLogger(YooliCrawlerWorker.class);
	
	public YooliCrawlerWorker(MultiController controller, Browser browser,
			BaseItem item) {
		super(controller, browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		
		if(!result.isStrictOk()){
			if(result.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
				logger.error(result.statusLine()+" moved to:"+result.getMovedToUrl()+" begin to sleep");
			}else{
				logger.error(result.statusLine()+" fetch error:"+result.getUrl());
			}
			return;
		}
		
		if(item.getType() == Constant.TYPE_LIST){
			processListPage(result);
		}else if(item.getType() == Constant.TYPE_INFO){
			if(item instanceof YooliLoanItem){
				YooliLoanItem ins = (YooliLoanItem) item;
				processInfoPage(ins,result);
			}else{
				logger.error("unexpected item:"+item.getType()+" "+item.getUrl());
			}
			
		}else{
			logger.error("unexpected type:"+item.getType());
		}
		
	}
	
	private void processListPage(BrowserResult result){
		
		ArrayList<YooliLoanItem> loans = YooliPageParser.listpage(result);
		
		YooliCrawlerWorker worker;
		for (YooliLoanItem rrdLoanItem : loans) {
			worker = new YooliCrawlerWorker(controller, selfBrowser, rrdLoanItem);
			controller.addWorker(worker);
		}
	
	}
	
	private void processInfoPage(YooliLoanItem item,BrowserResult result){
		try {
			item = YooliPageParser.infopage(item, result);
		} catch (Exception e) {
			logger.error(item.getUrl()+" "+e.getMessage());
			return;
		}
		
		if(Utils.isNullOrEmpty(item)){
			logger.error("parse info page fail");
			return;
		}
		
		
		//System.out.println(item.getBorrower().toString());
		item.storeWithPerson();
	}

}
