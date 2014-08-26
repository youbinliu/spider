package com.wadairen.spider.sites.jimubox;

import java.util.List;

import org.apache.log4j.Logger;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.core.controller.MultiControllerWorker;
import com.wadairen.spider.core.item.BaseItem;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.utils.Utils;

public class JimuCrawlerWorker extends MultiControllerWorker {

	private final static Logger logger = Logger.getLogger(JimuCrawlerWorker.class);
	
	public JimuCrawlerWorker(MultiController controller, Browser browser,
			BaseItem item) {
		super(controller, browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		if(!result.isStrictOk()){
			logger.error(result.statusLine()+" fetch error:"+result.getUrl());
			return;
		}
		
		JimuLoanItem ins = (JimuLoanItem) item;
		
		if(item.getType() == Constant.TYPE_LIST){
			processListPage(ins, result);
		}else if(item.getType() == Constant.TYPE_INFO){
			processInfoPage(ins, result);
		}else{
			logger.error("unexpected type:"+item.getType());
		}
		
	}
	
	private void processListPage(JimuLoanItem item,BrowserResult result){
		List<JimuLoanItem> items = JimuPageParser.listpage(item, result);
		
		if(items.isEmpty()){
			logger.error("not found items:"+result.getUrl());
			return;
		}
		
		for (JimuLoanItem one : items) {
			JimuCrawlerWorker worker = new JimuCrawlerWorker(controller, selfBrowser, one);
			controller.addWorker(worker);
		}
		
	}
	
	private void processInfoPage(JimuLoanItem item,BrowserResult result){
		
		try {
			item = JimuPageParser.infopage(item, result);
		} catch (Exception e) {
			logger.error("parse info page error:"+e.getLocalizedMessage());
			return;
		}
		System.out.println(item.toString()+" person:"+item.getBorrower().toString());
		/*
		if(!Utils.isNullOrEmpty(item)){
			if(!Utils.isNullOrEmpty(item.getBorrower())){
				item.setBorrowerId(item.getBorrower().store());
			}
			item.store();			
		}
		*/
	}

}
