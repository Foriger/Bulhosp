package com.bulhosp.client.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.enterprise.inject.Model;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;

import com.bulhosp.server.response.LoginResponse;
import com.bulhosp.session.utils.SessionUtilsSingleton;

@Model
public class LoginController {
	private String username;
	private String password;

	public String loginAttempt() {
		String result;
		String host = "https://api.parse.com/1/login";
		try {
			URI uri = new URIBuilder(host)
					.setParameter("username", username)
					.setParameter("password", password).build();
			HttpGet getRequest = new HttpGet(uri);
			getRequest.setHeader("X-Parse-Application-Id",
					"4wky9qF2hjZmOM6zU4WOSI6t6fkSPDFPxN0yY1f4");
			getRequest.setHeader("X-Parse-REST-API-Key",
					"ktB7WNVrbhwFIElE6a8Jq74daE1HqcDqyxtcHnEP");
			CloseableHttpClient httpclient = HttpClients.createDefault();
			CloseableHttpResponse response = httpclient.execute(getRequest);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != 200) {
				System.out.println("Failed with HTTP error code : "
						+ statusCode);
				result = "login";
			} else {
				System.out.println(response.toString());
				HttpEntity httpEntity = response.getEntity();

				ObjectMapper om = new ObjectMapper();
				LoginResponse loginResponse = om.readValue(EntityUtils
						.toByteArray(httpEntity), LoginResponse.class);
				SessionUtilsSingleton.getInstance().setSessionToken(loginResponse.getSessionToken());
				result = "index?faces-redirect=true";
			}		
		} catch (URISyntaxException e) {
			e.printStackTrace();
			result = "login";
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			result = "login";
		} catch (IOException e) {
			e.printStackTrace();
			result = "login";
		}

		return result;
	}

	public String logoutAttempt() {
		SessionUtilsSingleton.getInstance().setSessionToken(null);
		return "login";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
