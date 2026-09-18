package org.han.banco.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

@RestController
public class SaludoController {

    @GetMapping("/saludar/{id}/usuario")
    public String saludar(
            @RequestParam( name = "primerNombre", required = true)  String primerNombre,
            @RequestParam( name = "segundoNombre", required = false, defaultValue = "")  String segundoNombre,
            @RequestParam( name = "primerApellido", required = true)  String primerApellido,
            @RequestParam( name = "segundoApellido", required = false, defaultValue = "")  String segundoApellido,
            @PathVariable("id") int identificacion
    ) {
        //Genera el mismo return usando String.format()
        return String.format(
                "¡Holis! usuario [%d] %s %s %s %s",
                identificacion,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido
        );

        /*return "¡Holis! usuario [" + identificacion + "] "
                + primerNombre + " "
                + segundoNombre + " "
                + primerApellido + " "
                + segundoApellido;*/
    }

    @PostMapping("/saludar/{id}/usuario")
    public String ejemploPost(
            @RequestParam( name = "primerNombre", required = true)  String primerNombre,
            @RequestParam( name = "segundoNombre", required = false, defaultValue = "")  String segundoNombre,
            @RequestParam( name = "primerApellido", required = true)  String primerApellido,
            @RequestParam( name = "segundoApellido", required = false, defaultValue = "")  String segundoApellido,
            @PathVariable("id") int identificacion
    ) {
        return String.format(
                "¡Holis! usuario [%d] %s %s %s %s",
                identificacion,
                primerNombre,
                segundoNombre,
                primerApellido,
                segundoApellido
        );
    }
}
