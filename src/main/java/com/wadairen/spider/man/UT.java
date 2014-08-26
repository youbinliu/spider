package com.wadairen.spider.man;

import org.apache.http.Header;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.sites.CertificateManager;
import com.wadairen.spider.sites.CookieManager;


public class UT {

	public static void main(String[] args) {
		Config config = new Config();
		config.setProxyHost("127.0.0.1");
		config.setProxyPort(8080);
		Browser browser = new Browser(config,CertificateManager.gen("fiddler.cer"));
		String cookie = CookieManager.getInstace().getValue("ItouziCookie");
		//System.out.println(name+":"+value);
		browser.setHeader("Cookie",cookie);
		BrowserResult result = browser.get("https://www.itouzi.com/invest/54744653434a6868435a343d");
		Header[] headers = result.getResponseHeaders();
		
		for (int i = 0; i < headers.length; i++) {
			System.out.println(headers[i].getName()+":"+headers[i].getValue());
		}
		
		System.out.println(result.getResponseString());
	}

}
