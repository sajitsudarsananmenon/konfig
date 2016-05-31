package com.konfig.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class AppConfigTest {

	@Test
	public void testAppConfigGetInstanceNotNull() {
		assertNotNull(AppConfig.getInstance());
	}

	@Test
	public void withInputConfigParameExistsInConfig() {
		assertEquals("ALL IS WELL", AppConfig.getAppConfig("MyApp.test.config"));
	}

	@Test
	public void withInputConfigParameNotExistingInConfig() {
		assertEquals(null, AppConfig.getAppConfig("foo"));
	}
}
