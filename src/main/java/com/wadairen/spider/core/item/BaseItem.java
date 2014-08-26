package com.wadairen.spider.core.item;

import java.util.List;

import org.apache.http.message.BasicNameValuePair;


public class BaseItem {
	
	public BaseItem(String url){
		this(url,"",0,true,null);
	}
	
	public BaseItem(String url,String parentUrl){
		this(url,parentUrl,0,true,null);
	}
	
	public BaseItem(String url,String parentUrl,int priority,boolean isGet,List<BasicNameValuePair> params){
		setUrl(url);
		setParentUrl(parentUrl);
		setPriority(priority);
		setHttpMethod(isGet);
		setPostParams(params);
		
		validate();
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentUrl() {
		return parentUrl;
	}

	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isHttpGet() {
		return isGet;
	}

	public void setHttpMethod(boolean isGet) {
		this.isGet = isGet;
	}
	
	public List<BasicNameValuePair> getPostParams() {
		return postParams;
	}

	public void setPostParams(List<BasicNameValuePair> postParams) {
		this.postParams = postParams;
	}
	
	public int getRetryTimes() {
		return retryTimes;
	}

	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	protected int type;

	protected String url;
	
	protected String parentUrl;
	
	protected int priority = 0;
	
	protected int retryTimes = 0;
	
	protected boolean isGet = true;
	
	protected List<BasicNameValuePair> postParams;
	
	private void validate(){
		if(this.url == null || "".equals(this.url.trim())){
			throw new IllegalArgumentException("url must not be null or empty");
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		BaseItem otherUrl = (BaseItem) o;
		return url != null && url.equals(otherUrl.getUrl());

	}

	@Override
	public String toString() {
		return url;
	}
}
