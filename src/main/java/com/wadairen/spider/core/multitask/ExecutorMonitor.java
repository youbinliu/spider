package com.wadairen.spider.core.multitask;

import org.apache.log4j.Logger;

public class ExecutorMonitor extends Thread {
	
	private final static Logger logger = Logger.getLogger(ExecutorMonitor.class);
	
	private volatile boolean shutdown = false;
	
	private final  CommonExecutor executor;
	
	public ExecutorMonitor(CommonExecutor executor){
		super("Executor Monitor");
		this.executor = executor;
	}
	
	@Override
	public void run(){
		try {
			while(!shutdown){
				synchronized (this) {
					if(executor.getAliveCount() > 0 || !executor.queue.isEmpty()){
						if(executor.isDebug()){
							logger.info(" thread pool number of executors finished:"+executor.getCompletedWorkerCount());
							logger.info(" thread pool number of executors alive:"+executor.getAliveCount());
							logger.info(" thread queue size:"+executor.queue.size());
						}
						
						wait(5000);
						
					} else {
						if(executor.isDebug()){
							logger.info("will to shutdown execute pool");
						}
						shutdown = true;
						executor.shutdown();
					}
					
				}
			}
		}catch(InterruptedException e){
			logger.error("Executor Monitor catch a interrupted exception");
		}
	}
	
	public void shutdown(){
		shutdown = true;
		synchronized(this){
			notifyAll();
		}
	}
}
