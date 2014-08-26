package com.wadairen.spider.man;

import org.apache.http.HttpEntity;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.parser.HtmlParser;
import com.wadairen.spider.core.url.URLCanonicalizer;


public class BasicController {
	
	public static void main(String[] args) {
		Config config = new Config();
		Browser browser = new Browser(config);
		
		String url = "http://www.my089.com/Loan/default.aspx?pid=1";
		url = URLCanonicalizer.getCanonicalURL(url);
		System.out.println(url);
		BrowserResult result = browser.get(url);
		
		System.out.println("status code:"+result.getStatusCode());
		
		HttpEntity httpEntity = result.getEntity();
		
		System.out.println("content type:"+httpEntity.getContentType());
		System.out.println("content encoding:"+httpEntity.getContentEncoding());
		//System.out.println(result.getResponseString());
		
		HtmlParser parser = new HtmlParser();
		Document document = parser.parse(result.getResponseString());
		Elements items = document.select(".biao_item");
		
		for (Element element : items) {
			Elements biaoti = element.select(".biaoti");
			Element product = biaoti.get(0).child(0).child(2);
			String productLink = product.attr("href");
			String productName = product.text();
			
			System.out.println("product name:"+productName+" link:"+productLink);
		}
		browser.shutDown();
	}
}
