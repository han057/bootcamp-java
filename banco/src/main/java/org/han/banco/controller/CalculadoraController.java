package org.han.banco.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculadoraController {

    @PostMapping("/suma")
    public int sumar() {
       return Calculadora.suma(50, 20);
    }
}
