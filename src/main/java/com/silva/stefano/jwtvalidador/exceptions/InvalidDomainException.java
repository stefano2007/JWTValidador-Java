package com.silva.stefano.jwtvalidador.exceptions;

import com.silva.stefano.jwtvalidador.enumerators.TipoExcecao;

public class InvalidDomainException extends BaseException{
    public InvalidDomainException(String error){
        super(error, TipoExcecao.INVALID_DOMAIN);
    }
}
