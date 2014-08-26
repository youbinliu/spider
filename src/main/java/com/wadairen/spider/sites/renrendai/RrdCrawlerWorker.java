package com.wadairen.spider.sites.renrendai;

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

public class RrdCrawlerWorker extends MultiControllerWorker {

	private final static Logger logger = Logger.getLogger(RrdCrawlerWorker.class);
	
	public RrdCrawlerWorker(MultiController controller, Browser browser,
			BaseItem item) {
		super(controller, browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		if(!result.isStrictOk()){
			if(result.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
				
				logger.error(result.statusLine()+" moved to:"+result.getMovedToUrl()+" begin to sleep");
				this.sleep(60);
				if(item instanceof RrdLoanItem){
					RrdLoanItem ins = (RrdLoanItem) item;
					//重试
					ins.setRetryTimes(ins.getRetryTimes()+1);
					if(ins.getRetryTimes() > 3){
						logger.error("hit max retry times "+item.getUrl());
						return;
					}

					RrdCrawlerWorker worker = new RrdCrawlerWorker(controller, selfBrowser, item);
					controller.addWorker(worker);
				}
			}else{
				logger.error(result.statusLine()+" fetch error:"+result.getUrl());
			}
			
			return;
		}
		
		if(item.getType() == Constant.TYPE_LIST){
			processListPage(result);
		}else if(item.getType() == Constant.TYPE_INFO){
			if(item instanceof RrdLoanItem){
				RrdLoanItem ins = (RrdLoanItem) item;
				processInfoPage(ins,result);
			}else{
				logger.error("unexpected item:"+item.getType()+" "+item.getUrl());
			}
			
		}else{
			logger.error("unexpected type:"+item.getType());
		}
		
		
		
	}
	
	private void processListPage(BrowserResult result){
		
		ArrayList<RrdLoanItem> loans = RrdPageParser.listpage(result);
		
		RrdCrawlerWorker worker;
		for (RrdLoanItem rrdLoanItem : loans) {
			worker = new RrdCrawlerWorker(controller, selfBrowser, rrdLoanItem);
			controller.addWorker(worker);
		}
	
	}
	
	private void processInfoPage(RrdLoanItem item,BrowserResult result){
		try {
			item = RrdPageParser.infopage(item, result);
		} catch (Exception e) {
			logger.error(item.getUrl()+" "+e.getMessage());
			return;
		}
		
		
		if(Utils.isNullOrEmpty(item)){
			logger.error("parse info page fail");
			return;
		}
		
		
		System.out.println(item.toString());
		//item.storeWithPerson();
	}

}
