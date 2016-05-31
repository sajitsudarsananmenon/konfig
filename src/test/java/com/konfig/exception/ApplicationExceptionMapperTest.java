package com.konfig.exception;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.konfig.beans.ConfigResponse;

public class ApplicationExceptionMapperTest {

	ApplicationExceptionMapper aem = null;

	@Before
	public void setUp() {
		aem = new ApplicationExceptionMapper();
	}

	@Test
	public void whenResourceDoesNotExists() {
		GenericApplicationException gae = new GenericApplicationException(new ConfigResourceNotFoundException());
		Response response = aem.toResponse(gae);
		ConfigResponse configResponse = (ConfigResponse) response.getEntity();
		assertEquals("Either the config parameter or properties file is not available",
				configResponse.getError().getMessage());
	}

	@Test
	public void whenNullPointerExceptionOccurs() {
		GenericApplicationException gae = new GenericApplicationException(new NullPointerException());
		Response response = aem.toResponse(gae);
		ConfigResponse configResponse = (ConfigResponse) response.getEntity();
		assertEquals("Config value not set or is null", configResponse.getError().getMessage());
	}

	@Test
	public void whenUnsupportedOperationExceptionOccurs() {
		GenericApplicationException gae = new GenericApplicationException(new UnsupportedOperationException());
		Response response = aem.toResponse(gae);
		ConfigResponse configResponse = (ConfigResponse) response.getEntity();
		assertEquals("Operation Not Supported Yet!", configResponse.getError().getMessage());
	}
	
	@Test
	public void whenUnexpectedExceptionOccurs() {
		GenericApplicationException gae = new GenericApplicationException(new ArrayIndexOutOfBoundsException());
		Response response = aem.toResponse(gae);
		ConfigResponse configResponse = (ConfigResponse) response.getEntity();
		assertEquals("Unexpected System Error Occurred", configResponse.getError().getMessage());
	}

}
