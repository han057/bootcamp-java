package poo.banca;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {
    @Test
    void cuentaValoresCorrectos() {
        Cuenta cuenta = new Cuenta(
                "Cliente 1",
                "Sancho",
                "23456789",
                150
        );

        assertEquals("Cliente 1", cuenta.getNombreCliente());
    }

    @Test
    void cuentaSaldoNegativo() {
        assertThrows(ValorInvalidoException.class, () -> {
            Cuenta cuenta = new Cuenta(
                    "Cliente 1",
                    "Sancho",
                    "23456789",
                    -150
            );
        });
    }

    @Test
    void depositar() {
    }

    @Test
    void retirar() {
    }
}