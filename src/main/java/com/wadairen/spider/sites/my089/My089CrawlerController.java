package com.wadairen.spider.sites.my089;

import org.apache.log4j.Logger;

import com.wadairen.spider.sites.my089.My089CrawlerController;
import com.wadairen.spider.sites.my089.My089CrawlerWorker;
import com.wadairen.spider.sites.my089.My089LoanItem;
import com.wadairen.spider.sites.my089.My089Thief;
import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.sites.CertificateManager;

public class My089CrawlerController extends MultiController {

	private final static Logger logger = Logger.getLogger(My089CrawlerController.class);
	
	private Browser browser;
	
	public My089CrawlerController(Config config) {
		super(config);
		browser = new Browser(config,CertificateManager.gen("my089.cer"));
		My089Thief thief = new My089Thief(browser);
		if(!thief.breakdoor() || !thief.verify("http://www.my089.com/", "oreoliu")){
			logger.error("break door failed");
			System.exit(0);
		}
		System.out.println("break succ");
		this.setBrowser(browser);

		this.debug(true);
	}
	
	@Override
	public void start() {
		System.out.println("--------------My089 Begin----------------");
		
		String seeds = "http://www.my089.com/Loan/default.aspx?pid=%s";
		for (int i = 1; i < 5; i++) {
			String cur = String.format(seeds, i);
			My089LoanItem item = new My089LoanItem(cur);
			item.setType(My089LoanItem.TYPE_DEFAULT_LIST);
			My089CrawlerWorker worker = new My089CrawlerWorker(this, browser, item);
			this.addWorker(worker);
		}		
		
		seeds = "http://www.my089.com/Loan/Succeed.aspx?pid=%s";
		for (int i = 1; i < 2; i++) {
			String cur = String.format(seeds, i);
			My089LoanItem item = new My089LoanItem(cur);
			item.setType(My089LoanItem.TYPE_SUCC_LIST);
			My089CrawlerWorker worker = new My089CrawlerWorker(this, browser, item);
			this.addWorker(worker);
		}	
		
		
	}
	
	public static void main(String[] argvs){
		Config config = new Config();
		config.setPolitenessDelay(3000);
		My089CrawlerController controller = new My089CrawlerController(config);
		controller.start();
	}
}
