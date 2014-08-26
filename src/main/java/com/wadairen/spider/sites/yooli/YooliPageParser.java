package com.wadairen.spider.sites.yooli;

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

public class YooliPageParser {

	private final static Logger logger = Logger.getLogger(YooliPageParser.class);
	
	
	
	public static YooliLoanItem infopage(YooliLoanItem item,BrowserResult result){
		
		Document doc = Jsoup.parse(result.getResponseString());
		
		String intro = doc.select(".explain").text();
		
		Element eleRiskControl = doc.select(".agency-logo").first();
		String riskControlCompany = eleRiskControl.child(0).attr("alt");
		String riskControl = eleRiskControl.child(1).text();
		
		Element eleBorrowerInfo = doc.select("#borrowerDetails").first();
		
		Element eleBorrower = eleBorrowerInfo.child(0).child(0).select("ul").first();
		String age = eleBorrower.child(2).child(1).text();
		String degree = eleBorrower.child(3).child(1).text();
		String marriage = eleBorrower.child(4).child(1).text();
		//String city = eleBorrower.child(6).child(1).text();
		
		Element eleBorrowerAsset = eleBorrowerInfo.child(0).child(1).select("ul").first();
		String income = eleBorrowerAsset.child(0).child(1).text();
		
		String asset = "";
		String assetHorse = eleBorrowerAsset.child(1).child(1).text();
		if(!Utils.isNullOrEmpty(assetHorse) && "有".equals(assetHorse.trim())){
			asset = "有房产 ";
		}
		String assetCar = eleBorrowerAsset.child(2).child(1).text();
		if(!Utils.isNullOrEmpty(assetCar) && "有".equals(assetCar.trim())){
			asset += "有车产";
		}
		
		String job = "";
		Elements eleBorrowCompanyInfos = eleBorrowerInfo.child(0).child(2).select("ul").first().children();
		for(int i = 1; i < eleBorrowCompanyInfos.size(); i++){//跳过公司所在地
			job += eleBorrowCompanyInfos.get(i).text();
		}
				
		Element eleBorrowHistory = eleBorrowerInfo.child(1).child(2);
		Element eleInfo1 = eleBorrowHistory.child(1).select("tr").get(1);
		
		String borrowAmount = eleInfo1.child(0).text();
		int borrowAmountValue = 0;
		if(!Utils.isNullOrEmpty(borrowAmount)){
			borrowAmount = borrowAmount.replace("￥", "");
			borrowAmountValue = Float.valueOf(borrowAmount).intValue();
		}
		
		String breakAmount = eleInfo1.child(2).text();
		int breakAmountValue = 0;
		if(!Utils.isNullOrEmpty(breakAmount)){
			breakAmount = breakAmount.replace("￥", "");
			breakAmountValue = Float.valueOf(breakAmount).intValue();
		}
		
		Element eleInfo2 = eleBorrowHistory.child(2).select("tr").get(1);
		
		String borrowTimes = eleInfo2.child(0).text();
		int borrowTimesValue = 0;
		
		if(!Utils.isNullOrEmpty(borrowTimes)){
			borrowTimes = borrowTimes.replace("笔", "");
			borrowTimesValue = Float.valueOf(borrowTimes).intValue();
		}
		
		String breakTimes = eleInfo2.child(3).text();
		int breakTimesValue = 0;
		if(!Utils.isNullOrEmpty(breakTimes)){
			breakTimes = breakTimes.replace("次", "");
			breakTimesValue = Float.valueOf(breakTimes).intValue();
		}
		
		YooliLoanPersonItem person = new YooliLoanPersonItem(result.getUrl());
		person.setAge(Integer.valueOf(age));
		person.setAsset(asset);
		person.setBorrowAmount(borrowAmountValue);
		person.setBorrowTimes(borrowTimesValue);
		person.setBreakAmount(breakAmountValue);
		person.setBreakTimes(breakTimesValue);
		person.setDegree(degree);
		person.setIncome(income);
		person.setIntro(intro);
		person.setJob(job);
		person.setMarriage(marriage);
		person.setRiskControl(riskControl);
		person.setRiskControlCompany(riskControlCompany);
		
		item.setBorrower(person);
		item.setIntro(intro);
		
		return item;		
	}
	
	public static ArrayList<YooliLoanItem> listpage(BrowserResult result){
		ArrayList<YooliLoanItem> list = new ArrayList<>();
		
		Document doc = Jsoup.parse(result.getResponseString());
		Elements eleInvests = doc.select(".invest-loan");
		
		if(eleInvests.isEmpty()){
			logger.error("not found .invest-loan "+ result.getUrl());
			return null;
		}
		
		YooliLoanItem item;
		for (int i = 1; i < eleInvests.size(); i++) {//i=1 跳过表头
			try{
				Element eleInvest = eleInvests.get(i);
				Element eleUl = eleInvest.child(0);
				Element tagtitle = eleUl.child(0).child(0);//a 标签
				String url = tagtitle.attr("href");
				url = URLResolver.resolveUrl(result.getUrl(), url);
				String title = tagtitle.text();
				
				String creditType = "";
				if(eleUl.child(0).children().size() > 1){
					creditType = eleUl.child(0).child(1).attr("data-text");
				}
				String intvest = eleUl.child(1).text();
				intvest = intvest.replace("%", "");
				Float f = Float.valueOf(intvest)*100;
				int intvestValue = f.intValue();
				
				String amount = eleUl.child(2).text();
				amount = amount.replace(" ", "");
				amount = amount.replace("￥", "");
				int amountValue = Float.valueOf(amount).intValue();
				
				String month = eleUl.child(3).text();
				month = month.replace("个月", "");
				String progress = eleUl.child(5).child(0).attr("data-rel");
				Float p = Float.valueOf(progress)*100;
				int progressValue = p.intValue();
				
				item = new YooliLoanItem(url);
				item.setAmount(amountValue);
				item.setCreditType(creditType);
				item.setInterest(intvestValue);
				item.setInterestType(Constant.INTEREST_TYPE_YEAR);
				item.setPeriod(Integer.valueOf(month));
				item.setPeriodType(Constant.PERIOD_TYPE_MONTH);
				item.setProgress(progressValue);
				item.setPayWay(creditType);
				if(progressValue == 10000){
					item.setStatus(Constant.STATUS_CLOSE);
				}else{
					item.setStatus(Constant.STATUS_OPEN);
				}
				item.setTitle(title);
				item.setType(Constant.TYPE_INFO);
			}catch(Exception e){
				logger.error("page:"+result.getUrl()+" "+e.getMessage());
				continue;
			}
			list.add(item);
			
		}		
		return list;
	}
	
	
}
