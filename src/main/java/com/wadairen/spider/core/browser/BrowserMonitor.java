package com.wadairen.spider.core.browser;

import java.util.concurrent.TimeUnit;

import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.log4j.Logger;

public class BrowserMonitor extends Thread{
	
	protected static final Logger logger = Logger.getLogger(BrowserMonitor.class);
	
	private final PoolingClientConnectionManager connMgr;
	private volatile boolean shutdown = false;
	
	public BrowserMonitor(PoolingClientConnectionManager connMgr) {
		super("Browser Monitor");
		this.connMgr = connMgr;
	}
	
	@Override
	public void run(){
		try {
			while(!shutdown){
				synchronized(this){
					wait(5000);
					//关闭过期链接
					connMgr.closeExpiredConnections();
					//关闭超时链接
					connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
				}
			}
		} catch (InterruptedException e) {
			logger.error("Browser Monitor catch a interrupted exception");
		}
	}
	
	public void shutdown(){
		shutdown = true;
		synchronized(this){
			notifyAll();
		}
	}
}
