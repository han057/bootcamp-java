# Fundamentos de Java

## 1. Variables

Una variable es un espacio de memoria que almacena un valor. En Java, toda variable debe tener un **tipo de dato** declarado.

### Declaración de Variables

La sintaxis básica para declarar una variable es:

```java
tipoDedato nombreVariable;
```

### Inicialización de Variables

Puedes inicializar una variable al momento de declararla:

```java
tipoDedato nombreVariable = valor;
```

### Ejemplo

```java
int edad = 25;
String nombre = "Juan";
double altura = 1.75;
boolean esEstudiante = true;
```

### Reglas para Nombres de Variables

- Deben empezar con una letra, guión bajo `_` o símbolo de dólar `$`
- No pueden empezar con un número
- Son sensibles a mayúsculas y minúsculas (`edad` es diferente de `Edad`)
- Se recomienda usar **camelCase**: `miVariable`, `nombreCompleto`
- No pueden ser palabras reservadas de Java (`int`, `class`, `if`, etc.)

---

## 2. Tipos de Datos

Java es un lenguaje **fuertemente tipado**, lo que significa que cada variable debe tener un tipo específico.

### Tipos Primitivos

Los tipos primitivos son los más básicos en Java. Hay 8 tipos primitivos:

#### Tipos Numéricos Enteros

| Tipo   | Tamaño    | Rango                                  | Ejemplo     |
|--------|-----------|----------------------------------------|-------------|
| `byte` | 1 byte    | -128 a 127                            | `byte b = 10;` |
| `short`| 2 bytes   | -32,768 a 32,767                     | `short s = 1000;` |
| `int`  | 4 bytes   | -2,147,483,648 a 2,147,483,647       | `int edad = 25;` |
| `long` | 8 bytes   | -9,223,372,036,854,775,808 a ... | `long poblacion = 1000000L;` |

**Nota:** Para `long`, se debe agregar la letra `L` al final del número.

#### Tipos Numéricos Decimales

| Tipo     | Tamaño   | Precisión | Ejemplo        |
|----------|----------|-----------|----------------|
| `float`  | 4 bytes  | ~6-7 dígitos | `float pi = 3.14f;` |
| `double` | 8 bytes  | ~15-16 dígitos | `double altura = 1.75;` |

**Nota:** Para `float`, se debe agregar la letra `f` al final del número.

#### Tipo Booleano

| Tipo      | Valores | Ejemplo               |
|-----------|---------|----------------------|
| `boolean` | `true` o `false` | `boolean esActivo = true;` |

#### Tipo Carácter

| Tipo  | Almacena | Ejemplo          |
|-------|----------|------------------|
| `char` | Un carácter Unicode | `char letra = 'A';` |

### Tipos de Referencia

Además de los tipos primitivos, existen tipos de referencia. El más común es:

#### String (Cadena de Caracteres)

```java
String mensaje = "Hola, mundo";
String nombre = "Ana";
```

Las strings en Java son objetos que representan secuencias de caracteres.

### Conversión de Tipos (Casting)

#### Conversión Implícita (Ampliación)

Cuando convertimos un tipo más pequeño a uno más grande, Java lo hace automáticamente:

```java
int numero = 42;
double decimal = numero;  // Conversión automática
System.out.println(decimal);  // Imprime: 42.0
```

#### Conversión Explícita (Estrecha)

Cuando convertimos un tipo más grande a uno más pequeño, debemos hacerlo explícitamente:

```java
double decimal = 42.7;
int numero = (int) decimal;  // Conversión explícita
System.out.println(numero);  // Imprime: 42 (se pierde la parte decimal)
```

---

## 3. Operadores

### Operadores Aritméticos

