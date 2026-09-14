package poo.ejercicios;

/*
 * Ejercicio 4 - Interfaces y Abstracción
 *
 * Define la interfaz Sonora con el método hacerSonido() y la interfaz Movil
 * con el método moverse(). Una clase puede implementar una o varias interfaces:
 * - Perro y Gato implementan Sonora.
 * - Pajaro implementa Sonora y Movil (vuela).
 * El ejercicio muestra cómo distintas clases, sin relación de herencia entre sí,
 * pueden compartir un mismo "contrato" (abstracción de comportamiento).
 */
public class Ejercicio4Interfaces {

    static void main() {
        Sonora[] animales = { new Perro(), new Gato(), new Pajaro() };

        for (Sonora animal : animales) {
            IO.println(animal.hacerSonido());
            if (animal instanceof Movil movil) {
                IO.println(" -> " + movil.moverse());
            }
        }
    }
}

interface Sonora {
    String hacerSonido();
}

interface Movil {
    String moverse();
}

class Perro implements Sonora {
    @Override
    public String hacerSonido() {
        return "El perro dice: Guau";
    }
}

class Gato implements Sonora {
    @Override
    public String hacerSonido() {
        return "El gato dice: Miau";
    }
}

class Pajaro implements Sonora, Movil {
    @Override
    public String hacerSonido() {
        return "El pájaro dice: Pío";
    }

    @Override
    public String moverse() {
        return "El pájaro vuela";
    }
}
