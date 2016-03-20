package com.konfig.rs;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.konfig.beans.ConfigResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;

public class ConfigResourceTest {

	private HttpServer grizzlyServer;
	private WebResource resource;

	@Before
	public void setUp() throws Exception {
		// start the Grizzly2 web container
		grizzlyServer = Main.startServer();
		// create the jersey client
		Client c = Client.create();
		resource = c.resource(Main.BASE_URI);
	}

	// tests for get api
	@Test
	public void testGETwhenConfigParamFoundThenReturnValue() {
		WebResource.Builder wr = resource.path("/get/konfig/PROD/testApp.properties.files")
				.accept(MediaType.APPLICATION_JSON);
		ConfigResponse result = wr.get(ConfigResponse.class);
		assertTrue(result.getInfo().get(0).getParams().containsValue("config.properties,"));
	}

	/* Exception Mapper does not work for Junit */
	@Test(expected = UniformInterfaceException.class)
	public void testGETwhenConfigParamNotFoundThenThrowException() {
		WebResource.Builder wr = resource.path("/get/konfig/PROD/testApp.properties.fil")
				.accept(MediaType.APPLICATION_JSON);
		wr.get(ConfigResponse.class);
	}

	/* Exception Mapper does not work for Junit */
	@Test(expected = UniformInterfaceException.class)
	public void testGETwhenConfigFileNotFoundThenThrowException() {
		WebResource.Builder wr = resource.path("/get/dummy/PROD/testApp.properties.fil")
				.accept(MediaType.APPLICATION_JSON);
		wr.get(ConfigResponse.class);
	}

	// tests for getAll api
	@Test
	public void testGETALLwhenConfigFileFoundThenReturnValues() {
		WebResource.Builder wr = resource.path("/getall/konfig/PROD/").accept(MediaType.APPLICATION_JSON);
		ConfigResponse result = wr.get(ConfigResponse.class);
		assertTrue(result.getInfo().size() == 2);
	}

	/* Exception Mapper does not work for Junit */
	@Test(expected = UniformInterfaceException.class)
	public void testGETALLwhenConfigFileNotFoundThenReturnValues() {
		WebResource.Builder wr = resource.path("/getall/dummy/PROD/").accept(MediaType.APPLICATION_JSON);
		wr.get(ConfigResponse.class);
	}

	@After
	public void tearDown() throws Exception {
		grizzlyServer.stop();
	}
}
