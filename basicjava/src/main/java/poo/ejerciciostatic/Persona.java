package poo.ejerciciostatic;

public class Persona {
    final static float PI = 3.1415f;
    private static int contador = 0;
    final protected String nombre;
    final protected String apellido;

    public Persona(
            String nombre,
            String apellido
    ) {
        this.nombre = nombre;
        this.apellido = apellido;
        contador++;
    }

    public String presentarse() {
        return "Hola soy " + nombre;
    }

    public void presentarse(String saludo) {
        IO.println(saludo);
    }

    public String presentarse(String saludo, String despedida) {
        return saludo + presentarse() + despedida;
    }
    public String presentarse(String saludo, int despedida) {
        return saludo + presentarse() + despedida + " veces";
    }

    public static void reportarPersonas() {

        IO.println("Cantidad de personas instanciada: " + contador);
    }
}

class Programador extends Persona {

    public Programador(String nombre, String apellido) {
        super(nombre, apellido);
    }

    @Override
    public String presentarse() {
        return super.presentarse()
                + ", un programador";
    }
}

class ProgramadorJava extends Programador {

    public ProgramadorJava(String nombre, String apellido) {
        super(nombre, apellido);
    }
}

