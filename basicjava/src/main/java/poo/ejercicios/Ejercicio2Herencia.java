package poo.ejercicios;

/*
 * Ejercicio 2 - Herencia
 *
 * Crea una clase abstracta Empleado con: nombre, salarioBase y el método abstracto
 * calcularSalario().
 * - Gerente extiende Empleado y suma un bono fijo al salario base.
 * - Vendedor extiende Empleado y suma una comisión calculada sobre sus ventas.
 * Ambas subclases deben reutilizar el constructor de Empleado con super(...).
 */
public class Ejercicio2Herencia {

    static void main() {
        Empleado gerente = new Gerente("Ana", 2000, 500);
        Empleado vendedor = new Vendedor("Luis", 1200, 8000, 0.05);

        IO.println(gerente.nombre + " gana: " + gerente.calcularSalario());
        IO.println(vendedor.nombre + " gana: " + vendedor.calcularSalario());
    }
}

abstract class Empleado {
    protected String nombre;
    protected double salarioBase;

    public Empleado(String nombre, double salarioBase) {
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public abstract double calcularSalario();
}

class Gerente extends Empleado {
    private double bono;

    public Gerente(String nombre, double salarioBase, double bono) {
        super(nombre, salarioBase);
        this.bono = bono;
    }

    @Override
    public double calcularSalario() {
        return salarioBase + bono;
    }
}

class Vendedor extends Empleado {
    private double ventas;
    private double porcentajeComision;

    public Vendedor(String nombre, double salarioBase, double ventas, double porcentajeComision) {
        super(nombre, salarioBase);
        this.ventas = ventas;
        this.porcentajeComision = porcentajeComision;
    }

    @Override
    public double calcularSalario() {
        return salarioBase + (ventas * porcentajeComision);
    }
}
