package com.wadairen.spider.core;


public abstract class Configurable {
	protected Config config;
	
	protected Configurable(Config config) {
		this.config = config;
	}
	
	public Config getConfig() {
		return config;
	}
}
