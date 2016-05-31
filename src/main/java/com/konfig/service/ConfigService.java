package com.konfig.service;

import java.util.ArrayList;
import com.konfig.beans.ConfigInfo;
import com.konfig.beans.ConfigResponse;

/**
 * @author Sajit Sudarsanan
 * 
 *         The abstract class ConfigService needs to be extended by all the
 *         service handlers of the konfig application. FileService handler is
 *         one subclass of this class which handles the reading of .properties
 *         files. Multiple Service handlers can be added extending this class to
 *         support reading config from other sources for instance Database,
 *         MongoDB (NoSQL) and other file types like .xml or .yml files
 *
 */
public abstract class ConfigService {
	/**
	 * Subclassses should provide their implementation to fetch configuration
	 * info from their respective source as determined by the service method
	 * 
	 * @param configParam
	 * @param path
	 * @param appName
	 * @param env
	 * @return
	 */
	public abstract ConfigResponse get(String configParam, String path, String appName, String env);

	/**
	 * Subclassses should provide their implementation to fetch configuration
	 * info from their respective source as determined by the service method
	 * 
	 * @param path
	 * @param appName
	 * @param env
	 * @return
	 */
	public abstract ConfigResponse getAll(String path, String appName, String env);

	/**
	 * Builds the actual web service response which will be sent to the client
	 * application
	 * 
	 * @param configInfoList
	 * @param appName
	 * @param env
	 * @return
	 */
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

	/**
	 * This method dynamically identifies the service handler requested by using
	 * the type parameter from the request header
	 * 
	 * @param sourceType
	 * @return
	 */
	public static ConfigService service(String sourceType) {
		if (null != sourceType && !sourceType.isEmpty()) {
			if (sourceType.equalsIgnoreCase(".properties")) {
				return new FileService();
			} else {
				throw new UnsupportedOperationException();
			}
		}
		return null;
	}
}
