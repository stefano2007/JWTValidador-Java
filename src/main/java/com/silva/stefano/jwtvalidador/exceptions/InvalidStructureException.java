package com.silva.stefano.jwtvalidador.exceptions;

import com.silva.stefano.jwtvalidador.enumerators.ExceptionType;

public class InvalidStructureException  extends BaseException{
    public InvalidStructureException(String error){
        super(error, ExceptionType.INVALID_STRUCTURE);
    }
}