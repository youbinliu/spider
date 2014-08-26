package com.wadairen.spider.core;

public class Config {
	
	public String getUserAgentString() {
		return userAgentString;
	}

	public void setUserAgentString(String userAgentString) {
		this.userAgentString = userAgentString;
	}

	public int getPolitenessDelay() {
		return politenessDelay;
	}

	public void setPolitenessDelay(int politenessDelay) {
		this.politenessDelay = politenessDelay;
	}

	public boolean isIncludeHttpsPages() {
		return includeHttpsPages;
	}

	public void setIncludeHttpsPages(boolean includeHttpsPages) {
		this.includeHttpsPages = includeHttpsPages;
	}

	public boolean isIncludeBinaryContentInCrawling() {
		return includeBinaryContentInCrawling;
	}

	public void setIncludeBinaryContentInCrawling(
			boolean includeBinaryContentInCrawling) {
		this.includeBinaryContentInCrawling = includeBinaryContentInCrawling;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}

	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	
	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public int getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(int proxyPort) {
		this.proxyPort = proxyPort;
	}

	public String getProxyUsername() {
		return proxyUsername;
	}

	public void setProxyUsername(String proxyUsername) {
		this.proxyUsername = proxyUsername;
	}

	public String getProxyPassword() {
		return proxyPassword;
	}

	public void setProxyPassword(String proxyPassword) {
		this.proxyPassword = proxyPassword;
	}
	
	public int getMaxDownloadSize() {
		return maxDownloadSize;
	}

	public void setMaxDownloadSize(int maxDownloadSize) {
		this.maxDownloadSize = maxDownloadSize;
	}
	
	public int getThreadCorePoolSize() {
		return threadCorePoolSize;
	}

	public void setThreadCorePoolSize(int threadCorePoolSize) {
		this.threadCorePoolSize = threadCorePoolSize;
	}

	public int getThreadMaxPoolSize() {
		return threadMaxPoolSize;
	}

	public void setThreadMaxPoolSize(int threadMaxPoolSize) {
		this.threadMaxPoolSize = threadMaxPoolSize;
	}

	public int getThreadKeepAliveSeconds() {
		return threadKeepAliveSeconds;
	}

	public void setThreadKeepAliveSeconds(int threadKeepAliveSeconds) {
		this.threadKeepAliveSeconds = threadKeepAliveSeconds;
	}
	
	public int getThreadDelayInMilliseconds() {
		return threadDelayInMilliseconds;
	}

	public void setThreadDelayInMilliseconds(int threadDelayInMilliseconds) {
		this.threadDelayInMilliseconds = threadDelayInMilliseconds;
	}
	/**
	 */
	private String userAgentString = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.57 Safari/537.36";

	/**
	 * Politeness delay in milliseconds (delay between sending two requests to
	 * the same host).
	 */
	private int politenessDelay = 200;

	/**
	 * Should we also crawl https pages?
	 */
	private boolean includeHttpsPages = true;

	/**
	 * Should we fetch binary content such as images, audio, ...?
	 */
	private boolean includeBinaryContentInCrawling = false;

	/**
	 * Maximum Connections per host
	 */
	private int maxConnectionsPerHost = 1000;

	/**
	 * Maximum total connections
	 */
	private int maxTotalConnections = 1000;

	/**
	 * Socket timeout in milliseconds
	 */
	private int socketTimeout = 60000;

	/**
	 * Connection timeout in milliseconds
	 */
	private int connectionTimeout = 60000;
	
	/**
	 * If crawler should run behind a proxy, this parameter can be used for
	 * specifying the proxy host.
	 */
	private String proxyHost = null;

	

	/**
	 * If crawler should run behind a proxy, this parameter can be used for
	 * specifying the proxy port.
	 */
	private int proxyPort = 8888;

	/**
	 * If crawler should run behind a proxy and user/pass is needed for
	 * authentication in proxy, this parameter can be used for specifying the
	 * username.
	 */
	private String proxyUsername = null;

	/**
	 * If crawler should run behind a proxy and user/pass is needed for
	 * authentication in proxy, this parameter can be used for specifying the
	 * password.
	 */
	private String proxyPassword = null;
	
	/**
	 * Max allowed size of a page. Pages larger than this size will not be
	 * fetched.
	 */
	private int maxDownloadSize = 1048576;

	/**
	 * avage threads 
	 */
	private int threadCorePoolSize = 20;
	/**
	 * max number of threads
	 */
	private int threadMaxPoolSize = 50;
	/**
	 * thread's max alive time when have not task
	 */
	private int threadKeepAliveSeconds = 60;//1min
	
	private int threadDelayInMilliseconds = 100;

	
}
