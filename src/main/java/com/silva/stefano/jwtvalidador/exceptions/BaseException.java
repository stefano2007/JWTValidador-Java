package com.silva.stefano.jwtvalidador.exceptions;

import com.silva.stefano.jwtvalidador.enumerators.ExceptionType;

public abstract class BaseException extends Exception {
    private final ExceptionType ExceptionType;

    protected BaseException(String error, ExceptionType exceptionType) {
        super(error);
        ExceptionType = exceptionType;
    }
    public ExceptionType getExceptionType() {
        return ExceptionType;
    }
}
