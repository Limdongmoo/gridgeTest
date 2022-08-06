package com.example.GTC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jdbc.repository.config.EnableJdbcAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableJdbcAuditing
public class GtcApplication {

	public static void main(String[] args) {
		SpringApplication.run(GtcApplication.class, args);
	}

}
