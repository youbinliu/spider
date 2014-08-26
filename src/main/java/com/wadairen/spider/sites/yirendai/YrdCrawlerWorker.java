package com.wadairen.spider.sites.yirendai;

import java.util.List;

import org.apache.log4j.Logger;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.core.controller.MultiControllerWorker;
import com.wadairen.spider.core.item.BaseItem;
import com.wadairen.spider.utils.Utils;

public class YrdCrawlerWorker extends MultiControllerWorker {

	private final static Logger logger = Logger.getLogger(YrdCrawlerWorker.class);
	
	public YrdCrawlerWorker(MultiController controller, Browser browser,
			BaseItem item) {
		super(controller, browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		if(!result.isStrictOk()){
			logger.error(result.statusLine()+" fetch error:"+result.getUrl());
			return;
		}
		
		YrdLoanItem ins = (YrdLoanItem) item;
		
		if(item.getType() == YrdLoanItem.TYPE_JING_LIST ||
				item.getType() == YrdLoanItem.TYPE_SUCC_LIST ||
				item.getType() == YrdLoanItem.TYPE_ZHAI_LIST){
			processListPage(ins, result);
		}else if(item.getType() == YrdLoanItem.TYPE_JING_INFO ||
				item.getType() == YrdLoanItem.TYPE_SUCC_INFO ||
				item.getType() == YrdLoanItem.TYPE_ZHAI_INFO){
			processInfoPage(ins, result);
		}else{
			logger.error("unexpected type:"+item.getType());
		}
		
	}
	
	private void processListPage(YrdLoanItem item,BrowserResult result){
		List<YrdLoanItem> items = YrdPageParser.listpage(item, result);
		
		if(items.isEmpty()){
			logger.error("not found items:"+result.getUrl());
			return;
		}
		
		for (YrdLoanItem one : items) {
			YrdCrawlerWorker worker = new YrdCrawlerWorker(controller, selfBrowser, one);
			controller.addWorker(worker);
		}
		
	}
	
	private void processInfoPage(YrdLoanItem item,BrowserResult result){
		
		try {
			item = YrdPageParser.infopage(item, result);
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
