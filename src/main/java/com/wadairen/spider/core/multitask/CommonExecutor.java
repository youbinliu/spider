package com.wadairen.spider.core.multitask;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;

public class CommonExecutor {
	
	private final static Logger logger = Logger.getLogger(CommonExecutor.class);
	
	protected ThreadPoolExecutor executor;
	
	protected BlockingQueue<Runnable> queue;
	
	protected ExecutorMonitor monitor;
	
	protected ExecutorCounter counter;
	
	protected Config config;
	
	protected boolean debug = false;
	
	public CommonExecutor(Config config){
		this.config = config;		
		queue = new BlockingTaskQueue(config.getThreadDelayInMilliseconds());
		
		initExecutor();
	}
	
	public CommonExecutor(Config config,BlockingDeque<Runnable> queue){
		this.config = config;		
		this.queue = queue;
		
		initExecutor();
	}
	
	private void initExecutor(){
		executor = new ThreadPoolExecutor(
				config.getThreadCorePoolSize(),
				config.getThreadMaxPoolSize(), 
				config.getThreadKeepAliveSeconds(), 
				TimeUnit.SECONDS,
				this.queue);
		
		executor.setRejectedExecutionHandler(new RejectedExecutionHandler() {
			
			@Override
			public void rejectedExecution(Runnable runnable, ThreadPoolExecutor executor) {
				
				try {
					executor.getQueue().put(runnable);
				} catch (InterruptedException e) {
					logger.error("put runable error:"+e.getMessage());
				}
			}
		});
		
		counter = new ExecutorCounter();
	}
	
	public void add(CommonWorker worker){
		try {
			
			worker.setCounter(counter);
			executor.execute(worker);
			
			if(monitor == null){//开始监控线程池
				monitor = new ExecutorMonitor(this);
				monitor.start();
			}
		} catch (Exception e) {
			logger.error("work execute error:"+e.getMessage());
		}
		
	}
	
	public void shutdown(){
		executor.shutdown();
	}
	
	public void setDebug(boolean isdebug){
		this.debug = isdebug;
	}
	
	public boolean isDebug(){
		return this.debug;
	}
	
	public ExecutorCounter getCounter(){
		return counter;
	}
	
	public long getCompletedWorkerCount(){
		return executor.getCompletedTaskCount();
	}
	
	public long getAliveCount(){
		return counter.value();//executor.getActiveCount()
	}
	
	protected void sleep(long seconds) {
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			throw new RuntimeException("main thread died. ", e);
		}

	}

}
