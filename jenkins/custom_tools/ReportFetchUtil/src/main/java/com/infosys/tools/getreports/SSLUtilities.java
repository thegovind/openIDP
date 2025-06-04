/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package com.infosys.tools.getreports;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class SSLUtilities {
	private static HostnameVerifier hostnameVerifier;
	private static TrustManager[] trustManagers;
	
	

	private SSLUtilities() {
		// TODO Auto-generated constructor stub
	}

	private static void trustAllHostname() {
		// Create a trust manager that does not validate certificate chains
		if (hostnameVerifier == null) {
			hostnameVerifier = new FakeHostnameVerifier();
		} // if
			// Install the all-trusting host name verifier
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
	} // trustAllHostname

	private static void trustAllHttpsCertificate() {
		SSLContext context;
		// Create a trust manager that does not validate certificate chains
		if (trustManagers == null) {
			trustManagers = new TrustManager[] { new FakeX509TrustManager() };
		} // if
			// Install the all-trusting trust manager
		try {
			context = SSLContext.getInstance("SSL");
			context.init(null, trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} // catch
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	} // trustAllHttpsCertificate

	public static void trustAllHostnames() {
		trustAllHostname();
	} // trustAllHostnames

	public static void trustAllHttpsCertificates() {
		trustAllHttpsCertificate();
	} // trustAllHttpsCertificates

	public static class FakeHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
			return (true);
		} // verify
	} // FakeHostnameVerifier

	public static class FakeX509TrustManager implements X509TrustManager {
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkClientTrusted

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkServerTrusted

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} // getAcceptedIssuers
	} // FakeX509TrustManager
} // SSLUtilities
