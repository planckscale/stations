package com.home.stations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
// how to configure coverage happy without this?
public class StationsApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void applicationContextTest() {
		StationsApplication.main(new String[] {});
	}

}
