package com.wadairen.spider.core.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlParser{
	
	public HtmlParser(){
		
	}

	public static Document parse(String responseStr) {
		return Jsoup.parse(responseStr);
	}
	
}
