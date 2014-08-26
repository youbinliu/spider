package com.wadairen.spider.core.browser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;

import com.wadairen.spider.core.Config;
import com.wadairen.spider.core.Configurable;
import com.wadairen.spider.core.url.URLCanonicalizer;

public class Browser extends Configurable{
	
	protected static final Logger logger = Logger.getLogger(Browser.class);

	protected PoolingClientConnectionManager connectionManager;
	
	protected DefaultHttpClient httpClient;
	
	protected List<Header> httpHeader;
	
	protected SSLSocketFactory factory = SSLSocketFactory.getSocketFactory();

	public final static String HTTP_METHOD_GET = "GET";
	public final static String HTTP_METHOD_POST = "POST";
	
	protected BrowserMonitor browserMonitor;
	
	protected final Object mutex = new Object();

	protected long lastFetchTime = 0;
	
	public Browser(Config config,SSLSocketFactory factory){
		super(config);
		this.factory = factory;
		this.initHttpClient();
	}
	
	public Browser(Config config){
		super(config);
		this.initHttpClient();
	}
	
	private void initHttpClient(){
		
		HttpParams params = new BasicHttpParams();
		HttpProtocolParamBean paramsBean = new HttpProtocolParamBean(params);
		paramsBean.setVersion(HttpVersion.HTTP_1_1);
		paramsBean.setContentCharset("UTF-8");
		paramsBean.setUseExpectContinue(false);

		params.setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
		params.setParameter(CoreProtocolPNames.USER_AGENT, config.getUserAgentString());
		params.setIntParameter(CoreConnectionPNames.SO_TIMEOUT, config.getSocketTimeout());
		params.setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, config.getConnectionTimeout());

