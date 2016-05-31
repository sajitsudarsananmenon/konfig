package com.konfig.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.konfig.exception.ApplicationExceptionMapperTest;
import com.konfig.rs.ConfigResourceTest;
import com.konfig.rs.PingResourceTest;
import com.konfig.service.FileServiceTest;
import com.konfig.util.AppConfigTest;

@RunWith(Suite.class)
@SuiteClasses({ ConfigResourceTest.class, PingResourceTest.class, ConfigResourceTest.class, FileServiceTest.class,
		AppConfigTest.class, ApplicationExceptionMapperTest.class })
public class AllTests {

}
