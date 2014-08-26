package com.wadairen.spider.sites.yirendai;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.url.URLResolver;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.utils.Utils;

public class YrdPageParser {

	private final static Logger logger = Logger.getLogger(YrdPageParser.class);
	
	public static List<YrdLoanItem> listpage(YrdLoanItem item,BrowserResult result){
		if(item.getType() == YrdLoanItem.TYPE_JING_LIST){
			return listPage(item, result);
		}else if(item.getType() == YrdLoanItem.TYPE_SUCC_LIST){
			return listPage(item, result);
		}else if(item.getType() == YrdLoanItem.TYPE_ZHAI_LIST){
			
		}
		logger.error("unespected type");
		return new ArrayList<>();
	}
	
		
	private static List<YrdLoanItem> listPage(YrdLoanItem item,BrowserResult result){
		List<YrdLoanItem> items = new ArrayList<>();
		Document doc = Jsoup.parse(result.getResponseString());
		Elements eleList = doc.select(".tab_box");
		
		if(eleList.isEmpty()){
			logger.error("not found tab_box:"+result.getUrl());
			return null;
		}
		YrdLoanItem one;
		for (Element tab : eleList) {
			
			Elements eleLoanInfos = tab.child(0).select("tr");
			Element eleTaga = eleLoanInfos.get(0).child(1).child(0);
			String url = eleTaga.attr("href");
			url = URLResolver.resolveUrl(result.getUrl(), url);
			
			one = new YrdLoanItem(url);
			
			String classname = eleTaga.attr("class");
			String creditType = classname;
			if(!Utils.isNullOrEmpty(classname) && classname.contains("iconJyb")){
				creditType = YrdLoanItem.CREDIT_TYPE_JING;
			}
			
			String title = eleTaga.child(0).attr("title");
			
			String amount = eleLoanInfos.get(1).child(0).child(0).text();
			
			String months = "";
			if(item.getType() == YrdLoanItem.TYPE_JING_LIST){
				months = eleLoanInfos.get(3).child(0).child(0).text();
			}else if(item.getType() == YrdLoanItem.TYPE_SUCC_LIST){
				months = eleLoanInfos.get(2).child(0).child(0).text();
			}
			
			Element eleLoanInfo2 = tab.child(1).child(0);
			
			String progress = eleLoanInfo2.child(0).child(0).text();
			progress = progress.replace("投标完成:", "");
			progress = progress.replace("%", "");
			int progressValue = Float.valueOf(progress).intValue()*100;
			String invest = eleLoanInfo2.child(1).child(1).text();
			Float f = (Float.valueOf(invest)*100);
			int investValue = f.intValue();
			
			one.setAmount(Integer.valueOf(amount));
			one.setCreditType(creditType);
			one.setInterest(investValue);
			one.setInterestType(Constant.INTEREST_TYPE_YEAR);
			one.setPeriod(Integer.valueOf(months));
			one.setPeriodType(Constant.PERIOD_TYPE_MONTH);
			one.setProgress(progressValue);
			if(progressValue == 10000){
				one.setStatus(Constant.STATUS_CLOSE);
			}else{
				one.setStatus(Constant.STATUS_OPEN);
			}
			one.setTitle(title);
			if(item.getType() == YrdLoanItem.TYPE_JING_LIST){
				one.setType(YrdLoanItem.TYPE_JING_INFO);
			}else if(item.getType() == YrdLoanItem.TYPE_SUCC_LIST){
				one.setType(YrdLoanItem.TYPE_SUCC_INFO);
			}
			
			
			items.add(one);
		}
		
		return items;
	}
	
	public static YrdLoanItem infopage(YrdLoanItem item,BrowserResult result){
		if(item.getType() == YrdLoanItem.TYPE_JING_INFO){
			return infoPage(item, result);
		}else if(item.getType() == YrdLoanItem.TYPE_SUCC_INFO){
			return infoPage(item, result);
		}else if(item.getType() == YrdLoanItem.TYPE_ZHAI_INFO){
			
		}
		
		logger.error("unespected type");
		return null;
		
	}
	
	private static YrdLoanItem infoPage(YrdLoanItem item,BrowserResult result){
		Document doc = Jsoup.parse(result.getResponseString());
		Element eleTable = doc.select(".borrowApplyDetail").first();
		Elements eleTrs = eleTable.select("tr");
		
		String payWay = eleTrs.get(2).text();
		//这里也可以分析一些具体信息，listpage已经分析过了，此处不用重复分析
		
		String intro = doc.select(".borrowDetail_c").first().child(1).text();
		
		Elements eleBorrowerInfos = doc.select(".borrowerInformation_l").select("tr");
		
		Element eleDegree = eleBorrowerInfos.get(0).child(1);
		eleDegree.child(0).remove();
		String degree = eleDegree.text();
		
		Element eleCity = eleBorrowerInfos.get(1).child(0);
		eleCity.child(0).remove();
		String city = eleCity.text();
		
		Element eleMarriage = eleBorrowerInfos.get(1).child(1);
		eleMarriage.child(0).remove();
		String marriage = eleMarriage.text();
		
		Element eleIncome = eleBorrowerInfos.get(2);
		String income = eleIncome.text();
		
		Element eleAsset = eleBorrowerInfos.get(3).child(0);
		eleAsset.child(0).remove();
		String asset = eleAsset.text();
		
		String riskControl = doc.select(".borrowerInformation").first().child(0).text();
		
		YrdLoanPersonItem person = new YrdLoanPersonItem(result.getUrl());
		person.setAsset(asset);
		person.setCity(city);
		person.setDegree(degree);
		person.setIncome(income);
		person.setIntro(intro);
		person.setMarriage(marriage);
		person.setRiskControl(riskControl);
		
		item.setBorrower(person);
		item.setBorrowerType(Constant.BORROWER_TYPE_PERSON);
		item.setPayWay(payWay);
		item.setIntro(intro);
		return item;
	}
}
