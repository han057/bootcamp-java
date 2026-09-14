package poo.banca;

public class ConstruccionCuentaException extends RuntimeException {
    public ConstruccionCuentaException(String nombreClienteEsRequerido) {
        super(nombreClienteEsRequerido);
    }
}
