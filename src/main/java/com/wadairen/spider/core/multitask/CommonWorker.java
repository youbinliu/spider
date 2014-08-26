package com.wadairen.spider.core.multitask;

public abstract class CommonWorker implements Runnable {
	
	private ExecutorCounter counter;
	
	private String name;
	
	public CommonWorker(){
		this("");
	}
	
	public CommonWorker(String name){
		this.name = name;
	}
	
	public abstract boolean start();
	
	@Override
	public void run() {
		incre();
		start();
		decre();
		
	}
	
	public void setCounter(ExecutorCounter counter){
		this.counter = counter;
	}
	
	public void incre(){
		if(counter != null){
			counter.increase();
		}
	}
	
	public void decre(){
		if(counter != null){
			counter.decrease();
		}
	}
	
	@Override
	public String toString(){
		return String.format("name=[%s] counter=[%s]",this.name,this.counter.value());
	}
	
	public void sleep(int seconds){
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			
		}
	}

}
