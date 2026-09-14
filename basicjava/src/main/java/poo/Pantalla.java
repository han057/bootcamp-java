package poo;

import java.util.Locale;
import java.util.Objects;

public class Pantalla {
    protected String marca;
    private int codigo;
    public Pantalla(String marca) {
        if (Objects.isNull(marca) || marca.isBlank()) {
            throw new DatosInvalidosException();
        }
        this.marca = marca;
    }
    public void renderizar() {
        IO.println(marca.toUpperCase() + ": Renderizando frames");
    }
}
