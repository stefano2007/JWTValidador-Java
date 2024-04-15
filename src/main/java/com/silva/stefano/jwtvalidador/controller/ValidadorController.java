package com.silva.stefano.jwtvalidador.controller;

import com.silva.stefano.jwtvalidador.exceptions.BaseException;
import com.silva.stefano.jwtvalidador.validations.JWTValidation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/Validador")
public class ValidadorController {

    private static final Logger logger = Logger.getLogger(String.valueOf(ValidadorController.class));
    @GetMapping
    public ResponseEntity<String > Get(@RequestParam String jwt)
    {
        HttpHeaders responseHeaders = new HttpHeaders();
        try
        {
            //TODO: Swagger(revisar), README e DockerFile
            var result = JWTValidation.Valid(jwt);
            logger.log(Level.INFO, String.format("Token JWT sucess: %s", jwt));
            return ResponseEntity.ok(result);
        }
        catch (BaseException except)
        {
            var exceptionMessage = except.getMessage();
            var typeMessage = except.getTipoExcecao().name();
            logger.log(Level.WARNING,String.format("Token JWT error: %s", jwt));
            logger.log(Level.WARNING,String.format("Exception-Message: %s", exceptionMessage));
            logger.log(Level.WARNING,String.format("Exception-Type: %s", typeMessage));

            responseHeaders.set("Exception-Message", exceptionMessage);
            responseHeaders.set("Exception-Type", typeMessage);
        }

        return  ResponseEntity
                .badRequest()
                .headers(responseHeaders)
                .body("falso");
    }
}
