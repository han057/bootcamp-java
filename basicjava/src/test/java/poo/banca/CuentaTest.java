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
    void cuentaNombreClienteNull() {
        assertThrows(ConstruccionCuentaException.class, () -> {
            Cuenta cuenta = new Cuenta(
                    null,
                    "Sancho",
                    "23456789",
                    150
            );
        });
    }



    @Test
    void depositar() {
        //Arrange | Dado
        Cuenta cuenta = new Cuenta(
                "Cliente",
                "Apellido",
                "4848484",
                100
        );

        //Act | Cuando
        cuenta.depositar(50);

        //Assert | Entonces
        assertEquals(150, cuenta.getSaldo(), "El saldo del cliente e");
    }

    @Test
    void retirar() {

    }
}