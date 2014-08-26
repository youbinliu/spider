package com.wadairen.spider.sites.yirendai;

import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.CookieManager;

public class YrdCrawlerController extends MultiController {

	private final static Logger logger = Logger.getLogger(YrdCrawlerController.class);
	
	private Browser browser;
	
	public YrdCrawlerController(Config config) {
		super(config);
		browser = new Browser(config);
		String cookie = CookieManager.getInstace().getValue("YirendaiCookie");
		browser.setHeader("cookie", cookie);

		this.setBrowser(browser);
		
		this.debug(true);
	}
	
	@Override
	public void start() {
		System.out.println("--------------Itouzi Begin----------------");
		
		String seeds = "http://www.yirendai.com/LenderInvest/applyInfoListPage.action?pager.offset=%s&isJYD=1";
		for (int i = 0; i < 9; i++) {
			String cur = String.format(seeds, i*10);
			YrdLoanItem item = new YrdLoanItem(cur);
			item.setType(YrdLoanItem.TYPE_JING_LIST);
			YrdCrawlerWorker worker = new YrdCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
		seeds = "http://www.yirendai.com/LenderApplyListAction/applyInfoSuccList.action?pager.offset=%s";
		for (int i = 0; i < 10; i++) {
			String cur = String.format(seeds, i*10);
			YrdLoanItem item = new YrdLoanItem(cur);
			item.setType(YrdLoanItem.TYPE_SUCC_LIST);
			YrdCrawlerWorker worker = new YrdCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
		seeds = "http://www.yirendai.com/transferLoan/getTransferingLoanPoolList.action";
		for (int i = 1; i < 1; i++) {
			String cur = String.format(seeds, i*10);
			YrdLoanItem item = new YrdLoanItem(cur);
			item.setType(Constant.TYPE_LIST);
			YrdCrawlerWorker worker = new YrdCrawlerWorker(this, browser, item);
			
			this.addWorker(worker);
		}
		
	}
	
	public static void main(String[] argvs){
		Config config = new Config();
		YrdCrawlerController controller = new YrdCrawlerController(config);
		controller.start();
	}
}
