package com.wadairen.spider.sites.item;

import org.joda.time.DateTime;

import com.wadairen.spider.core.item.BaseItem;
import com.wadairen.spider.utils.Utils;

public class BaseLoanItem extends BaseItem {

	public BaseLoanItem(String url) {
		super(url);
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getSourceUrl() {
		return this.url;
	}

	public String getSourceUrlHash() {
		return Utils.urlHash(url);
	}

	public String getCreateAt() {
		return DateTime.now().toString();
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}
	
	protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 站点ID
	 */
	protected int siteId;
	/**
	 * 站点名
	 */
	protected String site;
	/**
	 * 源站点url
	 */
	protected String sourceUrl;
	/**
	 * 源站点url hash
	 */
	protected String sourceUrlHash;
	
	protected String createAt;
	
	protected String updateAt;
}
