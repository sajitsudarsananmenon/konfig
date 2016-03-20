package com.konfig.util;

import java.util.ResourceBundle;

public class AppConfig {
	private static AppConfig appConfig = null;
	ResourceBundle rb=null;
	private AppConfig() {
		rb = ResourceBundle.getBundle("config");
	}

	public static AppConfig getInstance() {
		if (appConfig != null) {
			return appConfig;
		} else {
			appConfig = new AppConfig();
			return appConfig;
		}
	}
	
	public static String getAppConfig(String configParam){
		String configValue=null;
		try{
		configValue= getInstance().rb.getString(configParam);
		}catch(Exception e){
			//logger to be configured
		}
		return configValue;
	}
}
