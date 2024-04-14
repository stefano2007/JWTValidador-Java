package com.silva.stefano.jwtvalidador.exceptions;

import com.silva.stefano.jwtvalidador.enumerators.TipoExcecao;

public class InvalidStructureException  extends BaseException{
    public InvalidStructureException(String error){
        super(error, TipoExcecao.INVALID_STRUCTURE);
    }
}