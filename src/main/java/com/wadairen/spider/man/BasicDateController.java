package com.wadairen.spider.man;

import java.util.Calendar;

import com.wadairen.spider.core.timetask.DateTaskCrawller;

public class BasicDateController extends DateTaskCrawller {

	@Override
	public void task() {
		Calendar calendar = Calendar.getInstance();
		System.out.println("the "+times()+" times run at " + 
				calendar.get(Calendar.HOUR_OF_DAY)+":"+
				calendar.get(Calendar.MINUTE)+ ":" +
				calendar.get(Calendar.SECOND));

	}

	public static void main(String[] args) {
		BasicDateController bdc = new BasicDateController();
		bdc.start(1, 53, 0);

	}

}
