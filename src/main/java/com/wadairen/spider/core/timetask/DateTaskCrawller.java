package com.wadairen.spider.core.timetask;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public abstract class DateTaskCrawller extends TimerTask {

	private Timer timer;
	private int counter = 0;
	
	public abstract void task();
	
	public void start(int hour,int minter,int second){
		if(timer == null){
			timer = new Timer();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, hour);
		calendar.set(Calendar.MINUTE, minter);
		calendar.set(Calendar.SECOND, second);
		
		Date date = calendar.getTime();
		//如果执行时间小于当前时间，则推迟到第二天执行
		if(date.before(new Date())){
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			date = calendar.getTime();
		}
		
		timer.schedule(this, date, 86400);
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
