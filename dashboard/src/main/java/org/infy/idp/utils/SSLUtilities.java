/***********************************************************************************************
*
* Copyright 2018 Infosys Ltd.
* Use of this source code is governed by MIT license that can be found in the LICENSE file or at
* https://opensource.org/licenses/MIT.
*
***********************************************************************************************/
package org.infy.idp.utils;

import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public final class SSLUtilities {
	private SSLUtilities() {
		// TODO Auto-generated constructor stub
	}

	private static HostnameVerifier _hostnameVerifier;
	private static TrustManager[] _trustManagers;

	private static void trustAllHostname() {
		// Create a trust manager that does not validate certificate chains
		if (_hostnameVerifier == null) {
			_hostnameVerifier = new FakeHostnameVerifier();
		} // if
			// Install the all-trusting host name verifier:
		HttpsURLConnection.setDefaultHostnameVerifier(_hostnameVerifier);
	} // _trustAllHttpsCertificates

	private static void trustAllHttpsCertificate() {
		SSLContext context;
		// Create a trust manager that does not validate certificate chains
		if (_trustManagers == null) {
			_trustManagers = new TrustManager[] { new FakeX509TrustManager() };
		} // if
			// Install the all-trusting trust manager:
		try {
			context = SSLContext.getInstance("TLSv1.2");
			context.init(null, _trustManagers, new SecureRandom());
		} catch (GeneralSecurityException gse) {
			throw new IllegalStateException(gse.getMessage());
		} // catch
		HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
	} // _trustAllHttpsCertificates

	public static void trustAllHostnames() {
		trustAllHostname();
	} // trustAllHostnames

	public static void trustAllHttpsCertificates() {
		trustAllHttpsCertificate();
	} // trustAllHttpsCertificates



	@SuppressWarnings({"java:S3510"})
	public static class FakeHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, javax.net.ssl.SSLSession session) {
			return (true);
		} // verify
	} // FakeHostnameVerifier

	public static class FakeX509TrustManager implements X509TrustManager {
		private static final X509Certificate[] _AcceptedIssuers = new X509Certificate[] {};

		public void checkClientTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkClientTrusted

		public void checkServerTrusted(X509Certificate[] chain, String authType) {
			//implements parent method
		} // checkServerTrusted

		public X509Certificate[] getAcceptedIssuers() {
			return (_AcceptedIssuers);
		} // getAcceptedIssuers
	} // FakeX509TrustManager
} // SSLUtilities