		params.setBooleanParameter("http.protocol.handle-redirects", false);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));

		if (config.isIncludeHttpsPages()) {
			schemeRegistry.register(new Scheme("https", 443, this.factory));
		}
		
		connectionManager = new PoolingClientConnectionManager(schemeRegistry);
		connectionManager.setMaxTotal(config.getMaxTotalConnections());
		connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());
		httpClient = new DefaultHttpClient(connectionManager, params);
		//加入代理设置
		if (config.getProxyHost() != null) {

			if (config.getProxyUsername() != null) {
				httpClient.getCredentialsProvider().setCredentials(
						new AuthScope(config.getProxyHost(), config.getProxyPort()),
						new UsernamePasswordCredentials(config.getProxyUsername(), config.getProxyPassword()));
			}

			HttpHost proxy = new HttpHost(config.getProxyHost(), config.getProxyPort());
			httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
        }
		//处理返回，将gzip解压
        httpClient.addResponseInterceptor(new HttpResponseInterceptor() {

            @Override
            public void process(final HttpResponse response, final HttpContext context) throws HttpException,
                    IOException {
                HttpEntity entity = response.getEntity();
                Header contentEncoding = entity.getContentEncoding();
                if (contentEncoding != null) {
                    HeaderElement[] codecs = contentEncoding.getElements();
                    for (HeaderElement codec : codecs) {
                        if (codec.getName().equalsIgnoreCase("gzip")) {
                            response.setEntity(new GzipDecompressingEntity(response.getEntity()));
                            return;
                        }
                    }
                }
            }

        });
        //监控链接
        if(browserMonitor == null){
        	browserMonitor = new BrowserMonitor(connectionManager);
        }
        browserMonitor.start();
	}
	
	public BrowserResult get(String url){
		return excute(HTTP_METHOD_GET, url, null);
	}
	
	public BrowserResult post(String url,List<BasicNameValuePair> params){
		return excute(HTTP_METHOD_POST, url, params);
	}
	
	private BrowserResult excute(String method,String url,List<BasicNameValuePair> params){
		BrowserResult browserResult = new BrowserResult();
		HttpGet get = null;
		HttpPost post = null;
		try {
			if(HTTP_METHOD_GET.equals(method)){
				get = new HttpGet(url);
			}else{
				post = new HttpPost(url);
			}
			synchronized (mutex) {
				long now = (new Date()).getTime();
				if (now - lastFetchTime < config.getPolitenessDelay()) {
					Thread.sleep(config.getPolitenessDelay() - (now - lastFetchTime));
				}
				lastFetchTime = (new Date()).getTime();
			}
			
			if(HTTP_METHOD_GET.equals(method)){
				get.addHeader("Accept-Encoding", "gzip");
				if(httpHeader != null){
					for (int i = 0; i < httpHeader.size(); i++) {
						get.setHeader(httpHeader.get(i));
					}
				}
			}else{
				if(httpHeader != null){
					for (int i = 0; i < httpHeader.size(); i++) {
						post.setHeader(httpHeader.get(i));
					}
				}
			}
			
			HttpResponse response = null;
			if(HTTP_METHOD_GET.equals(method)){
				response = httpClient.execute(get);
			}else{
				post.setEntity(new UrlEncodedFormEntity(params,"UTF-8"));
				response = httpClient.execute(post);
			}
			
			browserResult.setEntity(response.getEntity());
			browserResult.setResponseHeaders(response.getAllHeaders());
			
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				if (statusCode != HttpStatus.SC_NOT_FOUND) {
					if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
						Header header = response.getFirstHeader("Location");
						if (header != null) {
							String movedToUrl = header.getValue();
							movedToUrl = URLCanonicalizer.getCanonicalURL(movedToUrl, url);
							browserResult.setUrl(url);
							browserResult.setMovedToUrl(movedToUrl);
						} 
						browserResult.setStatusCode(statusCode);
						return browserResult;
					}
					logger.info("Failed: " + response.getStatusLine().toString() + ", while fetching " + url);
				}
				browserResult.setStatusCode(statusCode);
				return browserResult;
			}

			browserResult.setUrl(url);
			String uri = "";
			if(HTTP_METHOD_GET.equals(method)){
				uri = get.getURI().toString();
			}else{
				uri = post.getURI().toString();
			}
			
			if (!uri.equals(url)) {
				if (!URLCanonicalizer.getCanonicalURL(uri).equals(url)) {
					browserResult.setUrl(uri);
				}
			}

			if (browserResult.getEntity() != null) {
				long size = browserResult.getEntity().getContentLength();
				if (size == -1) {
					Header length = response.getLastHeader("Content-Length");
					if (length == null) {
						length = response.getLastHeader("Content-length");
					}
					if (length != null) {
						size = Integer.parseInt(length.getValue());
					} else {
						size = -1;
					}
				}
				if (size > config.getMaxDownloadSize()) {
					browserResult.setStatusCode(BrowserStatus.PageTooBig);
					if(HTTP_METHOD_GET.equals(method)){
						get.abort();
					}else{
						post.abort();
					}
					return browserResult;
				}

				browserResult.setStatusCode(HttpStatus.SC_OK);
				return browserResult;

			}
			
			if(HTTP_METHOD_GET.equals(method)){
				get.abort();
			}else{
				post.abort();
			}
			
		} catch (IOException e) {
			logger.error("Fatal transport error: " + e.getMessage() + " while get " + url);
			browserResult.setUrl(url);
			browserResult.setStatusCode(BrowserStatus.FatalTransportError);
			return browserResult;
		} catch (IllegalStateException e) {
				logger.error(e.getMessage());
			// ignoring exceptions that occur because of not registering https
			// and other schemes
		} catch (Exception e) {
			e.printStackTrace();
			if (e.getMessage() == null) {
				logger.error("Error while get " + url);
			} else {
				logger.error(e.getMessage() + " while get " + url);
			}
		} finally {
			try {
				if (browserResult.getEntity() == null && get != null) {
					if(HTTP_METHOD_GET.equals(method)){
						get.abort();
					}else{
						post.abort();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		browserResult.setStatusCode(BrowserStatus.UnknownError);
		return browserResult;
	}
	
	
	public synchronized void shutDown() {
		if (browserMonitor != null) {
			connectionManager.shutdown();
			browserMonitor.shutdown();
		}
	}
	
	public HttpClient getHttpClient() {
		return httpClient;
	}
	
	public void setHeader(Header header){
		if(httpHeader == null){
			httpHeader = new ArrayList<Header>();
		}
		httpHeader.add(header);
	}
	
	public void setHeader(String name,String value){
		BasicHeader header = new BasicHeader(name, value);
		if(httpHeader == null){
			httpHeader = new ArrayList<Header>();
		}
		httpHeader.add(header);
	}
	
	private static class GzipDecompressingEntity extends HttpEntityWrapper {

		public GzipDecompressingEntity(final HttpEntity entity) {
			super(entity);
		}

		@Override
		public InputStream getContent() throws IOException, IllegalStateException {

			// the wrapped entity's getContent() decides about repeatability
			InputStream wrappedin = wrappedEntity.getContent();

			return new GZIPInputStream(wrappedin);
		}

		@Override
		public long getContentLength() {
			// length of ungzipped content is not known
			return -1;
		}

	}
}
