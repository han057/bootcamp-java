# 10 Ejercicios de Java - Fundamentos

Basados en el documento "FUNDAMENTOS_JAVA.md", aquí hay 10 ejercicios para practicar variables, tipos de datos, operadores, estructuras de control de flujo y métodos.

---

## Ejercicio 1: Cálculo del Área de un Rectángulo

**Objetivo:** Practicar variables y operadores aritméticos.

**Descripción:**
Crea un programa que calcule el área de un rectángulo. El programa debe:
1. Declarar dos variables: `ancho` y `alto` (tipo double)
2. Asignarles valores
3. Calcular el área (ancho × alto)
4. Mostrar el resultado

**Ejemplo de salida:**
```
Ancho: 5.0
Alto: 3.0
Área: 15.0
```

**Requisitos:**
- Usar variables de tipo `double`
- Usar el operador de multiplicación `*`
- Mostrar el resultado con `System.out.println()`

---

## Ejercicio 2: Conversión de Temperatura

**Objetivo:** Practicar conversión de tipos y operadores.

**Descripción:**
Crea un programa que convierta grados Celsius a Fahrenheit. La fórmula es:
```
F = (C × 9/5) + 32
```

El programa debe:
1. Declarar una variable `celsius` con valor 25
2. Calcular la temperatura en Fahrenheit
3. Mostrar ambas temperaturas

**Ejemplo de salida:**
```
Temperatura en Celsius: 25
Temperatura en Fahrenheit: 77.0
```

---

## Ejercicio 3: Clasificación de Edad

**Objetivo:** Practicar estructuras IF-ELSE.

**Descripción:**
Crea un programa que clasifique a una persona según su edad:
- Menor de 13: "Niño"
- De 13 a 17: "Adolescente"
- De 18 a 65: "Adulto"
- Mayor de 65: "Adulto Mayor"

El programa debe leer la edad del usuario usando `Scanner` y mostrar la clasificación.

**Ejemplo de salida:**
```
Ingresa tu edad: 25
Clasificación: Adulto
```

---

## Ejercicio 4: Validador de Número Primo

**Objetivo:** Practicar operadores lógicos y condicionales.

**Descripción:**
Crea un programa que determine si un número es primo o no. Un número primo es divisible solo por 1 y por sí mismo.

El programa debe:
1. Leer un número entero del usuario
2. Verificar si es primo
3. Mostrar un mensaje indicando si es primo o no

**Ejemplo de salida:**
```
Ingresa un número: 7
7 es un número primo
```

**Pista:** Un número es primo si solo tiene 2 divisores.

---

## Ejercicio 5: Tabla de Multiplicar

**Objetivo:** Practicar bucles FOR.

**Descripción:**
Crea un programa que muestre la tabla de multiplicar de un número ingresado por el usuario.

El programa debe:
1. Leer un número entero del usuario
2. Mostrar la tabla de multiplicar del 1 al 10

**Ejemplo de salida:**
```
Ingresa un número: 5
Tabla del 5:
5 × 1 = 5
5 × 2 = 10
5 × 3 = 15
...
5 × 10 = 50
```

---

## Ejercicio 6: Suma de Números Pares

**Objetivo:** Practicar bucles y condicionales.

**Descripción:**
Crea un programa que calcule la suma de todos los números pares del 1 al 100.

El programa debe:
1. Usar un bucle para recorrer los números del 1 al 100
2. Sumar solo los números pares
3. Mostrar el resultado final

**Ejemplo de salida:**
```
La suma de números pares del 1 al 100 es: 2550
```

**Pista:** Un número es par si `número % 2 == 0`

---

## Ejercicio 7: Calculadora Simple

**Objetivo:** Practicar métodos y switch.

**Descripción:**
Crea un programa con métodos para realizar operaciones matemáticas. El programa debe:
1. Crear métodos para sumar, restar, multiplicar y dividir
2. Leer dos números y un operador del usuario
3. Usar switch para seleccionar la operación
4. Llamar al método correspondiente
5. Mostrar el resultado

