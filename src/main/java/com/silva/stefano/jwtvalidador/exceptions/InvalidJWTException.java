package com.silva.stefano.jwtvalidador.exceptions;

import com.silva.stefano.jwtvalidador.enumerators.ExceptionType;

public class InvalidJWTException extends BaseException{
    public InvalidJWTException(String error){
        super(error, ExceptionType.INVALID_JWT);
    }
}
