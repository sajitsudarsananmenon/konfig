
package com.konfig.rs;

import java.io.IOException;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

/**
 * @author Sajit Sudarsanan
 * 
 *         This Main class sets up the grizzly2 web container by configuring
 *         port/base uri and configuring the resource package of the
 *         application. This class will be only referenced by the JUnit Tests
 *         and not by the actual application. Every test case written to test
 *         the resource layer would @Before start server using Main and @After
 *         stop server using Main
 * 
 */
public class Main {
	/**
	 * This method reads the port from system property <jersey.test.port> if
	 * defined else sets the default port as 9998.
	 * 
	 * @param defaultPort
	 * @return
	 */
	private static int getPort(int defaultPort) {
		// grab port from environment, otherwise fall back to default port 9998
		String httpPort = System.getProperty("jersey.test.port");
		if (null != httpPort) {
			try {
				return Integer.parseInt(httpPort);
			} catch (NumberFormatException e) {
			}
		}
		return defaultPort;
	}

	/**
	 * Builds the base uri of the application, since this is for JUnit this is
	 * hardcoded to localhost
	 * 
	 * @return
	 */
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost/").port(getPort(9998)).build();
	}

	public static final URI BASE_URI = getBaseURI();

	/**
	 * This method configures the resource package then creates and starts the
	 * grizzly2 server instance for unit testing
	 * 
	 * @return
	 * @throws IOException
	 */
	protected static HttpServer startServer() throws IOException {
		ResourceConfig resourceConfig = new PackagesResourceConfig("com.konfig");

		System.out.println("Starting grizzly2...");
		return GrizzlyServerFactory.createHttpServer(BASE_URI, resourceConfig);
	}

	/*
	 * public static void main(String[] args) throws IOException { // Grizzly 2
	 * initialization HttpServer httpServer = startServer();
	 * System.out.println(String.format(
	 * "Jersey app started with WADL available at " +
	 * "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
	 * System.in.read(); httpServer.stop(); }
	 */
}
