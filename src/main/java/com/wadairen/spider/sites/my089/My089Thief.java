package com.wadairen.spider.sites.my089;

import com.wadairen.spider.core.browser.Browser;
import com.wadairen.spider.sites.AutoLogin;

public class My089Thief extends AutoLogin{
	
	private final static String POST_URL = "https://www.my089.com/Login.aspx";
	
	public My089Thief(Browser browser) {
		super(POST_URL, browser);
		this.fillform();
	}
	
	private void fillform(){
		
		this.addParam("__EVENTTARGET", "");
		this.addParam("__EVENTARGUMENT", "");
		this.addParam("__VIEWSTATE", "/wEPDwUKMTc3Mzg3OTU5Mw9kFgJmD2QWAmYPZBYCAgQPZBYCAgEPZBYCAgEPZBYEAgUPFgIeB1Zpc2libGVoFgICAQ8PFggeBElzSFpoHgNTSUQFJFNJRF8wYzNiMmU0NzllY2Y0Y2NlOWJhMzRiOWI3YzY4MTRlMx4HVmVyc2lvbgUBTx8AaGQWAgICDxYEHgdvbmNsaWNrBXNXZWJGb3JtX0RvQ2FsbGJhY2soJ2N0bDAwJGN0bDAwJENvbnRlbnRQbGFjZUhvbGRlcjEkQ29udGVudFBsYWNlSG9sZGVyMSRSYW5kb21Db2RlMScsIiIsR2V0Q2hlY2tDb2RlLCIiLG51bGwsZmFsc2UpHgNzcmMFfy4uL2NvbW1vblBhZ2UvcnZjLmFzcHg/c2lkPUQ3RTU4QUNEN0VDOUE2MzE2QkE5MDA1RjA2MzVFODhCRDM2MjBGOUE4MzNCMjVGMDMwRThCRjM3QTk5NDYyMURFODkyQjkwMTA1ODdBNTk1JnI9MDExODE3ODk1JnY9TyZpPUZkAgcPDxYCHg1PbkNsaWVudENsaWNrBb8DaWYoIVZhbGlkYXRlTG9naW4oJ2N0bDAwX2N0bDAwX0NvbnRlbnRQbGFjZUhvbGRlcjFfQ29udGVudFBsYWNlSG9sZGVyMV90eHRVc2VyTmFtZScsJ2N0bDAwX2N0bDAwX0NvbnRlbnRQbGFjZUhvbGRlcjFfQ29udGVudFBsYWNlSG9sZGVyMV90eHRQd2QnKSkgcmV0dXJuIGZhbHNlO0VuY3J5cHRUZXh0KCdjdGwwMF9jdGwwMF9Db250ZW50UGxhY2VIb2xkZXIxX0NvbnRlbnRQbGFjZUhvbGRlcjFfdHh0UHdkJyk7IHZhciBoPWRvY3VtZW50LmdldEVsZW1lbnRCeUlkKCdjdGwwMF9jdGwwMF9Db250ZW50UGxhY2VIb2xkZXIxX0NvbnRlbnRQbGFjZUhvbGRlcjFfaGZwd2QnKTsgdmFyIHA9ZG9jdW1lbnQuZ2V0RWxlbWVudEJ5SWQoJ2N0bDAwX2N0bDAwX0NvbnRlbnRQbGFjZUhvbGRlcjFfQ29udGVudFBsYWNlSG9sZGVyMV90eHRQd2QnKTsgaC52YWx1ZT1wLnZhbHVlO3AudmFsdWU9Jyc7ZGRklSyFjkda94BWJThEzoE6dakS5xs=");
		this.addParam("__EVENTVALIDATION", "/wEWEAKOgbe5CALVz5eVAwLFqsi1BQKC+8rVCAKJ9JOUBwKF9KOUBwKH9J+UBwKA9J+UBwKjtdhDAq21gEACqbXwQwKfyMGlAwLWm4jIDwKptbD+CwKFj72BCwK/w5iEDBNbB1YrwZW6ZUqCeVrCcguLIOfx");
		
		this.addParam("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$txtUserName", "oreoliu");
		this.addParam("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$txtPwd", "");
		this.addParam("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$hfpwd", "0b4e7a0e5fe84ad35fb5f95b9ceeac79");
		this.addParam("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$ddlKeepTime", "10080");
		
		this.addParam("ctl00$ctl00$ContentPlaceHolder1$ContentPlaceHolder1$btnLogin", "登　录");
	}
}
