package com.example.ApiREST;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("com.example.ApiREST.entities")
@ComponentScan(basePackages = {"com.example.ApiREST.controllers", "com.example.ApiREST.repositories", "com.example.ApiREST.configurationSpring"})

public class ApiRestApplication {
	public static void main(String[] args) {
		SpringApplication.run(ApiRestApplication.class, args);
	}

}
