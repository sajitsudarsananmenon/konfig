package com.konfig.service;

import java.util.ArrayList;

import com.konfig.beans.ConfigInfo;
import com.konfig.beans.ConfigResponse;

public abstract class ConfigService {
	public abstract ConfigResponse get(String configParam, String path, String appName, String env);

	public abstract ConfigResponse getAll(String path, String appName, String env);

	public ConfigResponse build(ArrayList<ConfigInfo> configInfoList, String appName, String env) {
		ConfigResponse cResponse = null;
		if (null != configInfoList && configInfoList.size() > 0) {
			cResponse = new ConfigResponse();
			cResponse.setApp(appName);
			cResponse.setEnv(env);
			cResponse.setInfo(configInfoList);
		}
		return cResponse;
	}

	public static ConfigService service(String sourceType) {
		if (null != sourceType && !sourceType.isEmpty()) {
			if (sourceType.equalsIgnoreCase(".properties")) {
				return new FileService();
			} else {
				// Not Supported
			}
		}
		return null;
	}
}
