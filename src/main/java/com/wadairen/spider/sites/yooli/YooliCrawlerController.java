package com.wadairen.spider.sites.yooli;

import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.sites.CertificateManager;
import com.wadairen.spider.sites.Constant;

public class YooliCrawlerController extends MultiController {

	private final static Logger logger = Logger.getLogger(YooliCrawlerController.class);
	
	private Browser browser;
	
	public YooliCrawlerController(Config config) {
		super(config);
		browser = new Browser(config,CertificateManager.gen("yooli.cer"));
		YooliThief thief = new YooliThief(browser);
		if(!thief.breakdoor() || !thief.verify("http://www.yooli.com/", "oreoliu")){
			logger.error("break door failed");
			System.exit(0);
		}
		
		this.setBrowser(browser);

		this.debug(true);
	}
	
	@Override
	public void start() {
		System.out.println("--------------Yooli Begin----------------");
		
		String seeds = "http://www.yooli.com/yuexitong/page/%s.html";
		for (int i = 1; i < 15; i++) {
			String cur = String.format(seeds, i);
			YooliLoanItem item = new YooliLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			YooliCrawlerWorker worker = new YooliCrawlerWorker(this, browser, item);
			this.addWorker(worker);
		}		
		
	}
	
	public static void main(String[] argvs){
		Config config = new Config();
		config.setPolitenessDelay(3000);
		YooliCrawlerController controller = new YooliCrawlerController(config);
		controller.start();
	}
}
