package com.konfig.rs;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.konfig.beans.ConfigRequest;
import com.konfig.beans.ConfigResponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
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
				.accept(MediaType.APPLICATION_JSON).header("path", "default").header("source", ".properties");
		ConfigResponse result = wr.get(ConfigResponse.class);
		assertTrue(result.getInfo().get(0).getParams().containsValue("config.properties,"));
	}

	@Test
	public void testGETwithoutHeaderswhenConfigParamFoundThenReturnValue() {
		WebResource.Builder wr = resource.path("/get/konfig/PROD/testApp.properties.files")
				.accept(MediaType.APPLICATION_JSON);
		ConfigResponse result = wr.get(ConfigResponse.class);
		assertTrue(result.getInfo().get(0).getParams().containsValue("config.properties,"));
	}

	@Test
	public void testGETwhenConfigParamNotFoundThenResourceNotFound() {
		WebResource.Builder wr = resource.path("/get/konfig/PROD/testApp.properties.fil")
				.accept(MediaType.APPLICATION_JSON).header("path", "default").header("source", ".properties");
		assertEquals(404, wr.get(ClientResponse.class).getStatus());
	}

	@Test
	public void testGETwhenConfigFileNotFoundThenResourceNotFound() {
		WebResource.Builder wr = resource.path("/get/dummy/PROD/testApp.properties.fil")
				.accept(MediaType.APPLICATION_JSON).header("path", "default").header("source", ".properties");
		assertEquals(404, wr.get(ClientResponse.class).getStatus());
	}

	// tests for getAll api
	@Test
	public void testGETALLwhenConfigFileFoundThenReturnValues() {
		WebResource.Builder wr = resource.path("/getall/konfig/PROD/").accept(MediaType.APPLICATION_JSON)
				.header("path", "default").header("source", ".properties");
		ClientResponse cresp = wr.get(ClientResponse.class);
		ConfigResponse result = cresp.getEntity(ConfigResponse.class);
		assertTrue(result.getInfo().size() == 2);
	}

	@Test
	public void testGETALLwithoutHeaderswhenConfigFileFoundThenReturnValues() {
		WebResource.Builder wr = resource.path("/getall/konfig/PROD/").accept(MediaType.APPLICATION_JSON);
		ClientResponse cresp = wr.get(ClientResponse.class);
		ConfigResponse result = cresp.getEntity(ConfigResponse.class);
		assertTrue(result.getInfo().size() == 2);
	}

	@Test
	public void testGETALLwhenConfigFileNotFoundThenReturnValues() {
		WebResource.Builder wr = resource.path("/getall/dummy/PROD/").accept(MediaType.APPLICATION_JSON)
				.header("path", "default").header("source", ".properties");
		assertEquals(404, wr.get(ClientResponse.class).getStatus());
	}

	@Test
	public void testUpdateConfigParamResourceIsReachable() {
		WebResource.Builder wr = resource.path("/update/konfig/PROD/").type(MediaType.APPLICATION_JSON);
		ConfigRequest cr = new ConfigRequest();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("MyApp.test.config", "ALL IS GREAT");
		cr.setParams(map);
		ClientResponse cresp = wr.put(ClientResponse.class, cr);
		assertEquals(204, cresp.getStatus());
	}

	@Test
	public void testUpdateConfigParamWhenConfigIsFound() {
		// Update param
		WebResource.Builder wrput = resource.path("/update/konfig/PROD/").type(MediaType.APPLICATION_JSON);
		ConfigRequest cr = new ConfigRequest();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("MyApp.test.config", "ALL IS GREAT");
		cr.setParams(map);
		ClientResponse cresp = wrput.put(ClientResponse.class, cr);
		assertEquals(204, cresp.getStatus());
		// Fetch updated value
		WebResource.Builder wrget = resource.path("/get/konfig/PROD/testApp.properties.files")
				.accept(MediaType.APPLICATION_JSON).header("path", "default").header("source", ".properties");
		ConfigResponse result = wrget.get(ConfigResponse.class);
		assertEquals("ALL IS GREAT", result.getInfo().get(0).getParams().get("MyApp.test.config"));
	}

	@After
	public void tearDown() throws Exception {
		grizzlyServer.stop();
	}
}
