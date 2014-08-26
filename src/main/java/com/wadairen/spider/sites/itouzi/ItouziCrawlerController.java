package com.wadairen.spider.sites.itouzi;

import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.sites.CertificateManager;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.CookieManager;

public class ItouziCrawlerController extends MultiController {

	private final static Logger logger = Logger.getLogger(ItouziCrawlerController.class);
	
	private Browser browser;
	
	public ItouziCrawlerController(Config config) {
		super(config);
		browser = new Browser(config,CertificateManager.gen("itouzi.cer"));
		String cookie = CookieManager.getInstace().getValue("ItouziCookie");
		browser.setHeader("cookie", cookie);

		this.setBrowser(browser);
		
		this.debug(true);
	}
	
	@Override
	public void start() {
		System.out.println("--------------Itouzi Begin----------------");
		
		String seeds = "https://www.itouzi.com/invest/index%s.html";
		for (int i = 1; i < 9; i++) {
			String cur = String.format(seeds, i);
			ItouziLoanItem item = new ItouziLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			item.setCreditType(ItouziLoanItem.CREDIT_TYPE_ZHI);
			ItouziCrawlerWorker worker = new ItouziCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
		seeds = "https://www.itouzi.com/debt/index%s.html";
		for (int i = 1; i < 2; i++) {
			String cur = String.format(seeds, i);
			ItouziLoanItem item = new ItouziLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			item.setCreditType(ItouziLoanItem.CREDIT_TYPE_ZHAI);
			ItouziCrawlerWorker worker = new ItouziCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
	}
	
	public static void main(String[] argvs){
		Config config = new Config();
		ItouziCrawlerController controller = new ItouziCrawlerController(config);
		controller.start();
	}
}
