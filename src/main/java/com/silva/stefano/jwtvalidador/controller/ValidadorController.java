package com.silva.stefano.jwtvalidador.controller;

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
        try
        {
            //return Ok(JWTValidation.Valid(jwt));
            return ResponseEntity.ok("verdadeiro");
        }
        catch (Exception except)
        {
            /*Response.Headers.Append("Exception-Message", except.Message);
            Response.Headers.Append("Exception-Type", except.TipoExcecao.ToString());
            */
        }

        return  ResponseEntity.badRequest().body("saldo");
    }
}
