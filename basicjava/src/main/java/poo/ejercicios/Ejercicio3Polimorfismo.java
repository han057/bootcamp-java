package poo.ejercicios;

import java.util.List;

/*
 * Ejercicio 3 - Polimorfismo
 *
 * Crea una clase abstracta Figura con los métodos abstractos calcularArea() y
 * calcularPerimetro(). Implementa Circulo, Rectangulo y Triangulo.
 * Luego recorre una lista de Figura e imprime el área de cada una sin saber
 * de qué tipo concreto se trata: cada objeto resuelve su propio cálculo
 * (polimorfismo en tiempo de ejecución).
 */
public class Ejercicio3Polimorfismo {

    static void main() {
        List<Figura> figuras = List.of(
                new Circulo(3),
                new Rectangulo(4, 5),
                new Triangulo(6, 2.5)
        );

        for (Figura figura : figuras) {
            IO.println(figura.getClass().getSimpleName()
                    + " -> área: " + figura.calcularArea()
                    + ", perímetro: " + figura.calcularPerimetro());
        }
    }
}

abstract class Figura {
    public abstract double calcularArea();
    public abstract double calcularPerimetro();
}

class Circulo extends Figura {
    private double radio;

    public Circulo(double radio) {
        this.radio = radio;
    }

    @Override
    public double calcularArea() {
        return Math.PI * radio * radio;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * Math.PI * radio;
    }
}

class Rectangulo extends Figura {
    private double base;
    private double altura;

    public Rectangulo(double base, double altura) {
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return base * altura;
    }

    @Override
    public double calcularPerimetro() {
        return 2 * (base + altura);
    }
}

class Triangulo extends Figura {
    private double base;
    private double altura;

    public Triangulo(double base, double altura) {
        this.base = base;
        this.altura = altura;
    }

    @Override
    public double calcularArea() {
        return (base * altura) / 2;
    }

    @Override
    public double calcularPerimetro() {
        // Simplificado: se asume triángulo equilátero para el ejercicio
        return base * 3;
    }
}
