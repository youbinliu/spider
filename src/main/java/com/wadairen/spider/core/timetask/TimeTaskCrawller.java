package com.wadairen.spider.core.timetask;

import java.util.Timer;
import java.util.TimerTask;

public abstract class TimeTaskCrawller extends TimerTask {

	protected static Timer timer;
	private int counter = 0;
	
	protected abstract void task();
	
	public void start(int seconds){
		if(timer == null){
			timer = new Timer();
		}
		
		timer.schedule(this,0,seconds*1000);
	}
	
	@Override
	public void run() {
		counter++;
		task();
	}
	
	public int times(){
		return counter;
	}

}
