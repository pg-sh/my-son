package com.son;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MySonApplication {

	public static void main(String[] args) {
		SpringApplication.run(MySonApplication.class, args);
	}

}

