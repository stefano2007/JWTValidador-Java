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
    private static final String[] ValidClaims = { "Name", "Role", "Seed" };
    private static final String[] ValidRoles = { "Admin", "Member", "External" };

    public static boolean Valid(String jwt) throws BaseException {
        var jwtToken = TryConvertToken(jwt);
        VerifyClaim(jwtToken);

        var jwtTokenModel = TokenToModel(jwtToken);

        VerifyName(jwtTokenModel);
        VerifyRole(jwtTokenModel);
        VerifySeed(jwtTokenModel);

        return true;
    }

    public static DecodedJWT TryConvertToken(String token) throws BaseException {
        try {
            return JWT.decode(token);
        } catch (Exception e) {
            throw new InvalidJWTException("Invalid JWT");
        }
    }

    public static JWTTokenModel TokenToModel(DecodedJWT jwtToken)
    {
        String name = jwtToken.getClaim("Name").asString();
        String role = jwtToken.getClaim("Role").asString();
        String seed = jwtToken.getClaim("Seed").asString();

        return new JWTTokenModel(
                name == null ? "": name,
                role == null ? "": role,
                seed == null ? "": seed
        );
    }

    private static void VerifyName(JWTTokenModel jwtTokenModel) throws BaseException {

        if (jwtTokenModel.getName().chars().anyMatch(Character::isDigit))
        {
            throw new InvalidDomainException("The Name cannot have a number character");
        }
        if (jwtTokenModel.getName().length() > 256)
        {
            throw new InvalidDomainException("The maximum length of the Name is 256 characters");
        }
    }

    private static void VerifyClaim(DecodedJWT jwtToken) throws BaseException {
        var isValidJWT = (jwtToken.getClaims().size() == 3
                && Stream.of(ValidClaims).noneMatch(cl -> jwtToken.getClaim(cl).isNull()));

        if (!isValidJWT)
        {
            throw new InvalidStructureException("Invalid token structure should contain only 3 claims(Name, Role and Seed)");
        }
    }

    private static void VerifyRole(JWTTokenModel jwtTokenModel) throws BaseException {
        if (Stream.of(ValidRoles).noneMatch(r -> jwtTokenModel.getRole().equals(r)))
        {
            throw new InvalidDomainException("The Role claim must contain only 1 of the three values (Admin, Member and External)");
        }
    }

    private static void VerifySeed(JWTTokenModel jwtTokenModel) throws BaseException {

        int seed;
        try{
            seed = Integer.parseInt(jwtTokenModel.getSeed());
        }catch (Exception ex){
            throw new InvalidDomainException("The Seed claim must be a prime number");
        }

        if (!IsPrimeNumber(seed))
        {
            throw new InvalidDomainException("The Seed claim must be a prime number");
        }
    }

    public static boolean IsPrimeNumber(int number) {
        if (number <= 1) {
            return false;
        }
        for (int divisor = 2; divisor <= Math.sqrt(number); divisor++) {
            if (number % divisor == 0) {
                return false;
            }
        }
        return true;
    }
}
