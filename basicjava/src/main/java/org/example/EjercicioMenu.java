package org.example;

import java.util.Scanner;

public class EjercicioMenu {

    static void main() {
        String opcion = "";
        Scanner scanner = new Scanner(System.in);
        while (!opcion.equals("e")) {
            IO.println("""
                    a. Es par
                    b. Sumar
                    c. Clasificador de edad
                    d. Temperatura Cº/Fº
                    e. Salir
                    """);
            IO.print("Ingrese opcion: ");
            opcion = scanner.nextLine();
            switch (opcion) {
                case "a":
                    IO.print("Ingrese número: ");
                    int numero = scanner.nextInt();
                    scanner.nextLine();
                    IO.println("Es par: " + (numero % 2 == 0));
                    break;
                case "b": break;
                case "c": break;
                case "d": break;
                case "e": break;
                default: IO.println("Opcion inválida");
            }
        }
    }
}
