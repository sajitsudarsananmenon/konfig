package com.konfig.rs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.MediaType;

import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.header.MediaTypes;

public class PingResourceTest {

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

	@Test
	public void testPingService() {
		String responseMsg = resource.path("ping").accept(MediaType.APPLICATION_JSON).get(String.class);
		assertEquals("{\"status\":\"ok\"}", responseMsg);
	}

	@Test
	public void testApplicationWadl() {
		String serviceWadl = resource.path("application.wadl").accept(MediaTypes.WADL).get(String.class);
		assertTrue(serviceWadl.length() > 0);
	}

	@After
	public void tearDown() throws Exception {
		grizzlyServer.stop();
	}
}
