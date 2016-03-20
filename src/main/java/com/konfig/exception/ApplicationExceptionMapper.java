package com.konfig.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.konfig.beans.ErrorResponse;

@Provider
public class ApplicationExceptionMapper implements ExceptionMapper<GenericApplicationException>{

	@Override
	public Response toResponse(GenericApplicationException gae) {
		int errorCode=500;
		ErrorResponse error = new ErrorResponse();
		if (gae.thrownException instanceof ConfigResourceNotFoundException) {
			errorCode=404;
			error.setCode("404");
			error.setMessage("Either the config parameter or properties file is not available");
		} else if (gae.thrownException instanceof NullPointerException) {
			error.setCode("500");
			error.setMessage("Config not available or is null");
		} else if (gae.thrownException instanceof UnsupportedOperationException) {
			error.setCode("500");
			error.setMessage("Operation Not Supported Yet!");
		} else {
			error.setCode("500");
			error.setMessage("Unexpected System Error Occurred");
		}
		Response response =  Response.status(errorCode).entity(error).build();
		return response;
	}

}
