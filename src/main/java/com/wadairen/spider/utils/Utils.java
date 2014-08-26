package com.wadairen.spider.utils;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;

public class Utils {
	
	public static boolean isNullOrEmpty(Object o){
		if(o instanceof String){
			String so = (String) o;
			return so == null || "".equals(so.trim());
		}
		
		if(o instanceof List<?>){
			List<?> lo = (List<?>) o;
			return lo == null || lo.isEmpty();
		}
		
		if(o instanceof Map<?, ?>){
			Map<?, ?> mo = (Map<?, ?>) o;
			return mo == null || mo.isEmpty();
		}
		
		if(o instanceof Set<?>){
			Set<?> setObj = (Set<?>) o;
			return null == setObj || setObj.isEmpty();
		}
		
		return null == o;
	}
	
	public static String urlHash(String url){
		return DigestUtils.md5Hex(url);
	}
	
	public static int parseMoney(String money){
		if(isNullOrEmpty(money))return 0;
		
		money = money.replace("￥", "");
		money = money.replace(",", "");
		int moneyValue = Float.valueOf(money).intValue();
		return moneyValue;
	}
	
	public static int parsePercent(String percent){
		if(isNullOrEmpty(percent))return 0;
		
		percent = percent.trim();
		percent = percent.replace("%", "");
		
		Float f = Float.valueOf(percent) * 100;
		return f.intValue();
	}
	
	public static void main(String[] argvs){
		System.out.println(urlHash("http://www.baidu.com"));//bfa89e563d9509fbc5c6503dd50faf2e
	}
	
	
}
