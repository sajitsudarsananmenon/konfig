package com.konfig.rs;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.konfig.beans.ConfigRequest;
import com.konfig.beans.ConfigResponse;
import com.konfig.exception.ConfigResourceNotFoundException;
import com.konfig.exception.GenericApplicationException;
import com.konfig.service.ConfigService;
import com.sun.istack.NotNull;

/**
 * @author Sajit Sudarsanan
 * 
 *         This resource class provides services for fetching configuration
 *         properties for the configured applications using get and getall apis.
 *         The applications must be configured to the konfig application.
 *
 */
@Path("/")
public class ConfigResource {

	/**
	 * This method provides the value of the config parameter requested for the
	 * respective application and environment
	 * 
	 * @param appName
	 *            Application Name
	 * @param env
	 *            Application Environment e.g. QA/PROD/PILOT/BETA
	 * @param configParam
	 *            Config for which value needs to be fetched
	 * @return
	 * @throws GenericApplicationException
	 */
	@GET
	@Path("/get/{app}/{env}/{param}/")
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigResponse get(@PathParam("app") String appName, @PathParam("env") String env,
			@PathParam("param") String configParam, @HeaderParam("path") String path,
			@HeaderParam("source") String sourceType) throws GenericApplicationException {
		if (path == null || path.isEmpty()) {
			path = "default";
		}
		if (sourceType == null || sourceType.isEmpty()) {
			sourceType = ".properties";
		}

		ConfigResponse response = null;
		try {
			ConfigService service = ConfigService.service(sourceType);
			response = service.get(configParam, path, appName, env);
		} catch (Exception e) {
			throw new GenericApplicationException(e);
		}
		if (null == response) {
			ConfigResourceNotFoundException crne = new ConfigResourceNotFoundException();
			throw new GenericApplicationException(crne);
		}
		return response;
	}

	/**
	 * This method provides all the configuration properties and values from
	 * multiple properties files configured for the application
	 * 
	 * @param appName
	 * @param env
	 * @return
	 * @throws GenericApplicationException
	 */
	@GET
	@Path("/getall/{app}/{env}/")
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigResponse getAll(@PathParam("app") String appName, @PathParam("env") String env,
			@HeaderParam("path") String path, @HeaderParam("source") String sourceType)
			throws GenericApplicationException {
		if (path == null || path.isEmpty()) {
			path = "default";
		}
		if (sourceType == null || sourceType.isEmpty()) {
			sourceType = ".properties";
		}
		ConfigResponse response = null;
		try {
			ConfigService service = ConfigService.service(sourceType);
			response = service.getAll(path, appName, env);
		} catch (Exception e) {
			throw new GenericApplicationException(e);
		}
		if (null == response) {
			ConfigResourceNotFoundException crne = new ConfigResourceNotFoundException();
			throw new GenericApplicationException(crne);
		}
		return response;
	}

	@PUT
	@Path("/update/{app}/{env}/")
	@Consumes(MediaType.APPLICATION_JSON)
	public void put(ConfigRequest request, @PathParam("app") String appName, @PathParam("env") String env,
			@HeaderParam("path") String path, @HeaderParam("source") String sourceType)
			throws GenericApplicationException {
		

	}
}