| Operador | Nombre        | Ejemplo      | Resultado |
|----------|---------------|--------------|-----------|
| `+`      | Suma          | `5 + 3`      | `8`       |
| `-`      | Resta         | `5 - 3`      | `2`       |
| `*`      | Multiplicación| `5 * 3`      | `15`      |
| `/`      | División      | `6 / 3`      | `2`       |
| `%`      | Módulo (residuo) | `7 % 3`    | `1`       |

```java
int a = 10;
int b = 3;
System.out.println(a + b);   // 13
System.out.println(a - b);   // 7
System.out.println(a * b);   // 30
System.out.println(a / b);   // 3
System.out.println(a % b);   // 1
```

### Operadores de Asignación

| Operador | Ejemplo  | Equivalente |
|----------|----------|-------------|
| `=`      | `x = 5`  | Asigna 5 a x |
| `+=`     | `x += 3` | `x = x + 3` |
| `-=`     | `x -= 3` | `x = x - 3` |
| `*=`     | `x *= 3` | `x = x * 3` |
| `/=`     | `x /= 3` | `x = x / 3` |
| `%=`     | `x %= 3` | `x = x % 3` |

```java
int numero = 10;
numero += 5;  // numero ahora es 15
numero -= 3;  // numero ahora es 12
numero *= 2;  // numero ahora es 24
```

### Operadores de Comparación

Devuelven un valor `boolean` (`true` o `false`):

| Operador | Nombre | Ejemplo | Resultado |
|----------|--------|---------|-----------|
| `==`     | Igual a | `5 == 5` | `true` |
| `!=`     | No igual a | `5 != 3` | `true` |
| `>`      | Mayor que | `5 > 3` | `true` |
| `<`      | Menor que | `5 < 3` | `false` |
| `>=`     | Mayor o igual a | `5 >= 5` | `true` |
| `<=`     | Menor o igual a | `5 <= 3` | `false` |

```java
int edad = 25;
System.out.println(edad > 18);   // true
System.out.println(edad == 25);  // true
System.out.println(edad < 30);   // true
```

### Operadores Lógicos

| Operador | Nombre | Descripción | Ejemplo |
|----------|--------|-------------|---------|
| `&&`     | AND (Y) | Ambas condiciones son verdaderas | `(5 > 3) && (8 > 6)` = `true` |
| `\|\|`   | OR (O) | Al menos una condición es verdadera | `(5 < 3) \|\| (8 > 6)` = `true` |
| `!`      | NOT (NO) | Invierte el valor booleano | `!(5 < 3)` = `true` |

```java
int edad = 25;
boolean esEstudiante = true;

if (edad > 18 && esEstudiante) {
    System.out.println("Eres un adulto estudiante");
}

if (edad < 18 || esEstudiante) {
    System.out.println("Eres menor de edad o estudiante");
}

if (!esEstudiante) {
    System.out.println("No eres estudiante");
}
```

### Operadores de Incremento y Decremento

| Operador | Nombre | Ejemplo | Equivalente |
|----------|--------|---------|-------------|
| `++`     | Incremento | `x++` | `x = x + 1` |
| `--`     | Decremento | `x--` | `x = x - 1` |

```java
int contador = 5;
contador++;  // contador ahora es 6
contador--;  // contador ahora es 5
```

---

## 4. Estructuras de Control de Flujo

Las estructuras de control permiten controlar el flujo de ejecución del programa.

### 4.1 Sentencia IF

Ejecuta un bloque de código si una condición es verdadera.

#### Sintaxis

```java
if (condición) {
    // Código a ejecutar si la condición es verdadera
}
```

#### Ejemplo

```java
int edad = 20;

if (edad >= 18) {
    System.out.println("Eres mayor de edad");
}
```

### 4.2 Sentencia IF-ELSE

Ejecuta un bloque si la condición es verdadera, y otro bloque si es falsa.

#### Sintaxis

```java
if (condición) {
    // Código si la condición es verdadera
} else {
    // Código si la condición es falsa
}
```

#### Ejemplo

