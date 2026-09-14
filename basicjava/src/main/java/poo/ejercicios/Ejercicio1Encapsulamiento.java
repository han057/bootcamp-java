package poo.ejercicios;

/*
 * Ejercicio 1 - Encapsulamiento
 *
 * Crea una clase CuentaBancaria con los atributos privados: titular, saldo.
 * - El saldo nunca debe poder modificarse directamente desde fuera de la clase.
 * - depositar(monto): suma el monto al saldo. Si el monto es negativo o cero, no hace nada
 *   e imprime un mensaje de error.
 * - retirar(monto): resta el monto al saldo solo si hay saldo suficiente. Si no lo hay,
 *   imprime un mensaje de error y no modifica el saldo.
 * - getSaldo(): devuelve el saldo actual (no debe existir un setSaldo público).
 */
public class Ejercicio1Encapsulamiento {

    static void main() {
        CuentaBancaria cuenta = new CuentaBancaria("Fredy");

        cuenta.depositar(1000);
        cuenta.retirar(300);
        cuenta.retirar(5000); // fondos insuficientes
        cuenta.depositar(-50); // monto inválido

        IO.println(cuenta.getTitular() + " tiene un saldo de: " + cuenta.getSaldo());
    }
}

class CuentaBancaria {
    private final String titular;
    private double saldo;

    public CuentaBancaria(String titular) {
        this.titular = titular;
        this.saldo = 0;
    }

    public void depositar(double monto) {
        if (monto <= 0) {
            IO.println("El monto a depositar debe ser positivo");
            return;
        }
        saldo += monto;
    }

    public void retirar(double monto) {
        if (monto > saldo) {
            IO.println("Fondos insuficientes para retirar " + monto);
            return;
        }
        saldo -= monto;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getTitular() {
        return titular;
    }
}
