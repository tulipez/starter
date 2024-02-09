package com.tulipez.starter.common;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class FreeMobileSmsSender {
	
	private String user;
	public void setUser(String user) { this.user = user; }
	
	private String password;
	public void setPassword(String password) { this.password = password; }

	public void send(String message) throws Exception {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append("https://smsapi.free-mobile.fr/sendmsg?user=" + user + "&pass=" + password + "&msg=");
		buffer.append(URLEncoder.encode(message, "UTF-8"));
		
		String request = buffer.toString();
		
		HttpURLConnection con = (HttpURLConnection) new URL(request).openConnection();
		con.setRequestMethod("GET");
		if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
			System.err.println("[" + this.getClass().getSimpleName() + "] [sendSms] Erreur " + con.getResponseCode() + " " + request);
		}
	}
}
