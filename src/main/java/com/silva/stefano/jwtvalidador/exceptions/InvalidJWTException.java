package com.silva.stefano.jwtvalidador.exceptions;

import com.silva.stefano.jwtvalidador.enumerators.TipoExcecao;

public class InvalidJWTException extends BaseException{
    public InvalidJWTException(String error){
        super(error, TipoExcecao.INVALID_JWT);
    }
}
