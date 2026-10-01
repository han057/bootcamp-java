# Guía de uso de Docker

## 1. Gestión de contenedores

### Ejecutar contenedores
```bash
docker run <imagen>                          # Ejecuta un contenedor (foreground)
docker run -d <imagen>                       # Ejecuta en background (detached)
docker run -it <imagen> bash                 # Modo interactivo con terminal
docker run --name mi-contenedor <imagen>      # Asigna un nombre
docker run -p 8080:80 <imagen>                # Mapea puerto host:contenedor
docker run -e VAR=valor <imagen>              # Define variable de entorno
docker run --rm <imagen>                      # Elimina el contenedor al terminar
docker run -d --name web -p 8080:80 nginx     # Ejemplo combinado
```

### Ver contenedores
```bash
docker ps                      # Contenedores en ejecución
docker ps -a                   # Todos (incluye detenidos)
docker inspect <contenedor>    # Detalles completos (JSON)
docker logs <contenedor>       # Ver logs
docker logs -f <contenedor>    # Seguir logs en tiempo real
docker stats                   # Uso de recursos en vivo
```

### Detener contenedores
```bash
docker stop <contenedor>       # Detiene (señal SIGTERM, gracioso)
docker stop $(docker ps -q)    # Detiene todos los contenedores activos
docker kill <contenedor>       # Fuerza la detención (SIGKILL)
docker pause <contenedor>      # Pausa procesos
docker unpause <contenedor>    # Reanuda
```

### Borrar contenedores
```bash
docker rm <contenedor>             # Elimina un contenedor detenido
docker rm -f <contenedor>          # Fuerza eliminación (aunque esté corriendo)
docker rm $(docker ps -aq)         # Elimina todos los contenedores
docker container prune             # Elimina todos los contenedores detenidos
```

### Ejecutar comandos dentro de un contenedor
```bash
docker exec -it <contenedor> bash     # Abre una shell dentro
docker exec <contenedor> ls /app      # Ejecuta un comando puntual
```

---

## 2. Gestión de imágenes

```bash
docker images                   # Lista imágenes locales
docker pull <imagen>:<tag>      # Descarga una imagen
docker build -t mi-app:1.0 .    # Construye imagen desde Dockerfile
docker rmi <imagen>             # Elimina una imagen
docker image prune              # Elimina imágenes sin usar (dangling)
docker image prune -a           # Elimina todas las imágenes no usadas por contenedores
docker tag <origen> <destino>   # Renombra/etiqueta una imagen
docker push <imagen>            # Sube imagen a un registry
```

---

## 3. Manejo de volúmenes

Los volúmenes permiten persistir datos fuera del ciclo de vida del contenedor.

```bash
docker volume create mi-volumen           # Crea un volumen
docker volume ls                          # Lista volúmenes
docker volume inspect mi-volumen          # Detalles del volumen
docker volume rm mi-volumen               # Elimina un volumen
docker volume prune                       # Elimina volúmenes no usados

# Montar un volumen en un contenedor
docker run -d -v mi-volumen:/app/data nginx

# Bind mount (carpeta del host)
docker run -d -v /ruta/en/host:/app/data nginx

# Montaje de solo lectura
docker run -d -v mi-volumen:/app/data:ro nginx
```

**Diferencia clave:**
- **Volumen (named volume):** gestionado por Docker, portable, recomendado para producción.
- **Bind mount:** apunta a una ruta específica del host, útil en desarrollo.

---

## 4. Manejo de redes

Docker permite aislar y comunicar contenedores mediante redes virtuales.

```bash
docker network ls                         # Lista redes
docker network create mi-red              # Crea red (driver bridge por defecto)
docker network create --driver bridge mi-red
docker network inspect mi-red             # Detalles de la red
docker network rm mi-red                  # Elimina una red
docker network prune                      # Elimina redes no usadas

# Conectar/desconectar contenedores
docker network connect mi-red <contenedor>
docker network disconnect mi-red <contenedor>

# Ejecutar un contenedor en una red específica
docker run -d --name app --network mi-red mi-imagen
```

**Tipos de driver comunes:**
- `bridge`: red aislada por defecto (comunicación entre contenedores por nombre).
- `host`: el contenedor usa la red del host directamente.
- `none`: sin red.
- `overlay`: para comunicación entre múltiples hosts (Docker Swarm).

---

## 5. Limpieza general

```bash
docker system df       # Espacio usado por Docker
docker system prune    # Elimina contenedores, redes e imágenes sin usar
docker system prune -a --volumes   # Limpieza total (¡cuidado, borra volúmenes!)
```

---

## 6. Guía de Docker Compose

Docker Compose permite definir y levantar múltiples contenedores (servicios) con un solo archivo `docker-compose.yml`.

### Estructura básica de `docker-compose.yml`
```yaml
version: "3.9"

services:
  app:
    build: .
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
    depends_on:
      - db
    networks:
      - mired

  db:
    image: postgres:15
    environment:
      POSTGRES_USER: user
      POSTGRES_PASSWORD: password
      POSTGRES_DB: mydb
    volumes:
      - db-data:/var/lib/postgresql/data
    networks:
      - mired

volumes:
  db-data:

networks:
  mired:
```

### Comandos principales

```bash
docker compose up               # Levanta los servicios (foreground)
docker compose up -d            # Levanta en background
docker compose up --build       # Reconstruye imágenes antes de levantar

docker compose ps                # Lista servicios del proyecto
docker compose logs              # Logs de todos los servicios
docker compose logs -f <servicio> # Logs en tiempo real de un servicio

docker compose stop              # Detiene los servicios
docker compose start             # Reinicia servicios detenidos
docker compose restart           # Reinicia servicios

docker compose down              # Detiene y elimina contenedores + red
docker compose down -v           # Además elimina volúmenes
docker compose down --rmi all    # Además elimina imágenes construidas

docker compose exec <servicio> bash   # Shell dentro de un servicio
docker compose build                  # Construye las imágenes definidas
docker compose config                 # Valida y muestra el archivo final procesado
```

### Notas útiles
- `depends_on` controla el orden de arranque, **no** espera a que el servicio esté realmente listo (para eso se usan healthchecks).
- Por defecto, Compose crea una red propia donde los servicios se resuelven por su nombre (`db`, `app`, etc.).
- Los volúmenes y redes declarados en el archivo se gestionan igual que con `docker volume` / `docker network`, pero con el prefijo del proyecto.
