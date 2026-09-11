package org.example;

import java.util.Scanner;

public class ConversorTemperatura {

    static void main() {
        IO.print("Ingrese temperatura en celsius: ");
        Scanner scanner = new Scanner(System.in);
        float temperaturaCelsius = scanner.nextFloat();
        float temperaturaFarenheit = (temperaturaCelsius * 9 / 5) + 32;
        IO.println(temperaturaCelsius + " celsius es  "
                + temperaturaFarenheit + " farenheit");
    }
}
