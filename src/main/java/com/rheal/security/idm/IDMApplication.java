package com.rheal.security.idm;

import java.util.Objects;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM
 *
 */
@EnableHystrix
@SpringBootApplication(scanBasePackages = "com.rheal")
public class IDMApplication {
	public static final String ENVIRONMENT_NAME = "RHEAL_ENV";

	public static void main(String[] args) throws Exception {
		String env = System.getenv(ENVIRONMENT_NAME);
		new SpringApplicationBuilder(IDMApplication.class)
				.profiles(Objects.isNull(env) || env.isEmpty() ? "local" : env).build().run(args);
	}

}
