package com.silva.stefano.jwtvalidador.validations;

import com.silva.stefano.jwtvalidador.Fixtures.JWTFixture;
import com.silva.stefano.jwtvalidador.exceptions.BaseException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidDomainException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidJWTException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidStructureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

@SpringBootTest
public class JWTValidationTest {
    private final String MensagemErroTokenInvalido = "Invalid JWT";

    @Test
    void Verificar_Valid() throws BaseException{
        //Act e Arrange
        var result = JWTValidation.Valid(JWTFixture.JTW_VALIDO_CASO1);
        var exception2 = Assertions.assertThrows(InvalidJWTException.class,
                () -> JWTValidation.Valid(JWTFixture.JTW_INVALIDO_CASO2)
        );
        var exception3 = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JTW_INVALIDO_CASO3)
        );
        var exception4 = Assertions.assertThrows(InvalidStructureException.class,
                () -> JWTValidation.Valid(JWTFixture.JTW_INVALIDO_CASO4)
        );

        var exceptionJWTEstruturaInvalida = Assertions.assertThrows(InvalidStructureException.class,
                () -> JWTValidation.Valid(JWTFixture.JTW_INVALIDO_ESTRUTURA_JWT)
        );
        var exceptionRoleInvalida = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JTW_INVALIDO_ROLE_INVALIDA)
        );
        var exceptionSeedNaoPrimo = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JTW_INVALIDO_SEED_NAO_PRIMO)
        );
        var exceptionNameMaior256 = Assertions.assertThrows(InvalidDomainException.class,
                () -> JWTValidation.Valid(JWTFixture.JTW_INVALIDO_NAME_MAIOR_256)
        );

        //Assert
        String mensagemErroEstruturaInvalida = "Invalid token structure should contain only 3 claims(Name, Role and Seed)";

        Assertions.assertEquals("verdadeiro", result);
        Assertions.assertEquals(MensagemErroTokenInvalido, exception2.getMessage());
        Assertions.assertEquals("The Name claim cannot have a number character", exception3.getMessage());
        Assertions.assertEquals(mensagemErroEstruturaInvalida, exception4.getMessage());

        Assertions.assertEquals(mensagemErroEstruturaInvalida, exceptionJWTEstruturaInvalida.getMessage());
        Assertions.assertEquals("The Role claim must contain only 1 of the three values (Admin, Member, and External)", exceptionRoleInvalida.getMessage());
        Assertions.assertEquals("The Seed claim must be a prime number", exceptionSeedNaoPrimo.getMessage());
        Assertions.assertEquals("The maximum length of the Name claim is 256 characters", exceptionNameMaior256.getMessage());
    }

    @Test
    void Verificar_ValidateToken() throws BaseException {
        //Act e Arrange
        var jwtValido = JWTValidation.ValidateToken(JWTFixture.JTW_VALIDO_CASO1);
        var exceptionValidateToken = Assertions.assertThrows(InvalidJWTException.class, () -> JWTValidation.ValidateToken(JWTFixture.JTW_INVALIDO_CASO2));
        var jwt3 = JWTValidation.ValidateToken(JWTFixture.JTW_INVALIDO_CASO3);
        var jwt4 = JWTValidation.ValidateToken(JWTFixture.JTW_INVALIDO_CASO4);

        //Assert
        Assertions.assertEquals(3, jwtValido.getClaims().size());
        Assertions.assertEquals(MensagemErroTokenInvalido, exceptionValidateToken.getMessage());
        Assertions.assertEquals(3, jwt3.getClaims().size());
        Assertions.assertEquals(4, jwt4.getClaims().size());
    }

    @Test
    void Verificar_ConvertToken() throws BaseException {
        //Act e Arrange
        var tokenModel1 = JWTValidation.ConvertToken(
                JWTValidation.ValidateToken(JWTFixture.JTW_VALIDO_CASO1)
        );
        var tokenModel3 = JWTValidation.ConvertToken(
                JWTValidation.ValidateToken(JWTFixture.JTW_INVALIDO_CASO3)
        );
        var tokenModel4 = JWTValidation.ConvertToken(
                JWTValidation.ValidateToken(JWTFixture.JTW_INVALIDO_CASO4)
        );

        //Assert
        Assertions.assertEquals("Admin", tokenModel1.getRole());
        Assertions.assertEquals("7841", tokenModel1.getSeed());
        Assertions.assertEquals("Toninho Araujo", tokenModel1.getName());

        Assertions.assertEquals("External", tokenModel3.getRole());
        Assertions.assertEquals("88037", tokenModel3.getSeed());
        Assertions.assertEquals("M4ria Olivia", tokenModel3.getName());

        Assertions.assertEquals("Member", tokenModel4.getRole());
        Assertions.assertEquals("14627", tokenModel4.getSeed());
        Assertions.assertEquals("Valdir Aranha", tokenModel4.getName());
    }

    @Test
    void Test_Metodo_EhNumeroPrimo(){
        //Act
        int[] numeroPrimos = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97};
        int[] numeroNaoPrimos = {1, 4, 6 ,8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28, 30, 32, 33, 34, 35, 36};

        //Arrange
        var todosPrimos = Arrays.stream(numeroPrimos).allMatch(JWTValidation::EhNumeroPrimo);
        var todosNaoPrimos = Arrays.stream(numeroNaoPrimos).noneMatch(JWTValidation::EhNumeroPrimo);

        //Assert
        Assertions.assertTrue(todosPrimos);
        Assertions.assertTrue(todosNaoPrimos);
    }
}
