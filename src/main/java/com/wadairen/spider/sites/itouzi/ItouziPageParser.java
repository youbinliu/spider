package com.wadairen.spider.sites.itouzi;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;
import com.wadairen.spider.core.browser.BrowserResult;
import com.wadairen.spider.core.url.URLResolver;
import com.wadairen.spider.sites.Constant;
import com.wadairen.spider.sites.item.ExtraImage;

public class ItouziPageParser {

	private final static Logger logger = Logger.getLogger(ItouziPageParser.class);
	
	public static ItouziLoanItem infopage(ItouziLoanItem item,BrowserResult result){
		Document doc = Jsoup.parse(result.getResponseString());
		
		item.setTitle(doc.select("#mingcheng").text());
		item.setInterestType(1);
		
		Element progressSection = null;
		try {
			progressSection = doc.select(".project-process").get(0).child(0);
		} catch (Exception e) {
			logger.warn("not find product info:"+result.getUrl());
			return null;
		}
		
		//投资
		String amount = progressSection.child(0).child(1).text().trim();
		amount = amount.substring(0,amount.indexOf("万"));
		item.setAmount(Integer.valueOf(amount)*10000);
		//进度
		String progress = progressSection.child(1).child(1).text().trim();
		progress = progress.substring(0,progress.indexOf("%"));
		int p = Float.valueOf(progress).intValue();
		item.setProgress(p*100);
		//status
		if(p == 10000){
			item.setStatus(Constant.STATUS_CLOSE);
		}else{
			item.setStatus(Constant.STATUS_OPEN);
		}
		//利率
		Element interestSection = doc.select(".key-table-wrap").get(0);
		
		String interest = interestSection.select(".finance-rate").text();
		interest = interest.substring(0,interest.indexOf("%"));
		Float i = Float.valueOf(interest)*100;
		item.setInterest(i.intValue());
		
		//返款方式
		String payway = interestSection.select(".return-type").text();
		item.setPayWay(payway);
		//最低投资额
		Element minInterestSection = doc.select(".invest-act").first();
		String minInterest = minInterestSection.child(1).select("em").get(0).text();
		minInterest = minInterest.substring(0,minInterest.indexOf("万"));
		int min = Integer.valueOf(minInterest)*10000;
		item.setMinInvest(min);
		
		Element tableSection = doc.select(".layout-table").select("table").select("tbody").first();
		String intro = tableSection.child(0).child(0).select(".apostrophe-con").text();
		item.setIntro(intro.trim());
		
		Element riskSection = doc.select(".remind-list").first();
		int period = Integer.valueOf(riskSection.select("#shouyi10").select("em").get(1).text());
		//周期
		item.setPeriodType(4);
		item.setPeriod(period);
		
				
		ItouziLoanCompanyItem company = new ItouziLoanCompanyItem(result.getUrl());
		company.setLoanIntro(intro);
		
		String  companyName = doc.select(".company-info").select("tbody").first().child(1).child(1).child(0).text();
		company.setName(companyName);
		
		String purpose = tableSection.child(0).child(1).select(".full-content").
				first().child(0).child(0).child(1).text();
		company.setPurpose(purpose);
		
		Element riskinfo = tableSection.child(1).child(0).select(".summary-content").first().child(0);
		String riskControlName = riskinfo.child(0).child(0).child(1).text();
		company.setRiskControlCompany(riskControlName);
		String riskProduct = riskinfo.child(1).child(0).child(1).text();
		company.setRiskControlProduct(riskProduct);
		String riskControl = riskinfo.child(2).child(0).child(1).text();
		company.setRiskControl(riskControl);
		
		String productIntro = tableSection.child(3).child(0).select(".full-content").text();
		productIntro += tableSection.child(3).child(1).select(".full-content").text();
		company.setComIntro(productIntro);
		
		Elements imglist = doc.select(".img-list");
		if(!imglist.isEmpty()){
			Elements images = imglist.select("a");
			if(!images.isEmpty()){
				List<ExtraImage> extraImages = new ArrayList<>();
				for (Element image : images) {
					String imgName = image.attr("title");
					String imgUrl = image.attr("href");
					imgUrl = URLResolver.resolveUrl(result.getUrl(), imgUrl);
					
					ExtraImage img = new ExtraImage(imgName, imgUrl);
					extraImages.add(img);
				}
				
				company.setExtraImg(new Gson().toJson(extraImages));
			}
		}else{
			//System.out.println(result.getResponseString());
		}
		
		item.setBorrower(company);
		
		return item;		
	}
}
