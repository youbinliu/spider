package com.wadairen.spider.sites.wzdai;

import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.CookieManager;

public class WzdaiCrawlerController extends MultiController {

	private final static Logger logger = Logger.getLogger(WzdaiCrawlerController.class);
	
	private Browser browser;
	
	public WzdaiCrawlerController(Config config) {
		super(config);
		browser = new Browser(config);
		String cookie = CookieManager.getInstace().getValue("WzdaiCookie");
		browser.setHeader("cookie", cookie);
		
		BrowserResult result = browser.get("http://www.wzdai.com/index.html");
		if(!result.isStrictOk() || !result.getResponseString().contains("oreoliu")){
			System.out.println("break failed");
		}
		
		System.out.println("break succ");
		
		this.setBrowser(browser);
		
		this.debug(true);
	}
	
	@Override
	public void start() {
		System.out.println("--------------Wzdai Begin----------------");
		
		String seeds = "http://www.wzdai.com/invest/index.html?status=1&page=%s";
		for (int i = 1; i < 122; i++) {
			String cur = String.format(seeds, i*10);
			WzdaiLoanItem item = new WzdaiLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			WzdaiCrawlerWorker worker = new WzdaiCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
		seeds = "http://www.wzdai.com/invest/index.html?status=10&page=%s";
		for (int i = 0; i < 122; i++) {
			String cur = String.format(seeds, i*10);
			WzdaiLoanItem item = new WzdaiLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			WzdaiCrawlerWorker worker = new WzdaiCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
		seeds = "http://www.wzdai.com/invest/index.html?status=1&page=%s";
		for (int i = 0; i < 9; i++) {
			String cur = String.format(seeds, i*10);
			WzdaiLoanItem item = new WzdaiLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			WzdaiCrawlerWorker worker = new WzdaiCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
	}
	
	public static void main(String[] argvs){
		Config config = new Config();
		WzdaiCrawlerController controller = new WzdaiCrawlerController(config);
		controller.start();
	}
}
