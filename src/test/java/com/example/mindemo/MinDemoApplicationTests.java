package com.example.mindemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class MinDemoApplicationTests {
	@Autowired
	private ApplicationContext context;

	@Test
	void contextLoads() {
		/* Will fail before the assert if spring fails to load the context.
		 * The assert makes sure that spring is running this test (not just junit). */
		assertNotNull(context);
	}
}
