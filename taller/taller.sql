-- =====================================================================
-- TALLER SQL - PostgreSQL
-- Creación de BD, 3 tablas relacionadas, carga masiva (1.000.000 de
-- registros), JOIN sin índice vs JOIN con índice.
--
-- Ejecución (desde psql):
--   psql -U postgres -f taller.sql
-- Nota: \c y \timing son comandos de psql, no SQL estándar.
-- =====================================================================

-- ---------------------------------------------------------------------
-- 1. CREACIÓN DE LA BASE DE DATOS
-- ---------------------------------------------------------------------
DROP DATABASE IF EXISTS taller_db;
CREATE DATABASE taller_db;

\c taller_db

-- Activa la medición de tiempo de cada sentencia
\timing on

-- ---------------------------------------------------------------------
-- 2. CREACIÓN DE 3 TABLAS RELACIONADAS
--    clientes (1) ---< pedidos (N) >--- (1) productos
--    La tabla principal (la de 1.000.000 de filas) es "pedidos".
-- ---------------------------------------------------------------------
CREATE TABLE clientes (
    id          SERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL,
    email       VARCHAR(150) NOT NULL,
    ciudad      VARCHAR(50)  NOT NULL,
    creado_en   TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE TABLE productos (
    id          SERIAL PRIMARY KEY,
    nombre      VARCHAR(100)   NOT NULL,
    categoria   VARCHAR(50)    NOT NULL,
    precio      NUMERIC(10, 2) NOT NULL
);

-- Se crean las FK sin índice propio sobre las columnas cliente_id y
-- producto_id (PostgreSQL NO crea índices automáticamente en las FK),
-- lo que permite demostrar el costo del JOIN sin índice.
CREATE TABLE pedidos (
    id           SERIAL PRIMARY KEY,
    cliente_id   INT            NOT NULL REFERENCES clientes (id),
    producto_id  INT            NOT NULL REFERENCES productos (id),
    cantidad     INT            NOT NULL,
    total        NUMERIC(12, 2) NOT NULL,
    fecha_pedido TIMESTAMP      NOT NULL
);

-- ---------------------------------------------------------------------
-- 3. INSERCIÓN DE DATOS
-- ---------------------------------------------------------------------

-- 3.1 Tablas de referencia
INSERT INTO clientes (nombre, email, ciudad)
SELECT
    'Cliente ' || g,
    'cliente' || g || '@correo.com',
    (ARRAY['Madrid', 'Malaga', 'Cadiz', 'Sevilla', 'Cartagena',
           'Valencia', 'Barcelona', 'Ronda'])[1 + (g % 8)]
FROM generate_series(1, 50000) AS g;

INSERT INTO productos (nombre, categoria, precio)
SELECT
    'Producto ' || g,
    (ARRAY['Electrónica', 'Hogar', 'Ropa', 'Deportes', 'Libros'])[1 + (g % 5)],
    ROUND((random() * 990 + 10)::numeric, 2)
FROM generate_series(1, 5000) AS g;

-- 3.2 Tabla principal: 1.000.000 de pedidos
INSERT INTO pedidos (cliente_id, producto_id, cantidad, total, fecha_pedido)
SELECT
    1 + floor(random() * 50000)::int,
    1 + floor(random() * 5000)::int,
    q,
    q * ROUND((random() * 990 + 10)::numeric, 2),
    NOW() - (random() * 365 || ' days')::interval
FROM (
    SELECT g, 1 + floor(random() * 5)::int AS q
    FROM generate_series(1, 1000000) AS g
) AS t;

-- Actualiza estadísticas para que el planificador decida bien
ANALYZE;

-- Verificación de conteos
SELECT 'clientes'  AS tabla, COUNT(*) AS registros FROM clientes
UNION ALL
SELECT 'productos', COUNT(*) FROM productos
UNION ALL
SELECT 'pedidos',   COUNT(*) FROM pedidos;

-- ---------------------------------------------------------------------
-- 4. CONSULTA CON JOIN SIN ÍNDICE
--    Se busca el historial de un cliente concreto. Como pedidos.cliente_id
--    no tiene índice, PostgreSQL hace un Seq Scan sobre 1.000.000 filas.
-- ---------------------------------------------------------------------
EXPLAIN (ANALYZE, BUFFERS)
SELECT
    c.nombre,
    c.ciudad,
    p.id          AS pedido_id,
    pr.nombre     AS producto,
    p.cantidad,
    p.total,
    p.fecha_pedido
FROM pedidos p
JOIN clientes  c  ON c.id  = p.cliente_id
JOIN productos pr ON pr.id = p.producto_id
WHERE p.cliente_id = 12345;

-- ---------------------------------------------------------------------
-- 5. CREACIÓN DE ÍNDICES
-- ---------------------------------------------------------------------
CREATE INDEX idx_pedidos_cliente_id  ON pedidos (cliente_id);
CREATE INDEX idx_pedidos_producto_id ON pedidos (producto_id);

-- Actualiza estadísticas tras crear los índices
ANALYZE pedidos;

-- ---------------------------------------------------------------------
-- 6. MISMA CONSULTA CON JOIN, AHORA CON ÍNDICE
--    Se espera un Index/Bitmap Scan en lugar de Seq Scan, con una
--    reducción notable del tiempo de ejecución.
-- ---------------------------------------------------------------------
EXPLAIN (ANALYZE, BUFFERS)
SELECT
    c.nombre,
    c.ciudad,
    p.id          AS pedido_id,
    pr.nombre     AS producto,
    p.cantidad,
    p.total,
    p.fecha_pedido
FROM pedidos p
JOIN clientes  c  ON c.id  = p.cliente_id
JOIN productos pr ON pr.id = p.producto_id
WHERE p.cliente_id = 12345;

-- ---------------------------------------------------------------------
-- 7. (OPCIONAL) LIMPIEZA
-- ---------------------------------------------------------------------
-- \c postgres
-- DROP DATABASE taller_db;
