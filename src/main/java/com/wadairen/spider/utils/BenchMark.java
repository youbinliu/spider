package com.wadairen.spider.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

public class BenchMark {

	private static HashMap<String , Long> marks = new HashMap<>();
	
	private final static String StartPre = "start#";
	
	private final static String EndPre = "end#";
	
	public static void Start(String tag){
		tag = StartPre+tag;
		marks.put(tag, DateTime.now().getMillis());
	}
	
	public static void End(String tag){
		tag = EndPre+tag;
		marks.put(tag, DateTime.now().getMillis());
	}
	
	public static String Show(String tag){
		String startTag = StartPre+tag;
		if(!marks.containsKey(startTag)){
			throw new IllegalArgumentException("start tag is not complete");
		}
		
		String endTag = EndPre+tag;
		if(!marks.containsKey(endTag)){
			throw new IllegalArgumentException("end tag is not complete");
		}
		
		float cost_s = (float)(marks.get(endTag) - marks.get(startTag))/1000;
		float start_s = ((float)marks.get(startTag)) / 1000;
		float end_s = ((float)marks.get(startTag)) / 1000;
		
		String preformance = String.format("start:%.2f end:%.2f cost:%.2f tag:%s", start_s,end_s,cost_s,tag);
		
		//System.out.println(preformance);
		
		return preformance;
	}
	
	public static List<String> ShowAll(){
		List<String> all = new ArrayList<>();
		
		if(marks.isEmpty())return all;
		
		Set<String> tags = marks.keySet();
		for (String tag : tags) {
			all.add(Show(tag));
		}
		
		return all;
	}
	
	
}
