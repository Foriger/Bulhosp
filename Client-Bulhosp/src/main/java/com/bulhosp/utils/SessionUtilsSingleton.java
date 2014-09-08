package com.bulhosp.utils;

public class SessionUtilsSingleton {
	private String sessionToken;
	
	private static  SessionUtilsSingleton instance = null;
	
	protected SessionUtilsSingleton() {
		
	}
	
	public static SessionUtilsSingleton getInstance(){
		if(instance == null){
			instance = new SessionUtilsSingleton();
		}
		return instance;
	}

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public static void setInstance(SessionUtilsSingleton instance) {
		SessionUtilsSingleton.instance = instance;
	}
	
	public void clearSessionToken(){
		this.sessionToken = null;
	}
}
