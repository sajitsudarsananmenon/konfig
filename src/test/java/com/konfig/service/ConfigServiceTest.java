package com.konfig.service;

import static org.junit.Assert.*;

import org.junit.Test;

public class ConfigServiceTest {

	@Test
	public void testServiceNotNull() {
		assertNotNull(ConfigService.service(".properties"));
	}
	
	@Test
	public void testServiceIsNull() {
		assertNull(ConfigService.service(".xml"));
	}

	@Test
	public void testConfigResponseIsNull() {
		ConfigService service=ConfigService.service(".properties");
		assertNull(service.build(null, "konfig", "PROD"));
	}


}
