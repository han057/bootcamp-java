package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AreaRectangulo {

    static void main() {
        IO.println("Hola, vamos a calcular el area de un rectángulo");
        try {
            Scanner scanner = new Scanner(System.in);
            IO.print("Ingresa el largo del rectángulo: ");
            double largo = scanner.nextDouble();

            IO.print("Ingresa el ancho del rectángulo: ");
            double ancho = scanner.nextDouble();

            double area = ancho * largo;
            IO.println("El area es: " + area);
        } catch (InputMismatchException e) {
            IO.println("Debes ingresar valores numéricos");
        }
    }
}
