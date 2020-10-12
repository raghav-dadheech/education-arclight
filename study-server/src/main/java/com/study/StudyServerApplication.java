package com.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class StudyServerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(StudyServerApplication.class, args);
	}

	@Override
	  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
	      return builder.sources(StudyServerApplication.class);
	  }

}
