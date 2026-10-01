# Guía de SQL (DDL, DML, JOINs e Índices)

## 1. DDL (Data Definition Language)

El DDL define y modifica la estructura de la base de datos: tablas, columnas, claves y restricciones.

### Crear base de datos y esquema
```sql
CREATE DATABASE banco;
USE banco;
```

### Crear tablas
```sql
CREATE TABLE clientes (
    id_cliente     INT PRIMARY KEY AUTO_INCREMENT,
    nombre         VARCHAR(100) NOT NULL,
    email          VARCHAR(150) NOT NULL UNIQUE,
    fecha_registro DATE NOT NULL DEFAULT (CURRENT_DATE)
);

CREATE TABLE cuentas (
    id_cuenta    INT PRIMARY KEY AUTO_INCREMENT,
    id_cliente   INT NOT NULL,
    tipo         VARCHAR(20) NOT NULL CHECK (tipo IN ('AHORRO', 'CORRIENTE')),
    saldo        DECIMAL(12,2) NOT NULL DEFAULT 0,
    CONSTRAINT fk_cuentas_cliente
        FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente)
);

CREATE TABLE movimientos (
    id_movimiento INT PRIMARY KEY AUTO_INCREMENT,
    id_cuenta     INT NOT NULL,
    tipo          VARCHAR(10) NOT NULL CHECK (tipo IN ('DEPOSITO', 'RETIRO')),
    monto         DECIMAL(12,2) NOT NULL,
    fecha         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_movimientos_cuenta
        FOREIGN KEY (id_cuenta) REFERENCES cuentas(id_cuenta)
);
```

### Modificar tablas
```sql
ALTER TABLE clientes ADD COLUMN telefono VARCHAR(20);
ALTER TABLE clientes MODIFY COLUMN telefono VARCHAR(30);   -- MySQL
ALTER TABLE clientes ALTER COLUMN telefono TYPE VARCHAR(30); -- PostgreSQL
ALTER TABLE clientes DROP COLUMN telefono;
ALTER TABLE clientes RENAME COLUMN nombre TO nombre_completo;
ALTER TABLE cuentas ADD CONSTRAINT uq_cuenta_cliente UNIQUE (id_cliente, tipo);
```

### Eliminar objetos
```sql
DROP TABLE movimientos;      -- Elimina la tabla y sus datos
TRUNCATE TABLE movimientos;  -- Vacía la tabla, conserva la estructura
DROP DATABASE banco;         -- Elimina toda la base de datos
```

**Notas:**
- `DROP` elimina la estructura; `TRUNCATE` vacía los datos (más rápido que `DELETE`, no dispara triggers de fila); `DELETE` (DML) borra filas con condición.
- Las claves foráneas (`FOREIGN KEY`) mantienen la integridad referencial entre tablas.

---

## 2. DML (Data Manipulation Language)

El DML manipula los datos almacenados: insertar, consultar, actualizar y borrar filas.

### INSERT
```sql
INSERT INTO clientes (nombre, email)
VALUES ('Ana Torres', 'ana.torres@example.com');

INSERT INTO clientes (nombre, email) VALUES
    ('Luis Pérez', 'luis.perez@example.com'),
    ('María Gómez', 'maria.gomez@example.com');

INSERT INTO cuentas (id_cliente, tipo, saldo) VALUES
    (1, 'AHORRO', 1500.00),
    (1, 'CORRIENTE', 300.00),
    (2, 'AHORRO', 2200.50);
```

### SELECT
```sql
SELECT * FROM clientes;

SELECT nombre, email FROM clientes
WHERE fecha_registro >= '2026-01-01'
ORDER BY nombre ASC;

SELECT tipo, COUNT(*) AS total_cuentas, SUM(saldo) AS saldo_total
FROM cuentas
GROUP BY tipo
HAVING SUM(saldo) > 1000;

SELECT * FROM cuentas
LIMIT 10 OFFSET 0;   -- Paginación
```

### UPDATE
```sql
UPDATE cuentas
SET saldo = saldo + 500.00
WHERE id_cuenta = 1;

UPDATE clientes
SET email = LOWER(email);
```

### DELETE
```sql
DELETE FROM movimientos
WHERE fecha < '2025-01-01';

DELETE FROM cuentas
WHERE saldo = 0 AND tipo = 'CORRIENTE';
```

### Transacciones
```sql
START TRANSACTION;

UPDATE cuentas SET saldo = saldo - 100 WHERE id_cuenta = 1;
UPDATE cuentas SET saldo = saldo + 100 WHERE id_cuenta = 2;

COMMIT;    -- Confirma los cambios
-- ROLLBACK;  -- Revierte los cambios si algo falla
```

---

## 3. JOINs

Los JOINs combinan filas de dos o más tablas según una condición de relación.

### INNER JOIN
Devuelve solo las filas que coinciden en ambas tablas.
```sql
SELECT c.nombre, cu.tipo, cu.saldo
FROM clientes c
INNER JOIN cuentas cu ON cu.id_cliente = c.id_cliente;
```

