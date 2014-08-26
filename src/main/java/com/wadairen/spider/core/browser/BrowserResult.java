package com.wadairen.spider.core.browser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;

public class BrowserResult {
	protected static final Logger logger = Logger.getLogger(BrowserResult.class);

	protected int statusCode = 0;;
	protected HttpEntity entity = null;
	protected Header[] responseHeaders = null;
	protected String url = null;
	protected String movedToUrl = null;
	protected String responseString = null;

	public String getResponseString() {
		return responseString;
	}

	private void setResponseString(String responseString) {
		this.responseString = responseString;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public HttpEntity getEntity() {
		return entity;
	}

	public void setEntity(HttpEntity entity) {
		this.entity = entity;
		this.setResponseString(this.entityContentToString(this.entity));
	}
	
	public Header[] getResponseHeaders() {
		return responseHeaders;
	}

	public void setResponseHeaders(Header[] responseHeaders) {
		this.responseHeaders = responseHeaders;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMovedToUrl() {
		return movedToUrl;
	}

	public void setMovedToUrl(String movedToUrl) {
		this.movedToUrl = movedToUrl;
	}
	
	private String entityContentToString(HttpEntity httpEntity){
		String responseString = ""; 
		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
			
			String line = "";
	        while((line = rd.readLine()) != null) {  
	        	responseString += line;  
	        }  
		} catch (Exception e) {
			logger.warn(url+" entity content to string has a exception:"+e.getMessage());
		} 		
		return responseString;
	}
	
	public boolean isStrictOk(){
		return (statusCode == HttpStatus.SC_OK);
	}
	
	public boolean isOk(){
		return statusCode < HttpStatus.SC_BAD_REQUEST && statusCode >= HttpStatus.SC_OK;
	}
	
	public String statusLine(){
		return "status code:"+statusCode+" status message:"+BrowserStatus.getStatusDescription(statusCode);
	}
}
