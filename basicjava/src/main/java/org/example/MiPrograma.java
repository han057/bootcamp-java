package org.example;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MiPrograma {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        System.out.print("¿Cuál es tu nombre? ");
        String nombre = entrada.nextLine();

        System.out.print("¿Cuál es tu edad? ");
        try {
            byte edad = entrada.nextByte();
            byte otroValor = (byte) (10 / edad);
            System.out.println("Hola " + nombre + ", tienes " + edad + " años");
        } catch (InputMismatchException | ArithmeticException e) {
            System.out.println("Error al ingresar la edad. Debe ser numérica");
        } catch (RuntimeException e) {

        } finally {
            entrada.close();
        }
    }
}
