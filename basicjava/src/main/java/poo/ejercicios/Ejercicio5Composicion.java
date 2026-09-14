package poo.ejercicios;

import java.util.List;

/*
 * Ejercicio 5 - Composición
 *
 * Un Coche "tiene un" Motor y "tiene" cuatro Ruedas (composición: el Coche crea
 * y es dueño de esos objetos; si el Coche deja de existir, sus partes también).
 * - Motor tiene tipo y potencia, y un método encender().
 * - Rueda tiene una marca.
 * - Coche.arrancar() delega en su Motor en lugar de duplicar lógica.
 */
public class Ejercicio5Composicion {

    static void main() {
        Coche coche = new Coche("Toyota", "V6", 300, "Michelin");
        coche.arrancar();
    }
}

class Motor {
    private String tipo;
    private int potenciaHp;

    public Motor(String tipo, int potenciaHp) {
        this.tipo = tipo;
        this.potenciaHp = potenciaHp;
    }

    public String encender() {
        return "Motor " + tipo + " (" + potenciaHp + "hp) encendido";
    }
}

class Rueda {
    private String marca;

    public Rueda(String marca) {
        this.marca = marca;
    }

    public String getMarca() {
        return marca;
    }
}

class Coche {
    private String modelo;
    private final Motor motor;
    private final List<Rueda> ruedas;

    public Coche(String modelo, String tipoMotor, int potenciaHp, String marcaRuedas) {
        this.modelo = modelo;
        this.motor = new Motor(tipoMotor, potenciaHp);
        this.ruedas = List.of(new Rueda(marcaRuedas), new Rueda(marcaRuedas),
                new Rueda(marcaRuedas), new Rueda(marcaRuedas));
    }

    public void arrancar() {
        IO.println(modelo + ": " + motor.encender());
        IO.println("Rodando sobre " + ruedas.size() + " ruedas " + ruedas.get(0).getMarca());
    }
}
