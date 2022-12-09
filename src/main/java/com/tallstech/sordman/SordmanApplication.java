package com.tallstech.sordman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class SordmanApplication {

	public static void main(String[] args) {
		SpringApplication.run(SordmanApplication.class, args);
	}

}
