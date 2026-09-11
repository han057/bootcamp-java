package org.example;

public class SumaDePares {

    static void main() {
        // i % 2 == 0
        int suma = 0;
        for (int i = 1; i <= 100; i++) {
            if (esPar(i)) {
                suma += i;
            }
        }

        IO.println("La suma de los numeros pares del 1 al 100 es: " + suma);
    }

    static boolean esPar(int numero) {
        return numero % 2 == 0;
    }
}
