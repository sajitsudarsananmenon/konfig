package com.konfig.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.konfig.beans.ConfigResponse;
import com.konfig.exception.ConfigResourceNotFoundException;
import com.konfig.exception.GenericApplicationException;
import com.konfig.service.ConfigService;

@Path("/")
public class ConfigResource {

	@GET
	@Path("/get/{app}/{env}/{param}/")
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigResponse get(@PathParam("app") String appName, @PathParam("env") String env,
			@PathParam("param") String configParam) throws GenericApplicationException {
		// Default for now, would be a header param
		String path = "default";
		String sourceType = ".properties";
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
	
	@GET
	@Path("/getall/{app}/{env}/")
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigResponse getAll(@PathParam("app") String appName, @PathParam("env") String env) throws GenericApplicationException {
		// Default for now, would be a header param
		String path = "default";
		String sourceType = ".properties";
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
}
