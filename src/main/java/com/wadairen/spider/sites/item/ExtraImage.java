package com.wadairen.spider.sites.item;

public class ExtraImage {
	
	public ExtraImage(String name,String url){
		setName(name);
		setUrl(url);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String name;
	
	private String url;
}
