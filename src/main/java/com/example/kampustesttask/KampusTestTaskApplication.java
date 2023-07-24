package com.example.kampustesttask;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "kampus-test-task"))
@SpringBootApplication
public class KampusTestTaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(KampusTestTaskApplication.class, args);
	}

}
