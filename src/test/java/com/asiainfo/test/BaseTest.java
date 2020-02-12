package com.asiainfo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/applicationContext.xml", "classpath:/applicationContext-dao.xml",
		"classpath:/applicationContext-security.xml" })
@ActiveProfiles(profiles = { "development" })

public class BaseTest {

	@Test
	public void test(){
		
	}
}
