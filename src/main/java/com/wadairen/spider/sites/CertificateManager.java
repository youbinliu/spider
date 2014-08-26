package com.wadairen.spider.sites;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.log4j.Logger;

public class CertificateManager {
	
	private final static Logger logger = Logger.getLogger(CertificateManager.class);
		
	public static SSLSocketFactory gen(String cerfile){
		InputStream input;
		SSLSocketFactory ssl = SSLSocketFactory.getSocketFactory();
		try {
			input = CertificateManager.class.getResourceAsStream(cerfile);
			CertificateFactory cerFactory = CertificateFactory.getInstance("X.509");
			Certificate cer = cerFactory.generateCertificate(input);
			input.close();
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(null,null);
			ks.setCertificateEntry("trust", cer);
			ssl = new SSLSocketFactory(ks);
			
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		
		return ssl;
	}
}