```java
int edad = 15;

if (edad >= 18) {
    System.out.println("Eres mayor de edad");
} else {
    System.out.println("Eres menor de edad");
}
```

### 4.3 Sentencia IF-ELSE IF-ELSE

Permite evaluar múltiples condiciones.

#### Sintaxis

```java
if (condición1) {
    // Código si condición1 es verdadera
} else if (condición2) {
    // Código si condición2 es verdadera
} else if (condición3) {
    // Código si condición3 es verdadera
} else {
    // Código si ninguna condición es verdadera
}
```

#### Ejemplo

```java
int calificacion = 85;

if (calificacion >= 90) {
    System.out.println("Calificación: A");
} else if (calificacion >= 80) {
    System.out.println("Calificación: B");
} else if (calificacion >= 70) {
    System.out.println("Calificación: C");
} else {
    System.out.println("Calificación: F");
}
```

### 4.4 Sentencia SWITCH

Selecciona una de varias opciones según el valor de una expresión.

#### Sintaxis

```java
switch (expresión) {
    case valor1:
        // Código si expresión == valor1
        break;
    case valor2:
        // Código si expresión == valor2
        break;
    default:
        // Código si no coincide con ningún caso
}
```

#### Ejemplo

```java
int dia = 3;

switch (dia) {
    case 1:
        System.out.println("Lunes");
        break;
    case 2:
        System.out.println("Martes");
        break;
    case 3:
        System.out.println("Miércoles");
        break;
    default:
        System.out.println("Día inválido");
}
```

**Nota:** El `break` es importante para salir del switch. Sin él, se ejecutará el siguiente caso.

### 4.5 Bucle FOR

Repite un bloque de código un número específico de veces.

#### Sintaxis

```java
for (inicialización; condición; incremento) {
    // Código a repetir
}
```

#### Ejemplo

```java
for (int i = 1; i <= 5; i++) {
    System.out.println("Número: " + i);
}
// Imprime: Número: 1, Número: 2, Número: 3, Número: 4, Número: 5
```

#### Desglose

- **Inicialización**: `int i = 1` - Se ejecuta una sola vez al inicio
- **Condición**: `i <= 5` - Se evalúa antes de cada iteración
- **Incremento**: `i++` - Se ejecuta después de cada iteración

### 4.6 Bucle WHILE

Repite un bloque de código mientras una condición sea verdadera.

#### Sintaxis

```java
while (condición) {
    // Código a repetir
}
```

#### Ejemplo

```java
int contador = 1;

while (contador <= 5) {
    System.out.println("Contador: " + contador);
    contador++;
}
```

### 4.7 Bucle DO-WHILE

Similar a `while`, pero el bloque se ejecuta al menos una vez.

#### Sintaxis

```java
do {
    // Código a repetir
} while (condición);
```

#### Ejemplo

```java
int numero = 1;

do {
    System.out.println("Número: " + numero);
    numero++;
} while (numero <= 3);
```

### 4.8 Sentencias BREAK y CONTINUE

#### BREAK

Sale del bucle o switch inmediatamente.

```java
for (int i = 1; i <= 10; i++) {
    if (i == 5) {
        break;  // Sale del bucle cuando i == 5
    }
    System.out.println(i);
}
// Imprime: 1, 2, 3, 4
```

#### CONTINUE

Salta a la siguiente iteración del bucle.

```java
for (int i = 1; i <= 5; i++) {
    if (i == 3) {
        continue;  // Salta cuando i == 3
    }
    System.out.println(i);
}
// Imprime: 1, 2, 4, 5
```

---

## 5. Métodos

Un método es un bloque de código reutilizable que realiza una tarea específica.

### Definición de Métodos

#### Sintaxis

```java
tipoDeRetorno nombreMetodo(parámetros) {
    // Cuerpo del método
    return valor;  // Si el tipo de retorno no es void
}
```

#### Componentes

