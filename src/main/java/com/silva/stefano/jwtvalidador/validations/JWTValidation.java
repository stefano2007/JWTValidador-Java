package com.silva.stefano.jwtvalidador.validations;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.silva.stefano.jwtvalidador.exceptions.BaseException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidDomainException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidJWTException;
import com.silva.stefano.jwtvalidador.exceptions.InvalidStructureException;
import com.silva.stefano.jwtvalidador.model.JWTTokenModel;

import java.util.stream.Stream;

public class JWTValidation {
/*
Construa uma aplicação que exponha uma api web que recebe por parâmetros um JWT (string) e verifica se é válida, conforme regras abaixo:
• Deve ser um JWT válido
• Deve conter apenas 3 claims(Name, Role e Seed)
• A claim Name não pode ter carácter de números
• A claim Role deve conter apenas 1 dos três valores (Admin, Member e External)
• A claim Seed deve ser um número primo.
• O tamanho máximo da claim Name é de 256 caracteres.
*/
    private static final String[] ClaimsValidas = { "Name", "Role", "Seed" };
    private static final String[] RolesValidas = { "Admin", "Member", "External" };

    public static String Valid(String jwt) throws BaseException {
        var jwtToken = ValidateToken(jwt);
        VerificarClaim(jwtToken);

        var jwtTokenModel = ConvertToken(jwtToken);

        VerificarName(jwtTokenModel);
        VerificarRole(jwtTokenModel);
        VerificarNumero(jwtTokenModel);

        return "verdadeiro";
    }

    public static DecodedJWT  ValidateToken(String token) throws BaseException {
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            throw new InvalidJWTException("Invalid JWT");
        }
    }

    public static JWTTokenModel ConvertToken(DecodedJWT jwtToken)
    {
        String name = "";
        String role = "";
        String seed = "";

        //TODO: Verificar se causa excesão quando não encontrado
        try{
            name = jwtToken.getClaim("Name").asString();
            role = jwtToken.getClaim("Role").asString();
            seed = jwtToken.getClaim("Seed").asString();
        }catch (Exception ex){

        }
        return new JWTTokenModel(name, role, seed);
    }

    private static void VerificarName(JWTTokenModel jwtTokenModel) throws BaseException {

        if (jwtTokenModel.getName().chars().anyMatch(x -> Character.isDigit(x)))
        {
            throw new InvalidDomainException("The Name claim cannot have a number character");
        }
        if (jwtTokenModel.getName().length() > 256)
        {
            throw new InvalidDomainException("The maximum length of the Name claim is 256 characters");
        }
    }

    private static void VerificarClaim(DecodedJWT jwtToken) throws BaseException {
        var isJWTValido = (jwtToken.getClaims().size() == 3
                && Stream.of(ClaimsValidas).allMatch(cl -> !jwtToken.getClaim(cl).isNull()));

        if (!isJWTValido)
        {
            throw new InvalidStructureException("Invalid token structure should contain only 3 claims(Name, Role and Seed)");
        }
    }

    private static void VerificarRole(JWTTokenModel jwtTokenModel) throws BaseException {
        if (Stream.of(RolesValidas).noneMatch(r -> jwtTokenModel.getRole().equals(r)))
        {
            throw new InvalidDomainException("The Role claim must contain only 1 of the three values (Admin, Member, and External)");
        }
    }

    private static void VerificarNumero(JWTTokenModel jwtTokenModel) throws BaseException {
        boolean isNumero = false;
        int seed = -1;
        try{
            seed = Integer.parseInt(jwtTokenModel.getSeed());
            isNumero= true;
        }catch (Exception ex){

        }
        var isPrimo = isNumero && VerificaNumeroPrimo(seed);

        if (!isPrimo )
        {
            throw new InvalidDomainException("The Seed claim must be a prime number");
        }
    }

    private static boolean VerificaNumeroPrimo(int numero) {
        if (numero <= 1) {
            return false;
        }
        for (int divisor = 2; divisor <= Math.sqrt(numero); divisor++) {
            if (numero % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
