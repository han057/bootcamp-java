# Taller: Docker con PostgreSQL

## Objetivos
1. Crear un contenedor PostgreSQL accesible desde `localhost`.
2. Entrar al contenedor con `docker exec`.
3. Conectarse a la base de datos con `psql` dentro del contenedor.

## Requisitos
- Docker instalado y el servicio en ejecución (`docker --version`).
- (Opcional) `psql` en el host para el ejercicio 4.

---

## Paso 1. Descargar la imagen
```bash
docker pull postgres:16
```

## Paso 2. Crear el contenedor
```bash
docker run -d \
  --name postgres-taller \
  -e POSTGRES_USER=taller \
  -e POSTGRES_PASSWORD=taller123 \
  -e POSTGRES_DB=tallerdb \
  -p 5432:5432 \
  postgres:16
```

| Opción | Significado |
|--------|-------------|
| `-d` | Ejecuta en segundo plano |
| `--name` | Nombre del contenedor |
| `-e POSTGRES_*` | Usuario, contraseña y base de datos iniciales |
| `-p 5432:5432` | Publica el puerto: `localhost:5432` → contenedor `:5432` |

> Si el puerto 5432 ya está ocupado en tu máquina, usa `-p 5433:5432` y conéctate al puerto 5433.

## Paso 3. Verificar que está corriendo
```bash
docker ps
docker logs postgres-taller
```
Debe aparecer en los logs: `database system is ready to accept connections`.

## Paso 4. Entrar al contenedor con `docker exec`
```bash
docker exec -it postgres-taller bash
```
Ahora estás dentro del contenedor (el prompt cambia a `root@<id>:/#`). Puedes explorar:
```bash
whoami
hostname
ls /var/lib/postgresql/data
exit        # salir del contenedor (no lo detiene)
```

## Paso 5. Conectarse con `psql` dentro del contenedor
Opción A: desde la shell del contenedor
```bash
docker exec -it postgres-taller bash
psql -U taller -d tallerdb
```

Opción B: directamente en un solo comando
```bash
docker exec -it postgres-taller psql -U taller -d tallerdb
```

Prompt esperado: `tallerdb=#`

### Comandos útiles de psql
| Comando | Descripción |
|---------|-------------|
| `\l` | Listar bases de datos |
| `\c tallerdb` | Conectarse a una base de datos |
| `\dt` | Listar tablas |
| `\d tabla` | Describir una tabla |
| `\du` | Listar usuarios/roles |
| `\conninfo` | Información de la conexión actual |
| `\q` | Salir de psql |

## Paso 6. Ejercicio práctico en psql
```sql
CREATE TABLE estudiante (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE
);

INSERT INTO estudiante (nombre, email) VALUES
  ('Ana', 'ana@example.com'),
  ('Luis', 'luis@example.com');

SELECT * FROM estudiante;
```

## Paso 7. Conexión desde localhost (host)
Si tienes `psql` instalado en tu máquina:
```bash
psql -h localhost -p 5432 -U taller -d tallerdb
```
Contraseña: `taller123`.

Sin `psql` local, puedes comprobar que el puerto está abierto:
```bash
nc -zv localhost 5432
```

Datos para un cliente gráfico o JDBC:
```
jdbc:postgresql://localhost:5432/tallerdb
usuario: taller
contraseña: taller123
```

## Paso 8. Persistencia (los datos sobreviven al contenedor)
Sin volumen, los datos se pierden al eliminar el contenedor. Comprueba:
```bash
docker rm -f postgres-taller
```
Recréalo con un volumen:
```bash
docker run -d \
  --name postgres-taller \
  -e POSTGRES_USER=taller \
  -e POSTGRES_PASSWORD=taller123 \
  -e POSTGRES_DB=tallerdb \
  -p 5432:5432 \
  -v pgdata-taller:/var/lib/postgresql/data \
  postgres:16
```
Crea datos, elimina el contenedor, vuelve a crearlo con el mismo `-v` y verifica que la tabla sigue existiendo.

## Paso 9. Ejecutar un script SQL
Con el archivo `taller.sql` de esta carpeta:
```bash
docker exec -i postgres-taller psql -U taller -d tallerdb < taller/taller.sql
```

## Paso 10. Limpieza
```bash
docker stop postgres-taller
docker rm postgres-taller
docker volume rm pgdata-taller
```

---

## Preguntas de repaso
1. ¿Qué hace la opción `-p 5432:5432`? ¿Qué pasa si la omites?
2. ¿Diferencia entre `docker exec -it` y `docker run -it`?
3. ¿Por qué `exit` en el contenedor no lo detiene?
4. ¿Qué ocurre con los datos al hacer `docker rm -f` sin volumen?
5. ¿Qué pasa si cambias `POSTGRES_PASSWORD` con un volumen que ya tiene datos?

## Solución rápida (resumen)
```bash
docker run -d --name postgres-taller -e POSTGRES_USER=taller -e POSTGRES_PASSWORD=taller123 -e POSTGRES_DB=tallerdb -p 5432:5432 postgres:16
docker exec -it postgres-taller psql -U taller -d tallerdb
```
