package com.wadairen.spider.man;

import java.util.Calendar;

import com.wadairen.spider.core.controller.SecondController;

public class BasicSecondController extends SecondController {

	@Override
	protected void task() {
		System.out.println("the "+times()+" times run at "+(int)(Calendar.getInstance().getTimeInMillis()/1000) );
	}
	
	public static void main(String[] args) {
		BasicSecondController bsc = new BasicSecondController();
		bsc.start(3);
	}
}
