package com.wadairen.spider.sites.jimubox;

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
import com.wadairen.spider.utils.Utils;

public class JimuPageParser {

	private final static Logger logger = Logger.getLogger(JimuPageParser.class);
	
	
	public static List<JimuLoanItem> listpage(JimuLoanItem item,BrowserResult result){
		List<JimuLoanItem> items = new ArrayList<>();
		Document doc = Jsoup.parse(result.getResponseString());
		Elements eleSumaryInfos = doc.select(".summary-info");
		
		if(eleSumaryInfos.isEmpty()){
			logger.error("not found summary info");
			return items;
		}
		
		for (Element eleSumaryInfo : eleSumaryInfos) {
			Element eleTaga = eleSumaryInfo.child(0).child(0).select("h4").first().child(2);
			String title = eleTaga.text();
			String url = eleTaga.attr("href");
			url = URLResolver.resolveUrl(result.getUrl(), url);
			
			Element eleRowFluid =  eleSumaryInfo.child(0).child(1);
			String amount = eleRowFluid.child(0).child(1).text();
			if(amount.contains("万")){
				amount = amount.replace("万", "").trim();
			}
			
			int amountValue = Integer.valueOf(amount)*10000;
			int interestValue = 0;
			String interest = eleRowFluid.child(1).child(1).text();
			if(interest.indexOf("+") > 0){
				String[] interests = interest.split("+");
				for (String inter : interests) {
					interestValue += Utils.parsePercent(inter);
				}
			}
			
			String progress = eleSumaryInfo.child(3).child(0).select("bar").first().attr("style");
			progress = progress.replace("width: ", "");
			progress = progress.replace(";", "");
			int progressValue = Utils.parsePercent(progress);
			
			Element eleThridPart = eleSumaryInfo.child(0).child(2);
			String creditType = eleThridPart.select(".projectType").first().select("img").first().attr("alt");
			
			item.setAmount(amountValue);
			item.setCreditType(creditType);
			item.setInterest(interestValue);
			item.setInterestType(Constant.INTEREST_TYPE_YEAR);
			item.setProgress(progressValue);
			
			if(progressValue == 10000){
				item.setStatus(Constant.STATUS_CLOSE);
			}else{
				item.setStatus(Constant.STATUS_OPEN);
			}
			
			item.setTitle(title);
			item.setType(Constant.TYPE_INFO);
			
			items.add(item);
		}
		
		return items;
	}
		
	public static JimuLoanItem infopage(JimuLoanItem item,BrowserResult result){
		
		if(JimuLoanItem.CREDIT_TYPE_QI.equals(item.getCreditType())){
			return qiInfoPage(item, result);
		}else if(JimuLoanItem.CREDIT_TYPE_FANG.equals(item.getCreditType())){
			return fangInfoPage(item, result);
		}
		
		logger.error("found unespected credit type");
		return null;
	}
	
