package com.silva.stefano.jwtvalidador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JWTValidadorApplication {

	public static void main(String[] args) {
		SpringApplication.run(JWTValidadorApplication.class, args);
	}

	/*
	TODO: Documentar Swagger
	@Bean
	private OpenAPI openInfo() {
		Info info = new Info()
				.description("Validar estrutura JWT com regras definidas")
				.title("JWT Validador")
				.version("v1");

		return new OpenAPI().info(info);
	}*/
}
