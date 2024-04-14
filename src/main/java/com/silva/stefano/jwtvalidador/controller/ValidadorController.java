package com.silva.stefano.jwtvalidador.controller;

import com.silva.stefano.jwtvalidador.exceptions.BaseException;
import com.silva.stefano.jwtvalidador.validations.JWTValidation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Validador")
public class ValidadorController {

    @GetMapping
    public ResponseEntity<String > Get(String jwt)
    {
        HttpHeaders responseHeaders = new HttpHeaders();
        try
        {
            //TODO: Adicionar Swagger, README e DockerFile
            return ResponseEntity.ok(JWTValidation.Valid(jwt));
        }
        catch (BaseException except)
        {
            responseHeaders.set("Exception-Message", except.getMessage());
            responseHeaders.set("Exception-Type", except.getTipoExcecao().name());
        }

        return  ResponseEntity
                .badRequest()
                .headers(responseHeaders)
                .body("falso");
    }
}