**Métodos a crear:**
```java
public static double sumar(double a, double b)
public static double restar(double a, double b)
public static double multiplicar(double a, double b)
public static double dividir(double a, double b)
```

**Ejemplo de salida:**
```
Ingresa el primer número: 10
Ingresa el segundo número: 5
Ingresa la operación (+, -, *, /): +
Resultado: 15.0
```

---

## Ejercicio 8: Contador de Vocales

**Objetivo:** Practicar bucles y métodos con retorno.

**Descripción:**
Crea un programa que cuente la cantidad de vocales en una palabra. El programa debe:
1. Leer una palabra del usuario
2. Crear un método que cuente las vocales
3. Mostrar el resultado

**Método a crear:**
```java
public static int contarVocales(String palabra)
```

**Ejemplo de salida:**
```
Ingresa una palabra: programacion
Cantidad de vocales: 5
```

---

## Ejercicio 9: Secuencia Fibonacci

**Objetivo:** Practicar bucles while y métodos.

**Descripción:**
Crea un programa que genere la secuencia de Fibonacci hasta un número específico de términos.

La serie Fibonacci es: 0, 1, 1, 2, 3, 5, 8, 13, 21...
Cada número es la suma de los dos anteriores.

El programa debe:
1. Leer la cantidad de términos que desea ver del usuario
2. Crear un método que genere la secuencia
3. Mostrar la secuencia

**Ejemplo de salida:**
```
¿Cuántos términos de Fibonacci deseas? 7
Secuencia Fibonacci:
0
1
1
2
3
5
8
```

---

## Ejercicio 10: Sistema de Calificaciones

**Objetivo:** Practicar todo lo aprendido: variables, operadores, condicionales, bucles y métodos.

**Descripción:**
Crea un programa que:
1. Lea las calificaciones de 5 estudiantes
2. Calcule el promedio de cada estudiante
3. Determine si aprobó (promedio >= 60) o reprobó
4. Muestre estadísticas generales (promedio de la clase, cantidad de aprobados, cantidad de reprobados)

**Métodos a crear:**
```java
public static double calcularPromedio(double[] calificaciones)
public static String obtenerEstado(double promedio)
public static void mostrarEstadisticas(int aprobados, int reprobados, double promedioClase)
```

**Ejemplo de interacción:**
```
Ingresa la calificación del estudiante 1: 85
Ingresa la calificación del estudiante 2: 92
Ingresa la calificación del estudiante 3: 78
Ingresa la calificación del estudiante 4: 65
Ingresa la calificación del estudiante 5: 55

Resultados:
Estudiante 1: Promedio 85.0 - Aprobado
Estudiante 2: Promedio 92.0 - Aprobado
Estudiante 3: Promedio 78.0 - Aprobado
Estudiante 4: Promedio 65.0 - Aprobado
Estudiante 5: Promedio 55.0 - Reprobado

Estadísticas:
Aprobados: 4
Reprobados: 1
Promedio de la Clase: 75.0
```

**Nota:** Este ejercicio requiere usar Arrays (arreglos). Un array es una colección de variables del mismo tipo.

Sintaxis básica:
```java
double[] calificaciones = new double[5];
calificaciones[0] = 85;
calificaciones[1] = 92;
// ...
```

---

## Soluciones Recomendadas

Para verificar tu trabajo, puedes:
1. Compilar tu código: `javac NombreArchivo.java`
2. Ejecutar: `java NombreArchivo`
3. Comparar tu salida con la salida esperada
4. Revisar que el código sea claro y bien estructurado

## Notas Importantes

- **Prueba casos límite:** Números negativos, cero, valores muy grandes
- **Manejo de errores:** Anticipa entradas inválidas del usuario
- **Nombres significativos:** Usa nombres de variables y métodos descriptivos
- **Código limpio:** Usa indentación consistente y comentarios cuando sea necesario

