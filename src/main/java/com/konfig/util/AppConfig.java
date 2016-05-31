package com.konfig.util;

import java.util.ResourceBundle;

/**
 * @author Sajit Sudarsanan
 * 
 *         AppConfig class is a singleton class which reads the konfig
 *         application configuration once and provides method to fetch specific
 *         configuration parameter
 *
 */
public class AppConfig {
	private static AppConfig appConfig = null;
	ResourceBundle rb = null;

	private AppConfig() {
		rb = ResourceBundle.getBundle("config");
	}

	/**
	 * Returns the singleton instance of AppConfig if exists otherwise creates
	 * one and returns
	 * 
	 * @return
	 */
	public static AppConfig getInstance() {
		if (appConfig != null) {
			return appConfig;
		} else {
			appConfig = new AppConfig();
			return appConfig;
		}
	}

	/**
	 * Returns the value of the requested config parameter, returns null if
	 * param does not exist
	 * 
	 * @param configParam
	 * @return
	 */
	public static String getAppConfig(String configParam) {
		String configValue = null;
		try {
			configValue = getInstance().rb.getString(configParam);
		} catch (Exception e) {
			// logger to be configured
		}
		return configValue;
	}
}
