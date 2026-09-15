package poo.colecciones;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    static void main() {
        int[] arr = new int[5];
        arr[0] = 10;
        arr[1] = 20;
        IO.println(arr[0]);


        List listaArreglo = new ArrayList();
        List listaEnlazada = new LinkedList();

        listaArreglo.add(20);
        listaArreglo.add(60);
        listaArreglo.add(76);
        listaArreglo.add(34);
        listaArreglo.add(3, 50);
       /* for (int i = 0; i < listaArreglo.size(); i++) {
            IO.println(listaArreglo.get(i));
        }*/
        listaArreglo.stream().forEach(e -> IO.println(e));

        listaEnlazada.add(454);
    }
}
