package com.wadairen.spider.sites.jimubox;

import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.sites.CertificateManager;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.CookieManager;

public class JimuCrawlerController extends MultiController {

	private final static Logger logger = Logger.getLogger(JimuCrawlerController.class);
	
	private Browser browser;
	
	public JimuCrawlerController(Config config) {
		super(config);
		browser = new Browser(config,CertificateManager.gen("jimu.cer"));
		String cookie = CookieManager.getInstace().getValue("JimuboxCookie");
		browser.setHeader("cookie", cookie);
		
		BrowserResult result = browser.get("https://www.jimubox.com/");
		if(!result.isStrictOk() || !result.getResponseString().contains("oreoliu")){
			System.out.println("break failed");
			System.exit(0);
		}
		
		System.out.println("break succ");
		
		this.setBrowser(browser);
		
		this.debug(true);
	}
	
	@Override
	public void start() {
		System.out.println("--------------Itouzi Begin----------------");
		
		String seeds = "https://www.jimubox.com/Project/List?Page=%s";
		for (int i = 1; i < 19; i++) {
			String cur = String.format(seeds, i*10);
			JimuLoanItem item = new JimuLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			JimuCrawlerWorker worker = new JimuCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
		
	}
	
	public static void main(String[] argvs){
		Config config = new Config();
		JimuCrawlerController controller = new JimuCrawlerController(config);
		controller.start();
	}
}
