package com.wadairen.spider.sites.itouzi;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.core.controller.MultiControllerWorker;
import com.wadairen.spider.core.item.BaseItem;
import com.wadairen.spider.core.url.URLResolver;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.utils.Utils;

public class ItouziCrawlerWorker extends MultiControllerWorker {

	private final static Logger logger = Logger.getLogger(ItouziCrawlerWorker.class);
	
	public ItouziCrawlerWorker(MultiController controller, Browser browser,
			BaseItem item) {
		super(controller, browser, item);
	}

	@Override
	public void afterFetch(BaseItem item, BrowserResult result) {
		if(!result.isStrictOk()){
			logger.error(result.statusLine()+" fetch error:"+result.getUrl());
			//item.setRetryTimes(item.getRetryTimes()+1);
			//controller.addWorker(new ItouziCrawlerWorker(controller, selfBrowser, item));
			return;
		}
		
		ItouziLoanItem ins = (ItouziLoanItem) item;
		
		if(item.getType() == Constant.TYPE_LIST){
			processListPage(ins, result);
		}else if(item.getType() == Constant.TYPE_INFO){
			processInfoPage(ins, result);
		}else{
			logger.error("unexpected type:"+item.getType());
		}
		
		
		
	}
	
	private void processListPage(ItouziLoanItem item,BrowserResult result){
		Document doc = Jsoup.parse(result.getResponseString());
		Elements titles = doc.select(".title");
		
		if(titles.isEmpty()){
			logger.error("current page does not find titles:"+result.getUrl());
			return;
		}
		
		for (Element el : titles) {
			String url = el.child(0).attr("href");
			
			if(Utils.isNullOrEmpty(url)){
				logger.error("not find href");
				continue;
			}
			
			url = URLResolver.resolveUrl(result.getUrl(), url);
			
			ItouziLoanItem i = new ItouziLoanItem(url);
			i.setType(Constant.TYPE_INFO);
			i.setCreditType(item.getCreditType());
			ItouziCrawlerWorker worker = new ItouziCrawlerWorker(controller, selfBrowser, i);
			controller.addWorker(worker);
		}
	}
	
	private void processInfoPage(ItouziLoanItem item,BrowserResult result){
		
		try {
			item = ItouziPageParser.infopage(item, result);
		} catch (Exception e) {
			logger.error("parse info page error:"+e.getLocalizedMessage());
			return;
		}
		
		if(!Utils.isNullOrEmpty(item)){
			item.setBorrowerType(Constant.BORROWER_TYPE_COMPANY);
			if(!Utils.isNullOrEmpty(item.getBorrower())){
				item.setBorrowerId(item.getBorrower().store());
			}
			item.store();
			
		}
	}

}
