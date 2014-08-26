package com.wadairen.spider.sites.my089;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.url.URLResolver;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.utils.Utils;

public class My089PageParser {

	private final static Logger logger = Logger.getLogger(My089PageParser.class);
	
	public static My089LoanItem infoDefaultPage(My089LoanItem item,BrowserResult result){
		Document doc = Jsoup.parse(result.getResponseString());
		
		String city = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_userInfo_lblHabitancy").text();
		String marriage = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblMarriage").text();
		String degree = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblEducation").text();
		String income = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblMonthlyWages").text();
		income = Utils.parseMoney(income) + "";
		
		String asset = "";
		asset += "社保:" + doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblSocso").text();
		asset += "住房条件:" + doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblHousing").text();
		asset += "是否购车:" + doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblHaveCar").text(); 
		
		String minIn = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblMinBidAmount").text();
		int minInValue = Utils.parseMoney(minIn);
		
		int breakTimes = 0;
		Elements tds = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_dlCreditRepayment").first().child(0).child(4).
		 select("tbody td");
		if(!tds.isEmpty()){
			breakTimes = Integer.valueOf(tds.get(5).text());
		}
		
		String breakMoney = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblPubTotalOverdueAmount").text();
		int breakMoneyValue = Utils.parseMoney(breakMoney);
		
		String borrowAmount = doc.select("#ctl00_ctl00_ContentPlaceHolder1_ContentPlaceHolder1_lblPubUnRepayTotalAmount").text();
		int borrowAmountValue = Utils.parseMoney(borrowAmount);
		
		int borrowTimes = 1;//抓不到数据，默认为1
		
		String riskControl = "";
		Elements eleRiskControlTag = doc.select("#divHtml").select("a");
		if(!eleRiskControlTag.isEmpty()){
			for (Element risk : eleRiskControlTag) {
				riskControl += risk.child(0).attr("title")+" ";
			}
		}
		
		My089LoanPersonItem person = new My089LoanPersonItem(result.getUrl());
		person.setAsset(asset);
		person.setBorrowAmount(borrowAmountValue);
		person.setBorrowTimes(borrowTimes);
		person.setBreakAmount(breakMoneyValue);
		person.setBreakTimes(breakTimes);
		person.setCity(city);
		person.setDegree(degree);
		person.setIncome(income);
		person.setMarriage(marriage);
		person.setRiskControl(riskControl);
		
		item.setBorrower(person);
		item.setMinInvest(minInValue);
		
		return item;		
	}
	
	public static ArrayList<My089LoanItem> listDefaultPage(BrowserResult result){
		ArrayList<My089LoanItem> list = new ArrayList<>();
		
		Document doc = Jsoup.parse(result.getResponseString());
		
		Elements eleItems = doc.select(".biao_item");
		if(eleItems.isEmpty()){
			logger.error("not found biao_item");
			return list;
		}
		My089LoanItem item;
		for (Element biao : eleItems) {
			String classname = biao.child(1).child(0).child(1).attr("class");
			String creditType = creditTypeMap(classname);
			String url = biao.child(1).child(0).child(2).attr("href");
			url = URLResolver.resolveUrl(result.getUrl(), url);
			String title = biao.child(1).child(0).child(2).text();
			
			String amount = biao.child(2).child(0).text();
			amount = amount.replace("￥", "");
			amount = amount.replace(",", "");
			int amountValue = Float.valueOf(amount).intValue();
			String inverst = biao.child(2).child(1).child(0).text();
			int investValue = 0;
			if(inverst.indexOf("%") > 0){
				Float f = Float.valueOf(inverst.substring(0, inverst.indexOf("%"))) * 100;
				investValue = f.intValue();
			}
			
			String progress = biao.child(3).child(0).child(1).text();
			int progressValue = 0;
			if(progress.indexOf("%") > 0){
				progressValue = Integer.valueOf(progress.substring(0,progress.indexOf("%"))) * 100;
			}
			
			String month = biao.child(4).child(0).text();
			int monthValue = 0;
			if(month.indexOf("个月") > 0){
				monthValue = Integer.valueOf(month.replace("个月", ""));
			}else{
				logger.warn("found unespected period type");;
			}
			
			String payWay = biao.child(4).text();
			
			item = new My089LoanItem(url);
			item.setTitle(title);
			item.setAmount(amountValue);
			item.setCreditType(creditType);
			item.setInterest(investValue);
			item.setInterestType(Constant.INTEREST_TYPE_YEAR);
			item.setPayWay(payWay);
			item.setPeriod(monthValue);
			item.setPeriodType(Constant.PERIOD_TYPE_MONTH);
			item.setProgress(progressValue);
			if(progressValue == 100){
				item.setStatus(Constant.STATUS_CLOSE);
			}else{
				item.setStatus(Constant.STATUS_OPEN);
			}
			
			item.setType(My089LoanItem.TYPE_DEFAULT_INFO);
			
			list.add(item);
		}
		return list;
	}
	
