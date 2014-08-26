package com.wadairen.spider.man;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.controller.MultiController;
import com.wadairen.spider.core.item.BaseItem;

public class BasicMultiController extends MultiController {

	public BasicMultiController(Config config) {
		super(config);
	}

	@Override
	public void start() {
		debug(true);
		
		String seed = "http://www.my089.com/Loan/Succeed.aspx?pid=";
		
		for (int i = 1; i <= 100; i++) {
			BaseItem item = new BaseItem(seed+i);
			BasicMultiControllerWorker worker = new BasicMultiControllerWorker(this,browser(), item);
			this.addWorker(worker);
		}
	}
	
	public static void main(String[] args) {
		Config config = new Config();
		config.setProxyHost("127.0.0.1");
		config.setProxyPort(8888);
		BasicMultiController controller = new BasicMultiController(config);
		controller.start();
	}
	
}
