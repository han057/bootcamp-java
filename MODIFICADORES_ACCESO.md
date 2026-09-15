# Modificadores de Acceso, Static y Final en Java

## Tabla de Contenidos
1. [Modificadores de Acceso](#modificadores-de-acceso)
2. [Modificador Static](#modificador-static)
3. [Modificador Final](#modificador-final)
4. [Tabla Comparativa](#tabla-comparativa)
5. [Diagrama de Decisión](#diagrama-de-decisión)
6. [Ejemplos Prácticos](#ejemplos-prácticos)

---

## Modificadores de Acceso

Los modificadores de acceso en Java controlan la visibilidad y accesibilidad de clases, métodos y atributos desde otros paquetes y clases.

### 1. **PUBLIC** (Público)

El miembro es accesible desde **cualquier lugar** del programa.

```java
public class Usuario {
    public String nombre;  // Accesible desde cualquier clase
    
    public void saludar() {
        System.out.println("Hola, soy " + nombre);
    }
}

// En otra clase, diferente paquete:
Usuario usuario = new Usuario();
usuario.nombre = "Carlos";  // ✅ Permitido
usuario.saludar();           // ✅ Permitido
```

**Cuándo usarlo:**
- Métodos que forman parte de la interfaz pública de la clase
- Métodos que otros módulos necesitan usar
- Getters y setters públicos

**Riesgo:** Expone los detalles internos de la clase

---

### 2. **PRIVATE** (Privado)

El miembro es accesible **SOLO dentro de la misma clase**. No es visible ni en subclases.

```java
public class CuentaBancaria {
    private String numeroCuenta;      // Solo accesible en esta clase
    private double saldo;             // Solo accesible en esta clase
    
    public CuentaBancaria(String numero, double saldoInicial) {
        this.numeroCuenta = numero;   // ✅ Permitido (dentro de la clase)
        this.saldo = saldoInicial;
    }
    
    private void validarSaldo() {     // Método privado
        if (saldo < 0) {
            System.out.println("Saldo negativo");
        }
    }
    
    public void depositar(double monto) {
        saldo += monto;
        validarSaldo();               // ✅ Permitido
    }
}

// En otra clase:
CuentaBancaria cuenta = new CuentaBancaria("123456", 1000);
// cuenta.saldo = 500;               // ❌ Error de compilación
// cuenta.validarSaldo();            // ❌ Error de compilación
cuenta.depositar(200);               // ✅ Permitido
```

**Cuándo usarlo:**
- Atributos internos que no deben modificarse directamente
- Métodos auxiliares internos
- Datos sensibles que requieren validación
- Encapsulamiento y ocultación de datos

**Ventaja:** Máxima protección y control

---

### 3. **PROTECTED** (Protegido)

El miembro es accesible **dentro del mismo paquete y en subclases** (incluso en otro paquete).

```java
public class Animal {
    protected String nombre;          // Accesible en subclases
    
    protected void hacerSonido() {    // Accesible en subclases
        System.out.println("Sonido genérico");
    }
}

public class Perro extends Animal {
    public void ladrar() {
        nombre = "Rex";               // ✅ Permitido (es subclase)
        hacerSonido();                // ✅ Permitido
        System.out.println(nombre + " ladra");
    }
}

// En otra clase del mismo paquete:
Animal animal = new Animal();
animal.nombre = "Gato";               // ✅ Permitido (mismo paquete)

// En un paquete diferente:
// animal.nombre = "Pajaro";         // ❌ Error - no es subclase
```

**Cuándo usarlo:**
- Atributos que las subclases necesitan acceder
- Métodos que pueden ser sobrescritos en subclases
- Cuando quieres permitir extensibilidad controlada

**Caso de uso:** Herencia de clases

---

### 4. **DEFAULT** (Sin modificador / Package-private)

El miembro es accesible **solo dentro del mismo paquete**. No se coloca ninguna palabra clave.

```java
// En el paquete com.banco
public class Cliente {
    String numeroDocumento;           // Default - solo visible en el paquete
    
    void procesarSolicitud() {        // Default - solo visible en el paquete
        // ...
    }
}

// En otra clase del mismo paquete:
Cliente cliente = new Cliente();
cliente.numeroDocumento = "12345678"; // ✅ Permitido

// En un paquete diferente (com.otro):
// Cliente cliente = new Cliente();
// cliente.numeroDocumento = "12345"; // ❌ Error - diferente paquete
```

**Cuándo usarlo:**
- Clases y métodos auxiliares dentro de un paquete
- Cuando quieres que algo sea visible solo a nivel de paquete
- Evitar exposición accidental a otros paquetes

---

## Modificador Static

El modificador `static` hace que un atributo o método **pertenezca a la clase, no a las instancias**.

### Atributos Static

Un atributo static es **compartido por todas las instancias** de la clase.

```java
public class Contador {
    private static int contador = 0;  // Compartido por todas las instancias
    private String nombre;
    
    public Contador(String nombre) {
        this.nombre = nombre;
        contador++;                   // Incrementa para todas las instancias
    }
    
    public static int obtenerContador() {
        return contador;
    }
}

// Uso:
Contador c1 = new Contador("Objeto 1");
Contador c2 = new Contador("Objeto 2");
Contador c3 = new Contador("Objeto 3");

System.out.println(Contador.obtenerContador()); // Imprime: 3
```

### Métodos Static

Los métodos static **no necesitan una instancia** para ser llamados.

```java
public class Matematica {
    // Método static - sin acceso a 'this'
    public static double calcularAreaCirculo(double radio) {
        return Math.PI * radio * radio;
    }
    
    // Método de instancia - accede a 'this'
    private double resultado;
    
    public void guardarArea(double radio) {
        resultado = calcularAreaCirculo(radio);  // ✅ Permitido
    }
}

// Uso:
double area = Matematica.calcularAreaCirculo(5);  // ✅ Sin instancia
System.out.println(area);  // Imprime: 78.54

Matematica math = new Matematica();
double area2 = math.calcularAreaCirculo(3);       // ✅ También funciona
```

**Limitaciones de métodos static:**
- No pueden acceder a atributos de instancia (`this`)
- No pueden llamar métodos de instancia (sin crear instancia)
- No se pueden sobrescribir, solo ocultar

```java
public class Ejemplo {
    private int valor = 10;  // Atributo de instancia
    
    public static void metodoEstatico() {
        // System.out.println(valor);  // ❌ Error - no puede acceder a 'this'
        // this.valor = 20;             // ❌ Error - 'this' no existe
    }
}
```

**Cuándo usar static:**
- Métodos utilitarios que no dependen del estado
- Constantes de la clase
- Variables compartidas entre todas las instancias
- Métodos que sirven como factory o helpers

**Ejemplo de constantes static:**

```java
public class Configuracion {
    public static final int PUERTO_DEFECTO = 8080;
    public static final String URL_API = "https://api.ejemplo.com";
    public static final int TIMEOUT_SEGUNDOS = 30;
    
    // No instanciar esta clase
    private Configuracion() {}
}

// Uso:
System.out.println(Configuracion.PUERTO_DEFECTO);  // 8080
```

---

## Modificador Final

El modificador `final` **previene que algo sea modificado o sobrescrito**.

### Final en Atributos

Un atributo final **no puede ser modificado** después de su inicialización.

```java
public class Producto {
    private final String codigo;      // Debe inicializarse en constructor
    private final double precioBase;
    
    public Producto(String codigo, double precioBase) {
        this.codigo = codigo;         // ✅ Inicialización
        this.precioBase = precioBase;
        
        // this.codigo = "OTRO";       // ❌ Error - ya fue inicializado
    }
}

// Uso:
Producto p = new Producto("P001", 99.99);
// p.codigo = "P002";                 // ❌ Error de compilación
```

### Final en Métodos

Un método final **no puede ser sobrescrito** en subclases.

```java
public class Padre {
    public final void metodoImportante() {
        System.out.println("Este método no puede ser cambiado");
    }
    
    public void metodoPuedeCambiar() {
        System.out.println("Este sí puede ser sobrescrito");
    }
}

public class Hijo extends Padre {
    // public void metodoImportante() {  // ❌ Error - está marcado como final
    //     System.out.println("Intento fallido");
    // }
    
    @Override
    public void metodoPuedeCambiar() {  // ✅ Permitido
        System.out.println("Versión del hijo");
    }
}
```

### Final en Clases

Una clase final **no puede ser extendida**.

```java
public final class ClaseFinal {
    public void miMetodo() {}
}

// public class SubClase extends ClaseFinal {  // ❌ Error - ClaseFinal es final
//     // ...
// }
```

**Ejemplo:** Las clases `String`, `Integer`, `Math` son final en Java.

**Cuándo usar final:**
- Para constantes (junto con static)
- Para proteger métodos críticos de ser sobrescritos
- Para clases que son utilidades o no deben ser extendidas
- Para mejorar el rendimiento (permite optimizaciones del compilador)

```java
public final class Utilidades {
    public static final int MAXIMO_REINTENTOS = 3;
    public static final String VERSION = "1.0.0";
    
    private Utilidades() {}  // Evitar instanciación
    
    public static String formatearPorcentaje(double valor) {
        return String.format("%.2f%%", valor * 100);
    }
}
```

---

## Tabla Comparativa

| Modificador | Misma Clase | Mismo Paquete | Subclase | Cualquier Lugar |
|-------------|:-----------:|:-------------:|:--------:|:---------------:|
| **public** | ✅ | ✅ | ✅ | ✅ |
| **protected** | ✅ | ✅ | ✅ | ❌ |
| **default** | ✅ | ✅ | ❌ | ❌ |
| **private** | ✅ | ❌ | ❌ | ❌ |

### Tabla Static y Final

| Modificador | Atributos | Métodos | Clases | Efecto |
|-------------|:---------:|:-------:|:------:|--------|
| **static** | Compartido entre instancias | Accesible sin instancia | No aplica | Pertenece a la clase |
| **final** | No modificable | No sobrescribible | No extensible | Inmutable/Bloqueado |
| **static final** | Constante de clase | - | - | Valor fijo compartido |

---

## Diagrama de Decisión

```
¿Qué visibilidad necesita?

    │
    ├─→ ¿Debe ser accesible SOLO dentro de la clase?
    │   └─→ PRIVATE ✓
    │       Uso: Datos sensibles, métodos auxiliares
    │
    ├─→ ¿Debe ser accesible en subclases?
    │   └─→ PROTECTED ✓
    │       Uso: Métodos para override, datos de herencia
    │
    ├─→ ¿Debe ser accesible en el mismo paquete?
    │   └─→ DEFAULT (sin modificador) ✓
    │       Uso: Clases auxiliares del paquete
    │
    └─→ ¿Debe ser accesible desde cualquier lugar?
        └─→ PUBLIC ✓
            Uso: API pública de la clase

¿Qué tipo de dato o método es?

    │
    ├─→ ¿Es una constante?
    │   └─→ static final ✓
    │       Ejemplo: public static final int PUERTO = 8080;
    │
    ├─→ ¿Es compartido entre instancias?
    │   └─→ static ✓
    │       Ejemplo: private static int contador = 0;
    │
    ├─→ ¿Es un método utilitario independiente?
    │   └─→ static ✓
    │       Ejemplo: public static String formatear(String texto);
    │
    └─→ ¿No debe cambiar después de inicializar?
        └─→ final ✓
            Ejemplo: private final String id;
```

---

## Ejemplos Prácticos

### Ejemplo 1: Clase de Dominio (Entidad)

```java
public class CuentaBancaria {
    
    // Constantes públicas de la clase
    public static final double SALDO_MINIMO = 0.0;
    public static final String BANCO = "Banco SPLAI";
    
    // Atributos privados (no modificables directamente)
    private final String numeroCuenta;      // final - no cambia
    private final String titular;
    private static int totalCuentas = 0;    // static - contador global
    
    private double saldo;                   // private - solo lectura/escritura controlada
    
    // Constructor
    public CuentaBancaria(String numeroCuenta, String titular, double saldoInicial) {
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.saldo = saldoInicial;
        totalCuentas++;
    }
    
    // Getters públicos (solo lectura)
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    public String getTitular() {
        return titular;
    }
    
    public double getSaldo() {
        return saldo;
    }
    
    // Métodos públicos (comportamiento controlado)
    public void depositar(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("Monto debe ser positivo");
        }
        saldo += monto;
        registrarMovimiento("Depósito", monto);
    }
    
    public boolean retirar(double monto) {
        if (monto <= 0 || monto > saldo) {
            return false;
        }
        saldo -= monto;
        registrarMovimiento("Retiro", monto);
        return true;
    }
    
    // Método privado auxiliar
    private void registrarMovimiento(String tipo, double monto) {
        System.out.println("[" + tipo + "] " + numeroCuenta + ": " + monto);
    }
    
    // Método estático para información de clase
    public static int obtenerTotalCuentas() {
        return totalCuentas;
    }
    
    public static String obtenerNombroBanco() {
        return BANCO;
    }
}

// Uso:
CuentaBancaria cuenta = new CuentaBancaria("123456", "Juan Pérez", 5000);
cuenta.depositar(1000);      // ✅ Permitido
cuenta.retirar(500);         // ✅ Permitido
System.out.println(cuenta.getSaldo());  // ✅ 5500

System.out.println(CuentaBancaria.obtenerTotalCuentas());  // ✅ 1
```

### Ejemplo 2: Clase Utilitaria

```java
public final class Validador {
    private Validador() {  // Evitar instanciación
    }
    
    // Métodos estáticos públicos
    public static boolean esEmailValido(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
    
    public static boolean esNumeroValido(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // Constantes privadas
    private static final int MIN_LONGITUD_CONTRASENA = 8;
    private static final String CARACTERES_ESPECIALES = "!@#$%^&*";
    
    public static boolean esContrasenaSegura(String contrasena) {
        return contrasena != null 
            && contrasena.length() >= MIN_LONGITUD_CONTRASENA
            && contrasena.matches(".*[A-Z].*")  // Mayúscula
            && contrasena.matches(".*[a-z].*")  // Minúscula
            && contrasena.matches(".*[0-9].*"); // Número
    }
}

// Uso:
if (Validador.esEmailValido("usuario@ejemplo.com")) {
    System.out.println("Email válido");
}

boolean segura = Validador.esContrasenaSegura("Abc123!@");
```

### Ejemplo 3: Herencia con Protected

```java
public abstract class Empleado {
    protected String nombre;           // Accesible en subclases
    protected double salarioBase;
    private String numeroEmpleado;     // No accesible en subclases
    
    public Empleado(String nombre, double salarioBase) {
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }
    
    // Método protected para subclases
    protected double calcularBonus() {
        return salarioBase * 0.1;  // 10% de bonus
    }
    
    // Método abstracto para override
    public abstract double calcularSalarioTotal();
    
    public String obtenerNombre() {
        return nombre;
    }
}

public class EmpleadoTiempoCompleto extends Empleado {
    private int diasVacaciones;
    
    public EmpleadoTiempoCompleto(String nombre, double salarioBase, int diasVacaciones) {
        super(nombre, salarioBase);
        this.diasVacaciones = diasVacaciones;
    }
    
    @Override
    public double calcularSalarioTotal() {
        return salarioBase + calcularBonus();  // ✅ Usa protected
    }
}

public class EmpleadoPorHora extends Empleado {
    private double tarifaHora;
    private int horasTrabajadas;
    
    public EmpleadoPorHora(String nombre, double tarifaHora) {
        super(nombre, tarifaHora);
        this.tarifaHora = tarifaHora;
    }
    
    @Override
    public double calcularSalarioTotal() {
        return (tarifaHora * horasTrabajadas) + calcularBonus();  // ✅ Usa protected
    }
}
```

---

## Resumen y Mejores Prácticas

### ✅ Hacer
- Usar `private` por defecto para atributos y métodos auxiliares
- Usar `public` solo para lo que forma parte de la API pública
- Usar `protected` para métodos que subclases necesiten sobrescribir
- Usar `static` para utilidades y constantes
- Usar `final` para constantes y proteger métodos críticos
- Encapsular datos con getters y setters
- Comentar por qué algo es `public` o `protected`

### ❌ Evitar
- Usar `public` para atributos (excepto constantes)
- Cambiar atributos sin control
- Exponer detalles internos de implementación
- Crear métodos estáticos cuando deberían ser de instancia
- Usar atributos mutables en constantes

---

**Última actualización:** 2026-09-15
**Para:** Fundación SPLAI Bootcamp Java
