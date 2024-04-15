package com.silva.stefano.jwtvalidador;

import com.silva.stefano.jwtvalidador.Fixtures.JWTFixture;
import com.silva.stefano.jwtvalidador.controller.ValidadorController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import org.mockito.InjectMocks;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Assertions;

@RunWith(SpringRunner.class)
@WebMvcTest(ValidadorController.class)
class JWTValidadorApplicationTests {

	@InjectMocks
	private ValidadorController validadorController;

	@Test
	void Valid_ReturnsOkResponse() {
		// Arrange & Act
		ResponseEntity<String> response = validadorController.Get(JWTFixture.JTW_VALIDO_CASO1);

		// Assert
		Assertions.assertEquals(200, response.getStatusCodeValue());
		Assertions.assertEquals("verdadeiro", response.getBody());
	}

	@Test
	void InvalidJwt_ReturnsBadRequestResponse() {
		// Arrange & Act
		ResponseEntity<String> response = validadorController.Get(JWTFixture.JTW_INVALIDO_CASO2);

		// Assert
		String exceptionMessage = response.getHeaders().getFirst("Exception-Message");
		String exceptionType = response.getHeaders().getFirst("Exception-Type");

		Assertions.assertEquals(400, response.getStatusCodeValue());
		Assertions.assertEquals("falso", response.getBody());

		Assertions.assertEquals("Invalid JWT", exceptionMessage);
		Assertions.assertEquals("INVALID_JWT", exceptionType);
	}

	@Test
	void InvalidName_ReturnsBadRequestResponse() {
		// Arrange & Act
		ResponseEntity<String> response = validadorController.Get(JWTFixture.JTW_INVALIDO_CASO3);

		// Assert
		String exceptionMessage = response.getHeaders().getFirst("Exception-Message");
		String exceptionType = response.getHeaders().getFirst("Exception-Type");

		Assertions.assertEquals(400, response.getStatusCodeValue());
		Assertions.assertEquals("falso", response.getBody());

		Assertions.assertEquals("The Name claim cannot have a number character", exceptionMessage);
		Assertions.assertEquals("INVALID_DOMAIN", exceptionType);
	}

	@Test
	void InvalidStruture_ReturnsBadRequestResponse() {
		// Arrange & Act
		ResponseEntity<String> response = validadorController.Get(JWTFixture.JTW_INVALIDO_CASO4);

		// Assert
		String exceptionMessage = response.getHeaders().getFirst("Exception-Message");
		String exceptionType = response.getHeaders().getFirst("Exception-Type");

		Assertions.assertEquals(400, response.getStatusCodeValue());
		Assertions.assertEquals("falso", response.getBody());

		Assertions.assertEquals("Invalid token structure should contain only 3 claims(Name, Role and Seed)", exceptionMessage);
		Assertions.assertEquals("INVALID_STRUCTURE", exceptionType);
	}
}
