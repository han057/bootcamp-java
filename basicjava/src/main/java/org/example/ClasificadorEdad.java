package org.example;

import java.util.Scanner;

public class ClasificadorEdad {
    static void main() {
        /*
        Menor de 13: "Niño"
        De 13 a 17: "Adolescente"
        De 18 a 65: "Adulto"
        Mayor de 65: "Adulto Mayor"
         */
        IO.print("Ingrese edad en años: ");
        Scanner scanner = new Scanner(System.in);
        float edadAnos = scanner.nextInt();

        if (edadAnos < 0) {
            IO.println("La edad debe ser positiva");
        } else if (edadAnos < 13) {
            IO.println("Niño");
        } else if (edadAnos < 18) {
            IO.println("Adolescente");
        } else if (edadAnos <= 65) {
            IO.println("Adulto");
        } else {
            IO.println("Adulto Mayor");
        }
    }
}

