package org.han.banco;

import org.han.banco.controller.Cliente;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class StreamExample {

    static void main() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            int edad = new Random().nextInt(25);
            clientes.add(new Cliente(i, "Nombre" + i, "correo" + i, "8384" + i, edad));
        }

        /*ArrayList<Cliente> clientesMenoresDeEdad = new ArrayList<>();

        for (int i = 0; i < clientes.size(); i++) {
            if (esMenorEdad(clientes.get(i).getEdad())) {
                clientesMenoresDeEdad.add(clientes.get(i));
            }
        }

        for (Cliente c: clientesMenoresDeEdad) {
            IO.println(c.toString());
        }*/
        var clientesMenores = clientes.stream().filter((c) -> c.getEdad() < 18);
    }

    public static boolean esMenorEdad(int edad) {
        return edad < 18;
    }

    //Function<Cliente, Boolean>  doblarNumero = (i) -> i * 2;
}
