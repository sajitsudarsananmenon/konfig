package com.konfig.service;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.konfig.beans.ConfigInfo;
import com.konfig.beans.ConfigResponse;

public class FileServiceTest {
	FileService fs;

	@Before
	public void setUp() {
		fs = new FileService();
	}

	// test cases for get
	@Test
	public void whenInputConfigParamIsFoundInFileThenReturnValue() {
		ConfigResponse actual = fs.get("MyApp.test.config", "default", "konfig", "env");
		assertTrue(actual.getInfo().get(0).getParams().containsValue("ALL IS WELL"));
	}

	@Test
	public void whenInputConfigParamNotFoundInFileThenReturnNull() {
		ConfigResponse actual = fs.get("MyApp.test.foo", "default", "konfig", "env");
		assertNull(actual);
	}

	@Test
	public void whenInputConfigFileItselfNotFoundThenReturnNull() {
		ConfigResponse actual = fs.get("MyApp.test.foo", "default", "testApp", "env");
		assertNull(actual);
	}

	// test cases for getPath
	@Test
	public void whenInputPathIsDefaultThenGetFromAppConfig() {
		String[] files = new String[2];
		files[0] = "/app/conf/config.properties";
		files[1] = "/app/conf/env.properties";
		assertArrayEquals(files, fs.getPath("default", "MyApp"));
	}

	@Test
	public void whenInputPathIsEmptyThenGetFromAppConfig() {
		String[] files = new String[2];
		files[0] = "/app/conf/config.properties";
		files[1] = "/app/conf/env.properties";
		assertArrayEquals(files, fs.getPath("", "MyApp"));
	}

	@Test
	public void whenInputPathIsNullThenGetFromAppConfig() {
		String[] files = new String[2];
		files[0] = "/app/conf/config.properties";
		files[1] = "/app/conf/env.properties";
		assertArrayEquals(files, fs.getPath(null, "MyApp"));
	}

	@Test
	public void whenInputPathIsDefaultAndAppHasOnePropFileInAppConfig() {
		String[] files = new String[1];
		files[0] = "C:\\work\\Sajit\\workspace\\Confy\\konfig\\conf\\config.properties";
		assertArrayEquals(files, fs.getPath("default", "testApp3"));
	}

	@Test
	public void whenInputPathIsDefaultAndAppHasOnePropFileWithEndCommaInAppConfig() {
		String[] files = new String[1];
		files[0] = "/app/conf/config.properties";
		assertArrayEquals(files, fs.getPath("default", "testApp"));
	}

	@Test
	public void whenInputPathIsDefaultAndAppHasOnePropFileWithEmptyStringInAppConfig() {
		assertArrayEquals(null, fs.getPath("default", "testApp2"));
	}

	@After
	public void tearDown() {
		fs = null;
	}

}
