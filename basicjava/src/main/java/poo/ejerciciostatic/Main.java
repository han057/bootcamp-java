package poo.ejerciciostatic;

public class Main{
    static void main() {
        var p1 = new Persona(
                "Fredy",
                "Sanchez"
        );

        var p2 = new Persona(
          "Flor",
          "Yakuts"
        );

        Persona.reportarPersonas();
        IO.println(p2.presentarse());

        var programador = new Programador(
                "Alo",
                "Hawai"
        );
        IO.println(programador.presentarse());

        programador.presentarse("Hola");
        IO.println(programador.presentarse("Buenos dias", "Salto de fé"));
        IO.println( programador.presentarse("Buenas noches", 2));
    }
}
