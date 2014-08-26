package com.wadairen.spider.sites.my089;

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

public class My089CrawlerWorker extends MultiControllerWorker {

	private final static Logger logger = Logger.getLogger(My089CrawlerWorker.class);
	
	public My089CrawlerWorker(MultiController controller, Browser browser,
			BaseItem item) {
		super(controller, browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		
		if(!result.isStrictOk()){
			if(result.getStatusCode() == HttpStatus.SC_MOVED_TEMPORARILY){
				logger.error(result.statusLine()+" moved to:"+result.getMovedToUrl());
			}else{
				logger.error(result.statusLine()+" fetch error:"+result.getUrl());
			}
			return;
		}
		
		My089LoanItem ins = null;
		if(item instanceof My089LoanItem){
			ins = (My089LoanItem) item;
		}else{
			logger.error("unexpected item:"+item.getType()+" "+item.getUrl());
		}
		
		if(item.getType() == My089LoanItem.TYPE_DEFAULT_LIST || item.getType() == My089LoanItem.TYPE_SUCC_LIST){
			processListPage(ins,result);
		}else if(item.getType() == My089LoanItem.TYPE_DEFAULT_INFO || My089LoanItem.TYPE_SUCC_INFO == item.getType()){
			processInfoPage(ins,result);
		}else{
			logger.error("unexpected type:"+item.getType());
		}
		
	}
	
	private void processListPage(My089LoanItem item,BrowserResult result){
		ArrayList<My089LoanItem> loans = null;
		try {
			if(item.getType() == My089LoanItem.TYPE_DEFAULT_LIST){
				loans = My089PageParser.listDefaultPage(result);
			}else if(item.getType() == My089LoanItem.TYPE_SUCC_LIST){
				loans = My089PageParser.listSuccPage(result);
			}
			
		} catch (Exception e) {
			logger.warn("parse list exception:"+e.getMessage());
			return;
		}
		
		
		My089CrawlerWorker worker = null;
		for (My089LoanItem ins : loans) {
			//System.out.println(ins.toString());
			worker = new My089CrawlerWorker(controller, selfBrowser, ins);
			controller.addWorker(worker);
		}
	
	}
	
	private void processInfoPage(My089LoanItem item,BrowserResult result){
		
		try {
			if(item.getType() == My089LoanItem.TYPE_DEFAULT_INFO){
				item = My089PageParser.infoDefaultPage(item, result);
			}else if(item.getType() == My089LoanItem.TYPE_SUCC_INFO){
				item = My089PageParser.infoDefaultPage(item, result);
			}
			
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
