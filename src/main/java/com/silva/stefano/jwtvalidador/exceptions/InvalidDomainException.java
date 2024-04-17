package com.silva.stefano.jwtvalidador.exceptions;

import com.silva.stefano.jwtvalidador.enumerators.ExceptionType;

public class InvalidDomainException extends BaseException{
    public InvalidDomainException(String error){
        super(error, ExceptionType.INVALID_DOMAIN);
    }
}
