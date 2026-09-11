package org.example;

import java.util.Scanner;

public class TablaMultiplicar {
    static void main() {
        IO.println("Ingrese número:");
        Scanner scanner = new Scanner(System.in);
        int numero = scanner.nextInt();

        IO.println("Ingrese número máximo a multiplicar:");
        int tope = scanner.nextInt();
        for (int i = 1; i <= tope; i++) {
            IO.println(numero + " * " + i + " = " + numero * i);
        }
    }
}
