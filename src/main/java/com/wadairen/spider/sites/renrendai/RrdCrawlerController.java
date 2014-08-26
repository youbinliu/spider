package com.wadairen.spider.sites.renrendai;

import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.sites.CertificateManager;
import com.wadairen.spider.sites.Constant;

public class RrdCrawlerController extends MultiController {

	private final static Logger logger = Logger.getLogger(RrdCrawlerController.class);
	
	private Browser browser;
	
	public RrdCrawlerController(Config config) {
		super(config);
		browser = new Browser(config,CertificateManager.gen("renrendai.cer"));
		RrdThief thief = new RrdThief(browser);
		if(!thief.breakdoor()){
			System.out.println("thief not break");
			System.exit(0);
		}
		
		if(!thief.verify("http://www.renrendai.com/", "oreoliu")){
			System.out.println("thief verfiy fail");
			System.exit(0);
		}
		BrowserResult result = browser.get("http://www.renrendai.com/");
		if(!result.isOk() || !result.getResponseString().contains("oreoliu")){
			System.out.println("not found oreoliu");
			System.exit(0);
		}
				
		this.setBrowser(browser);

		this.debug(true);
	}
	
	@Override
	public void start() {
		System.out.println("--------------Renrendai Begin----------------");
		
		String seeds = "http://www.renrendai.com/lend/loanList!json.action?pageIndex=%s";
		for (int i = 1; i < 29; i++) {
			String cur = String.format(seeds, i);
			RrdLoanItem item = new RrdLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			RrdCrawlerWorker worker = new RrdCrawlerWorker(this, browser, item);
			this.addWorker(worker);
		}
		
		
	}
	
	public static void main(String[] argvs){
		Config config = new Config();
		config.setPolitenessDelay(3000);
		RrdCrawlerController controller = new RrdCrawlerController(config);
		controller.start();
	}
}
