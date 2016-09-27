package com.konfig.service;

import com.konfig.beans.ConfigInfo;
import com.konfig.beans.ConfigResponse;
import com.konfig.util.AppConfig;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;

/**
 * @author Sajit Sudarsanan
 * 
 *         File service handlers class reads the .properties files from
 *         specified application configuration path
 *
 */
public class FileService extends ConfigService {
	
	/**
	 * This method looks through the multiple config properties files of the
	 * application to get the value of the requested config parameter
	 */
	public ConfigResponse get(String configParam, String path, String appName, String env) {
		ArrayList<ConfigInfo> cInfoList = null;
		String configValue = "";
		String source = "";
		String[] actualPath = getPath(path, appName);
		if (null != actualPath && actualPath.length > 0) {
			final Properties props = new Properties();
			for (int i = 0; i < actualPath.length; i++) {
				try {
					props.load(new FileInputStream(actualPath[i]));
					if (props.containsKey(configParam)) {
						configValue = props.getProperty(configParam);
						source = actualPath[i];
						break;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if (null != configValue && !configValue.isEmpty()) {
				cInfoList = new ArrayList<ConfigInfo>();
				ConfigInfo cInfo = buildConfigInfo(configParam, configValue, source);
				cInfoList.add(cInfo);
			}
		}

		return build(cInfoList, appName, env);
	}

	/**
	 * This methods returns all the config values present in multiple properties
	 * files present for the application
	 */
	public ConfigResponse getAll(String path, String appName, String env) {
		ArrayList<ConfigInfo> cInfoList = new ArrayList<ConfigInfo>();
		ConfigInfo cInfo = null;
		String source = "";
		String[] actualPath = getPath(path, appName);
		if (null != actualPath && actualPath.length > 0) {
			for (int i = 0; i < actualPath.length; i++) {
				try {
					final Properties props = new Properties();
					props.load(new FileInputStream(actualPath[i]));
					source = actualPath[i];
					cInfo = buildConfigInfoAll(props);
					cInfo.setSource(source);
					cInfoList.add(cInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}

		return build(cInfoList, appName, env);
	}

	/**
	 * This methods returns the fully qualified config path for the application
	 * using the path parameter from the request header and the konfig
	 * applications configuration. If path is not present in request header or
	 * value is "default", this method will return the path of the application
	 * config from the konfig configuration
	 * 
	 * @param path
	 * @param appName
	 * @return
	 */
	public String[] getPath(String path, String appName) {
		String actualPath = "";
		String files = "";
		String[] fileLocation = null;
		if (null == path || path.isEmpty() || path.equalsIgnoreCase("default")) {
			actualPath = AppConfig.getAppConfig(appName + ".config.path");
		} else {
			actualPath = path;
		}
		files = AppConfig.getAppConfig(appName + ".properties.files");
		if (null != files && !files.isEmpty()) {
			if (files.contains(",")) {
				fileLocation = files.split(",");
				for (int i = 0; i < fileLocation.length; i++) {
					fileLocation[i] = actualPath + fileLocation[i];
				}
			} else {
				fileLocation = new String[1];
				fileLocation[0] = actualPath + files;
			}
		}
		return fileLocation;
	}

	/**
	 * Builds the ConfigInfo bean for the requested config parameter based on
	 * the given inputs
	 * 
	 * @param key
	 * @param value
	 * @param source
	 * @return
	 */
	private ConfigInfo buildConfigInfo(String key, String value, String source) {
		ConfigInfo cInfo = new ConfigInfo();
		HashMap<String, String> keyVal = new HashMap<String, String>();
		cInfo.setSource(source);
		keyVal.put(key, value);
		cInfo.setParams(keyVal);
		return cInfo;
	}

	/**
	 * Builds the ConfigInfo bean for the all config parameters available for
	 * given application
	 * 
	 * @param props
	 * @return
	 */
	private ConfigInfo buildConfigInfoAll(Properties props) {
		ConfigInfo cInfo = new ConfigInfo();
		HashMap<String, String> keyVal = new HashMap<String, String>();
		String configValue = "";
		Set<String> keyset = props.stringPropertyNames();
		for (String configParam : keyset) {
			configValue = props.getProperty(configParam);
			if (null != configValue && !configValue.isEmpty()) {
				keyVal.put(configParam, configValue);
			}
		}
		cInfo.setParams(keyVal);
		return cInfo;
	}
    private boolean getBool(){
        boolean flag=false;
        return flag;
    }
}
