package org.example;

import ataquetitanes.externo.Annie;
import ataquetitanes.interno.Eren;
import ataquetitanes.interno.Erwin;

// [método accesor](class|interface|enum) (nombre) [implements|extends] {}
public class Main {
    //[método accesor] [final] [static] (tipo dato retorno) (nombre)([tipo d1, tipo d2..])
    public static void main(String[] args) {
        Eren eren = new Eren();
        Erwin erwin = new Erwin();
        Annie annie = new Annie();
        IO.println("Información de Erwin:");
        IO.println(erwin.dimeTodoloQueSabes());
        IO.println("Información de Annie:");
        IO.println(annie.dimeTodoloQueSabes());
        IO.println("Información de Eren:");
        IO.println(eren.dimeTodoloQueSabes());
    }
}