### LEFT JOIN
Devuelve todas las filas de la tabla izquierda, con `NULL` si no hay coincidencia en la derecha.
```sql
SELECT c.nombre, cu.id_cuenta, cu.saldo
FROM clientes c
LEFT JOIN cuentas cu ON cu.id_cliente = c.id_cliente;
-- Incluye clientes sin cuentas (cu.id_cuenta vendrá NULL)
```

### RIGHT JOIN
Equivalente al LEFT JOIN invirtiendo las tablas (todas las filas de la derecha).
```sql
SELECT c.nombre, cu.id_cuenta
FROM clientes c
RIGHT JOIN cuentas cu ON cu.id_cliente = c.id_cliente;
```

### FULL OUTER JOIN
Devuelve todas las filas de ambas tablas, coincidan o no (no soportado en MySQL; se emula con `UNION`).
```sql
-- PostgreSQL / SQL estándar
SELECT c.nombre, cu.id_cuenta
FROM clientes c
FULL OUTER JOIN cuentas cu ON cu.id_cliente = c.id_cliente;

-- Emulación en MySQL
SELECT c.nombre, cu.id_cuenta
FROM clientes c
LEFT JOIN cuentas cu ON cu.id_cliente = c.id_cliente
UNION
SELECT c.nombre, cu.id_cuenta
FROM clientes c
RIGHT JOIN cuentas cu ON cu.id_cliente = c.id_cliente;
```

### Joins múltiples
```sql
SELECT c.nombre, cu.tipo, m.tipo AS movimiento, m.monto, m.fecha
FROM clientes c
INNER JOIN cuentas cu ON cu.id_cliente = c.id_cliente
INNER JOIN movimientos m ON m.id_cuenta = cu.id_cuenta
WHERE m.fecha >= '2026-01-01'
ORDER BY m.fecha DESC;
```

### SELF JOIN
Une una tabla consigo misma (ej: empleados y su jefe).
```sql
CREATE TABLE empleados (
    id_empleado INT PRIMARY KEY,
    nombre      VARCHAR(100),
    id_jefe     INT REFERENCES empleados(id_empleado)
);

SELECT e.nombre AS empleado, j.nombre AS jefe
FROM empleados e
LEFT JOIN empleados j ON e.id_jefe = j.id_empleado;
```

**Resumen visual:**
| JOIN         | Resultado                                              |
|--------------|---------------------------------------------------------|
| INNER JOIN   | Solo coincidencias en ambas tablas                       |
| LEFT JOIN    | Todas las filas de la izquierda + coincidencias           |
| RIGHT JOIN   | Todas las filas de la derecha + coincidencias              |
| FULL OUTER   | Todas las filas de ambas tablas                            |

---

## 4. Índices

Los índices aceleran las búsquedas y los JOINs a costa de espacio extra y de un pequeño overhead en `INSERT`/`UPDATE`/`DELETE`.

### Crear índices
```sql
-- Índice simple sobre una columna muy consultada
CREATE INDEX idx_clientes_email ON clientes(email);

-- Índice compuesto (orden de columnas importa)
CREATE INDEX idx_movimientos_cuenta_fecha ON movimientos(id_cuenta, fecha);

-- Índice único (además de validar unicidad, acelera búsquedas)
CREATE UNIQUE INDEX uq_idx_clientes_email ON clientes(email);
```

### Ver y eliminar índices
```sql
SHOW INDEX FROM clientes;              -- MySQL
\d clientes                            -- PostgreSQL (psql)

DROP INDEX idx_clientes_email ON clientes;   -- MySQL
DROP INDEX idx_clientes_email;               -- PostgreSQL
```

### Analizar el uso de un índice
```sql
EXPLAIN SELECT * FROM clientes WHERE email = 'ana.torres@example.com';
-- Revisa si aparece "Using index" / "Index Scan" en lugar de "Full Scan" / "Seq Scan"
```

### Buenas prácticas
- Indexar columnas usadas frecuentemente en `WHERE`, `JOIN ON` y `ORDER BY`.
- Las claves primarias y, en la mayoría de motores, las foráneas con `UNIQUE`/`FOREIGN KEY` ya generan índice automáticamente; no duplicarlo.
- En índices compuestos, poner primero la columna más selectiva o la más usada en filtros de igualdad.
- Evitar indexar columnas con baja cardinalidad (ej: una columna booleana) si no aportan selectividad.
- Demasiados índices penalizan la velocidad de escritura (`INSERT`/`UPDATE`/`DELETE`); indexar solo lo necesario.

---

## 5. Ejemplo integrado

```sql
-- DDL
CREATE TABLE productos (
    id_producto INT PRIMARY KEY AUTO_INCREMENT,
    nombre      VARCHAR(100) NOT NULL,
    categoria   VARCHAR(50) NOT NULL,
    precio      DECIMAL(10,2) NOT NULL
);

CREATE INDEX idx_productos_categoria ON productos(categoria);

-- DML
INSERT INTO productos (nombre, categoria, precio) VALUES
    ('Teclado', 'Periféricos', 25.90),
    ('Monitor', 'Pantallas', 189.00),
    ('Mouse', 'Periféricos', 15.50);

-- Consulta con JOIN y filtro que aprovecha el índice
SELECT p.nombre, p.precio
FROM productos p
WHERE p.categoria = 'Periféricos'
ORDER BY p.precio DESC;
```
