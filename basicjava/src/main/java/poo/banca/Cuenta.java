package poo.banca;

import java.util.Objects;

public class Cuenta {
    private String nombreCliente;
    private String apellidoCliente;
    private String numero;
    private long saldo;

    public Cuenta(
            String nombreCliente,
            String apellidoCliente,
            String numero,
            long saldo
    ) {
        this.validarValoresContructor(nombreCliente, apellidoCliente, numero, saldo);
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.numero = numero;
        this.saldo = saldo;
    }

    private void validarValoresContructor(
            String nombreCliente,
            String apellidoCliente,
            String numero,
            long saldo
    ) throws ConstruccionCuentaException, ValorInvalidoException {
        if (saldo < 0) {
            throw new ValorInvalidoException("El saldo es negativo");
        }

        if (Objects.isNull(nombreCliente) || nombreCliente.isBlank()) {
            throw new ConstruccionCuentaException("Nombre cliente es requerido");
        }

        if (Objects.isNull(apellidoCliente) || apellidoCliente.isBlank()) {
            throw new ConstruccionCuentaException("Apellido cliente es requerido");
        }

        if (Objects.isNull(numero) || numero.isBlank()) {
            throw new ConstruccionCuentaException("Apellido cliente es requerido");
        }
    }

    public void depositar(long valor) {
        if (valor < 0) {
            throw new ValorInvalidoException("El valor a depositar es negativo");
        }
        this.saldo += valor; //this.saldo = this.saldo + valor;
    }

    public void retirar(long valor) {
        if (valor > this.saldo) {
            throw new ValorInvalidoException("El valor a retirar es mayor al saldo de tu cuenta");
        }

        if (valor < 0) {
            throw new ValorInvalidoException("El valor a retirar es negativo");
        }
        this.saldo -= valor;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public String getNumero() {
        return numero;
    }

    public long getSaldo() {
        return saldo;
    }
}
