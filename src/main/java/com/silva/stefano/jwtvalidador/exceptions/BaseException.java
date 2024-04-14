package com.silva.stefano.jwtvalidador.exceptions;

import  com.silva.stefano.jwtvalidador.enumerators.TipoExcecao;

public abstract class BaseException extends Exception {
    private final TipoExcecao TipoExcecao;

    protected BaseException(String error, TipoExcecao tipoExcecao) {
        super(error);
        TipoExcecao = tipoExcecao;
    }
    public TipoExcecao getTipoExcecao() {
        return TipoExcecao;
    }
}