	public static ArrayList<My089LoanItem> listSuccPage(BrowserResult result){
		ArrayList<My089LoanItem> list = new ArrayList<>();
		
		Document doc = Jsoup.parse(result.getResponseString());
		
		Elements eleItems = doc.select(".biao_item");
		if(eleItems.isEmpty()){
			logger.error("not found biao_item");
			return list;
		}
		My089LoanItem item;
		for (Element biao : eleItems) {
			String classname = biao.child(1).child(0).child(0).child(0).attr("class");
			String creditType = creditTypeMap(classname);
			String url = biao.child(1).child(0).child(1).attr("href");
			url = URLResolver.resolveUrl(result.getUrl(), url);
			String title = biao.child(1).child(0).child(1).text();
			
			String amount = biao.child(2).child(0).text();
			amount = amount.replace("￥", "");
			amount = amount.replace(",", "");
			int amountValue = Float.valueOf(amount).intValue();
			String inverst = biao.child(2).child(1).child(0).text();
			int investValue = 0;
			if(inverst.indexOf("%") > 0){
				Float f = Float.valueOf(inverst.substring(0, inverst.indexOf("%"))) * 100;
				investValue = f.intValue();
			}
			
			String progress = biao.child(3).child(0).child(1).text();
			int progressValue = 0;
			if(progress.indexOf("%") > 0){
				progressValue = Integer.valueOf(progress.substring(0,progress.indexOf("%"))) * 100;
			}
			
			String month = biao.child(4).child(0).text();
			int monthValue = 0;
			if(month.indexOf("个月") > 0){
				monthValue = Integer.valueOf(month.replace("个月", ""));
			}else{
				logger.warn("found unespected period type");;
			}
			
			String payWay = biao.child(4).text();
			
			item = new My089LoanItem(url);
			item.setTitle(title);
			item.setAmount(amountValue);
			item.setCreditType(creditType);
			item.setInterest(investValue);
			item.setInterestType(Constant.INTEREST_TYPE_YEAR);
			item.setPayWay(payWay);
			item.setPeriod(monthValue);
			item.setPeriodType(Constant.PERIOD_TYPE_MONTH);
			item.setProgress(progressValue);
			if(progressValue == 100){
				item.setStatus(Constant.STATUS_CLOSE);
			}else{
				item.setStatus(Constant.STATUS_OPEN);
			}
			
			item.setType(My089LoanItem.TYPE_DEFAULT_INFO);
			
			list.add(item);
		}
		return list;
	}
	
	private static String creditTypeMap(String classname){
		if(Utils.isNullOrEmpty(classname)){
			logger.error("classname is empty");
			return null;
		}
		classname = classname.trim();
		if("SubL1".equals(classname)){
			return "信用标";
		}else if("SubL20".equals(classname)){
			return "秒还标";
		}else if("SubL90".equals(classname)){
			return "净值标";
		}else if("SubL10".equals(classname)){
			return "担保标";
		}else if("SubL30".equals(classname)){
			return "重组标";
		}else if("SubL60".equals(classname)){
			return "推荐标";
		}else if("SubL50".equals(classname)){
			return "快借标";
		}else if("SubL110".equals(classname)){
			return "资产贷";
		}else if("SubL40".equals(classname)){
			return "阳光贷";
		}else if("SubL80".equals(classname)){
			return "成长贷";
		}
		logger.info("found unespected credit type");
		return classname;
	}
	
	
}