- **Tipo de Retorno**: El tipo de dato que devuelve el método (o `void` si no devuelve nada)
- **Nombre**: Identificador del método (en camelCase)
- **Parámetros**: Variables que recibe el método (opcionales)
- **Cuerpo**: El código del método
- **Return**: Devuelve un valor (solo si el tipo de retorno no es void)

### 5.1 Métodos sin Parámetros y sin Retorno

```java
public static void saludar() {
    System.out.println("¡Hola, mundo!");
}

// Llamada al método
saludar();
```

### 5.2 Métodos con Parámetros y sin Retorno

```java
public static void saludar(String nombre) {
    System.out.println("¡Hola, " + nombre + "!");
}

// Llamada al método
saludar("Juan");  // Imprime: ¡Hola, Juan!
```

#### Ejemplo con Múltiples Parámetros

```java
public static void sumar(int a, int b) {
    int resultado = a + b;
    System.out.println("La suma es: " + resultado);
}

// Llamada al método
sumar(5, 3);  // Imprime: La suma es: 8
```

### 5.3 Métodos sin Parámetros y con Retorno

```java
public static int obtenerEdad() {
    return 25;
}

// Llamada al método
int edad = obtenerEdad();
System.out.println("Edad: " + edad);  // Imprime: Edad: 25
```

### 5.4 Métodos con Parámetros y con Retorno

```java
public static int multiplicar(int a, int b) {
    return a * b;
}

// Llamada al método
int resultado = multiplicar(4, 5);
System.out.println("Resultado: " + resultado);  // Imprime: Resultado: 20
```

### 5.5 Métodos con Retorno de String

```java
public static String obtenerNombreCompleto(String nombre, String apellido) {
    return nombre + " " + apellido;
}

// Llamada al método
String nombreCompleto = obtenerNombreCompleto("Juan", "Pérez");
System.out.println(nombreCompleto);  // Imprime: Juan Pérez
```

### 5.6 Métodos con Retorno Boolean

```java
public static boolean esAdulto(int edad) {
    return edad >= 18;
}

// Llamada al método
if (esAdulto(25)) {
    System.out.println("Es adulto");
}
```

### Scope de Variables (Alcance)

Las variables declaradas dentro de un método solo existen dentro de ese método:

```java
public static void ejemplo() {
    int numero = 10;
    System.out.println(numero);  // OK
}

// fuera del método
System.out.println(numero);  // ERROR: numero no existe aquí
```

### Métodos Auxiliares

En un programa Java, los métodos deben estar dentro de una clase. Aquí hay un ejemplo de un programa completo con métodos:

```java
public class MiPrograma {
    
    public static void main(String[] args) {
        System.out.println("Bienvenido");
        int suma = sumar(5, 3);
        System.out.println("5 + 3 = " + suma);
    }
    
    public static int sumar(int a, int b) {
        return a + b;
    }
}
```

---

## 6. Entrada y Salida (I/O)

### Salida: System.out.println()

```java
System.out.println("Texto");  // Imprime y salta a una nueva línea
System.out.print("Texto");     // Imprime sin saltar de línea
```

### Entrada: Scanner

Para leer datos del usuario, usamos la clase `Scanner`:

```java
import java.util.Scanner;

public class MiPrograma {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        
        System.out.print("¿Cuál es tu nombre? ");
        String nombre = entrada.nextLine();
        
        System.out.print("¿Cuál es tu edad? ");
        int edad = entrada.nextInt();
        
        System.out.println("Hola " + nombre + ", tienes " + edad + " años");
        
        entrada.close();
    }
}
```

### Métodos comunes de Scanner

| Método      | Descripción |
|-------------|-------------|
| `nextLine()` | Lee una línea completa (String) |
| `nextInt()` | Lee un número entero (int) |
| `nextDouble()` | Lee un número decimal (double) |
| `nextBoolean()` | Lee un valor booleano |
| `next()` | Lee una palabra (sin espacios) |