	public static JimuLoanItem qiInfoPage(JimuLoanItem item,BrowserResult result){
		Document doc = Jsoup.parse(result.getResponseString());
		
		Element eleLoanInfo = doc.select("#loanInfo").first();
		String label = eleLoanInfo.child(2).child(0).child(0).child(8).text();
		
		String loanIntro = "";
		if(label.indexOf("项目情况") > 0){
			loanIntro = eleLoanInfo.child(2).child(0).child(0).child(9).text();
		}
		
		String purpose = "";
		label = eleLoanInfo.child(2).child(0).child(0).child(10).text();
		if(label.indexOf("资金用途") > 0){
			purpose = eleLoanInfo.child(2).child(0).child(0).child(11).text();
		}
		
		Element eleRiskControl = eleLoanInfo.child(2).child(1).child(0);
		
		label = eleRiskControl.child(0).text();
		String riskControl = "";
		if(label.indexOf("保证情况") > 0){
			riskControl = eleRiskControl.child(1).text();
		}
		
		String riskControlCompany = "";
		label = eleRiskControl.child(2).text();
		if(label.indexOf("担保公司") > 0){
			riskControlCompany = eleRiskControl.child(3).text();
		}
		
		label = eleRiskControl.child(4).text();
		if(label.indexOf("担保情况") > 0){
			riskControl += eleRiskControl.child(5).text();
		}
		
		String comIntro = doc.select("#companyInfo").select(".dl-horizontal").text();
		
		String extraImg = "";
		Elements elePicTagas = doc.select("#reference").select("a");
		if(!elePicTagas.isEmpty()){
			List<ExtraImage> images = new ArrayList<>();
			for (Element elePictaga : elePicTagas) {
				String ti = elePictaga.attr("title");
				String downlink = elePictaga.attr("href");
				if(!Utils.isNullOrEmpty(ti) || downlink.indexOf("UploadHandler") > 0){
					ExtraImage img = new ExtraImage(ti, URLResolver.resolveUrl(result.getUrl(), downlink));
					images.add(img);
				}
			}
			Gson gson = new Gson();
			extraImg = gson.toJson(images);
		}
		
		JimuLoanCompanyItem company = new JimuLoanCompanyItem(result.getUrl());
		company.setComIntro(comIntro);
		company.setExtraImg(extraImg);
		company.setLoanIntro(loanIntro);
		company.setName("企业经营贷");//没有找到企业名字
		company.setPurpose(purpose);
		company.setRiskControl(riskControl);
		company.setRiskControlCompany(riskControlCompany);
		
		item.setIntro(loanIntro);
		item.setBorrower(company);
		return item;
	}
	
	public static JimuLoanItem fangInfoPage(JimuLoanItem item,BrowserResult result){
		Document doc = Jsoup.parse(result.getResponseString());
		
		Element eleLoanInfo = doc.select("#loanInfo").first();
		String label = eleLoanInfo.child(2).child(0).child(0).child(8).text();
		
		String loanIntro = "";
		if(label.indexOf("项目情况") > 0){
			loanIntro = eleLoanInfo.child(2).child(0).child(0).child(9).text();
		}
		
		String purpose = "";
		label = eleLoanInfo.child(2).child(0).child(0).child(10).text();
		if(label.indexOf("资金用途") > 0){
			purpose = eleLoanInfo.child(2).child(0).child(0).child(11).text();
		}
		
		Element eleRiskControl = eleLoanInfo.child(2).child(1).child(0);
		
		label = eleRiskControl.child(0).text();
		String riskControl = "";
		if(label.indexOf("保证情况") > 0){
			riskControl = eleRiskControl.child(1).text();
		}
		
		String riskControlCompany = "";
		label = eleRiskControl.child(2).text();
		if(label.indexOf("担保公司") > 0){
			riskControlCompany = eleRiskControl.child(3).text();
		}
		
		label = eleRiskControl.child(4).text();
		if(label.indexOf("担保情况") > 0){
			riskControl += eleRiskControl.child(5).text();
		}
		
		String comIntro = doc.select("#houseInfo").select(".dl-horizontal").text();
		
		String extraImg = "";
		Elements elePicTagas = doc.select("#reference").select("a");
		if(!elePicTagas.isEmpty()){
			List<ExtraImage> images = new ArrayList<>();
			for (Element elePictaga : elePicTagas) {
				String ti = elePictaga.attr("title");
				String downlink = elePictaga.attr("href");
				if(!Utils.isNullOrEmpty(ti) || downlink.indexOf("UploadHandler") > 0){
					ExtraImage img = new ExtraImage(ti, URLResolver.resolveUrl(result.getUrl(), downlink));
					images.add(img);
				}
			}
			Gson gson = new Gson();
			extraImg = gson.toJson(images);
		}
		
		JimuLoanCompanyItem company = new JimuLoanCompanyItem(result.getUrl());
		company.setComIntro(comIntro);
		company.setExtraImg(extraImg);
		company.setLoanIntro(loanIntro);
		company.setName("购房周转贷");//没有找到企业名字
		company.setPurpose(purpose);
		company.setRiskControl(riskControl);
		company.setRiskControlCompany(riskControlCompany);
		
		item.setIntro(loanIntro);
		item.setBorrower(company);
		return item;
	}
}
