package com.silva.stefano.jwtvalidador.validations;

import com.silva.stefano.jwtvalidador.Fixtures.JWTFixture;
import com.silva.stefano.jwtvalidador.exceptions.BaseException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidDomainException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidJWTException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidStructureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

@SpringBootTest
public class JWTValidationTest {
    private final String MessageErrorInvalidJWT = "Invalid JWT";
    private final String MessageErrorInvalidStructure = "Invalid token structure should contain only 3 claims(Name, Role and Seed)";

    @Test
    @DisplayName("Deve validar com sucesso Caso 1")
    void ShouldValidWithSucessCase1() throws BaseException{
        //Act e Arrange
        var result = JWTValidation.Valid(JWTFixture.JWT_VALIDO_CASO1);

        //Assert
        Assertions.assertTrue(result);
    }

    @Test
    @DisplayName("Deve falhar ao validar o Caso 2 - Token invalido")
    void ShouldReturnExceptionCase2() throws BaseException{
        //Act e Arrange
        var exception = Assertions.assertThrows(InvalidJWTException.class,
                () -> JWTValidation.Valid(JWTFixture.JWT_INVALIDO_CASO2)
        );

        //Assert
        Assertions.assertEquals(MessageErrorInvalidJWT, exception.getMessage());
    }

    @Test
    @DisplayName("Deve falhar ao validar o Caso 3 - Nome invalido")
    void ShouldReturnExceptionCase3() throws BaseException{
        //Act e Arrange
        var exception = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JWT_INVALIDO_CASO3)
        );

        //Assert
        Assertions.assertEquals("The Name cannot have a number character", exception.getMessage());
    }

    @Test
    @DisplayName("Deve falhar ao validar o Caso 4 - Nome invalido")
    void ShouldReturnExceptionCase4() throws BaseException{
        //Act e Arrange
        var exception = Assertions.assertThrows(InvalidStructureException.class,
                () -> JWTValidation.Valid(JWTFixture.JWT_INVALIDO_CASO4)
        );

        //Assert

        Assertions.assertEquals(MessageErrorInvalidStructure, exception.getMessage());
    }

    @Test
    @DisplayName("Deve falhar ao validar Token com Estrutura invalida")
    void ShouldReturnExceptionInvalidStructure() throws BaseException{
        //Act e Arrange
        var exceptionJWTInvalidStructure = Assertions.assertThrows(InvalidStructureException.class,
                () -> JWTValidation.Valid(JWTFixture.JWT_INVALIDO_ESTRUTURA_JWT)
        );

        //Assert
        Assertions.assertEquals(MessageErrorInvalidStructure, exceptionJWTInvalidStructure.getMessage());
    }

    @Test
    @DisplayName("Deve falhar ao validar Token com Role diferente de (Admin, Member e External)")
    void ShouldReturnExceptionInvalidRole() throws BaseException{
        //Act e Arrange
        var exceptionInvalidRole = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JWT_INVALIDO_ROLE_INVALIDA)
        );

        //Assert
        Assertions.assertEquals(
                "The Role claim must contain only 1 of the three values (Admin, Member and External)",
                exceptionInvalidRole.getMessage()
        );
    }

    @Test
    @DisplayName("Deve falhar ao validar Token com Seed com numero não primo")
    void ShouldReturnExceptionInvalidSeed() throws BaseException{
        //Act e Arrange
        var exceptionSeedNaoPrimo = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JWT_INVALIDO_SEED_NAO_PRIMO)
        );

        //Assert
        Assertions.assertEquals("The Seed claim must be a prime number", exceptionSeedNaoPrimo.getMessage());
    }

    @Test
    @DisplayName("Deve falhar ao validar Token Name com mais de 256 caracteres")
    void ShouldReturnExceptionInvalidName() throws BaseException{
        //Act e Arrange
        var exceptionNameMaior256 = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JWT_INVALIDO_NAME_MAIOR_256)
        );

        //Assert
        Assertions.assertEquals("The maximum length of the Name is 256 characters", exceptionNameMaior256.getMessage());
    }

    @Test
    @DisplayName("Deve converter o jwt Caso 1 com Sucesso")
    void ShouldTryTokenToModelWithSucess() throws BaseException {
        //Act e Arrange
        var jwtDecode = JWTValidation.TryConvertToken(JWTFixture.JWT_VALIDO_CASO1);

        //Assert
        Assertions.assertEquals(3, jwtDecode.getClaims().size());
    }

    @Test
    @DisplayName("Deve lançar exceção ao converte o jwt Caso 2 inválido")
    void ShouldTryTokenToModelWithFailCase2() throws BaseException {
        //Act e Arrange
        var exceptionValidateToken = Assertions.assertThrows(InvalidJWTException.class, () -> JWTValidation.TryConvertToken(JWTFixture.JWT_INVALIDO_CASO2));

        //Assert
        Assertions.assertEquals(MessageErrorInvalidJWT, exceptionValidateToken.getMessage());
    }

    @Test
    @DisplayName("Deve converter o jwt Caso 3 com Sucesso")
    void ShouldTryTokenToModelWithSucessCase3() throws BaseException {
        //Act e Arrange
        var jwt3 = JWTValidation.TryConvertToken(JWTFixture.JWT_INVALIDO_CASO3);

        //Assert
        Assertions.assertEquals(3, jwt3.getClaims().size());
    }

    @Test
    @DisplayName("Deve converter o jwt Caso 4 com Sucesso")
    void ShouldTryTokenToModelWithSucessCase4() throws BaseException {
        //Act e Arrange
        var jwt4 = JWTValidation.TryConvertToken(JWTFixture.JWT_INVALIDO_CASO4);

        //Assert
        Assertions.assertEquals(4, jwt4.getClaims().size());
    }


    @Test
    @DisplayName("Deve converte o JWT do caso de teste 1 com Sucesso")
    void testTokenToModelCaso1() throws BaseException {
        //Act
        var jwtCaso1 = JWTValidation
                .TryConvertToken(JWTFixture.JWT_VALIDO_CASO1);

        // Arrange
        var tokenModel = JWTValidation
                    .TokenToModel(jwtCaso1);
        //Assert
        Assertions.assertEquals("Admin", tokenModel.getRole());
        Assertions.assertEquals("7841", tokenModel.getSeed());
        Assertions.assertEquals("Toninho Araujo", tokenModel.getName());
    }

    @Test
    @DisplayName("Deve converte o JWT do caso de teste 3 com Sucesso")
    void testTokenToModelCaso3() throws BaseException {
        //Act
        var jwtCaso3 = JWTValidation
                .TryConvertToken(JWTFixture.JWT_INVALIDO_CASO3);

        // Arrange
        var tokenModel = JWTValidation
                .TokenToModel(jwtCaso3);

        //Assert
        Assertions.assertEquals("External", tokenModel.getRole());
        Assertions.assertEquals("88037", tokenModel.getSeed());
        Assertions.assertEquals("M4ria Olivia", tokenModel.getName());
    }

    @Test
    @DisplayName("Deve converte o JWT do caso de teste 4 com Sucesso")
    void testConvertTokenCaso4() throws BaseException {
        //Act
        var jwtCaso4 = JWTValidation
                .TryConvertToken(JWTFixture.JWT_INVALIDO_CASO4);

        // Arrange
        var tokenModel = JWTValidation
                .TokenToModel(jwtCaso4);

        //Assert
        Assertions.assertEquals("Member", tokenModel.getRole());
        Assertions.assertEquals("14627", tokenModel.getSeed());
        Assertions.assertEquals("Valdir Aranha", tokenModel.getName());
    }

    @Test
    @DisplayName("Deve testar um range de números primos todos retornar verdadeiro")
    void testIsPrimeNumber() {
        //Act
        int[] numbersPrime = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};

        //Arrange
        var allPrimes = Arrays.stream(numbersPrime).allMatch(JWTValidation::IsPrimeNumber);

        //Assert
        Assertions.assertTrue(allPrimes);
    }

    @Test
    @DisplayName("Deve testar um range de números não primos e todos retornaram falso")
    void testIsPrimeNumberFalse() {
        //Act
        int[] nonPrimeNumbers = {1, 4, 6 ,8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36};

        //Arrange
        var result = Arrays.stream(nonPrimeNumbers).noneMatch(JWTValidation::IsPrimeNumber);

        //Assert
        Assertions.assertTrue(result);
    }
}
