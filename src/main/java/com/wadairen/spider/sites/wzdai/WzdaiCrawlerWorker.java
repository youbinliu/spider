package com.wadairen.spider.sites.wzdai;

import java.util.List;

import org.apache.log4j.Logger;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.core.controller.MultiControllerWorker;
import com.wadairen.spider.core.item.BaseItem;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.utils.Utils;

public class WzdaiCrawlerWorker extends MultiControllerWorker {

	private final static Logger logger = Logger.getLogger(WzdaiCrawlerWorker.class);
	
	public WzdaiCrawlerWorker(MultiController controller, Browser browser,
			BaseItem item) {
		super(controller, browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		if(!result.isStrictOk()){
			logger.error(result.statusLine()+" fetch error:"+result.getUrl());
			return;
		}
		
		WzdaiLoanItem ins = (WzdaiLoanItem) item;
		
		if(item.getType() == Constant.TYPE_LIST){
			processListPage(ins, result);
		}else if(item.getType() == Constant.TYPE_INFO){
			processInfoPage(ins, result);
		}else{
			logger.error("unexpected type:"+item.getType());
		}
		
	}
	
	private void processListPage(WzdaiLoanItem item,BrowserResult result){
		List<WzdaiLoanItem> items = WzdaiPageParser.listpage(item, result);
		
		if(items.isEmpty()){
			logger.error("not found items:"+result.getUrl());
			return;
		}
		
		for (WzdaiLoanItem one : items) {
			WzdaiCrawlerWorker worker = new WzdaiCrawlerWorker(controller, selfBrowser, one);
			controller.addWorker(worker);
		}
		
	}
	
	private void processInfoPage(WzdaiLoanItem item,BrowserResult result){
		
		try {
			item = WzdaiPageParser.infopage(item, result);
		} catch (Exception e) {
			logger.error("parse info page error:"+e.getLocalizedMessage());
			return;
		}
//		System.out.println(item.toString()+" person:"+item.getBorrower().toString());
		
		if(!Utils.isNullOrEmpty(item)){
			if(!Utils.isNullOrEmpty(item.getBorrower())){
				item.setBorrowerId(item.getBorrower().store());
			}
			item.store();			
		}
	}

}
