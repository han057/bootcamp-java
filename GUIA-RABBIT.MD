# Guía de RabbitMQ como cola de mensajes

## 1. ¿Qué es RabbitMQ?

RabbitMQ es un **message broker** (intermediario de mensajes) de código abierto que implementa el protocolo **AMQP 0-9-1**. Recibe mensajes de aplicaciones que los *producen* y los entrega a aplicaciones que los *consumen*, sin que ambas necesiten conocerse ni estar activas al mismo tiempo.

### ¿Por qué usar una cola de mensajes?

| Problema | Cómo ayuda RabbitMQ |
|---|---|
| Acoplamiento entre servicios | Productor y consumidor solo comparten el formato del mensaje |
| Picos de carga | La cola actúa como "buffer"; el consumidor procesa a su ritmo |
| Tareas lentas (emails, reportes, imágenes) | Se ejecutan de forma asíncrona, sin bloquear la petición del usuario |
| Fallos del consumidor | Los mensajes se conservan hasta que se procesan (con persistencia y *acks*) |
| Escalabilidad | Se añaden más consumidores sobre la misma cola (*competing consumers*) |

---

## 2. Conceptos básicos

```
┌──────────┐      ┌──────────┐  routing key  ┌─────────┐      ┌──────────┐
│ Producer │ ───► │ Exchange │ ────────────► │  Queue  │ ───► │ Consumer │
└──────────┘      └──────────┘   (binding)   └─────────┘      └──────────┘
```

El productor **nunca publica directamente a una cola**: publica a un *exchange*, y este decide a qué cola(s) enviar el mensaje según los *bindings* y la *routing key*.

### 2.1 Producer (productor)
Aplicación que envía mensajes.

### 2.2 Consumer (consumidor)
Aplicación que recibe y procesa mensajes de una cola.

### 2.3 Queue (cola)
Buffer ordenado (FIFO) que almacena mensajes hasta que un consumidor los procesa. Propiedades importantes:

- **durable**: sobrevive a un reinicio del broker.
- **exclusive**: solo la usa la conexión que la creó.
- **auto-delete**: se borra cuando se desconecta el último consumidor.

### 2.4 Exchange
Recibe los mensajes del productor y los enruta a las colas. Tipos:

| Tipo | Comportamiento | Uso típico |
|---|---|---|
| **direct** | Entrega a las colas cuyo binding key es **igual** a la routing key | Enrutamiento exacto (ej. `error`, `info`) |
| **fanout** | Entrega a **todas** las colas enlazadas, ignora la routing key | Broadcast / pub-sub puro |
| **topic** | Enruta por **patrón** de routing key usando `*` y `#` | Enrutamiento flexible (`orden.*.creada`) |
| **headers** | Enruta según los *headers* del mensaje | Casos avanzados, poco común |

### 2.5 Binding (enlace)
Regla que une un exchange con una cola. Puede incluir una *binding key*.

### 2.6 Routing key
Cadena de texto que el productor adjunta al mensaje. El exchange la compara con los bindings para decidir el destino.

En un exchange **topic**:
- `*` sustituye **exactamente una** palabra.
- `#` sustituye **cero o más** palabras.
- Las palabras se separan con punto: `pedido.europa.creado`.

| Binding key | `pedido.europa.creado` | `pedido.asia.cancelado` | `pedido.creado` |
|---|---|---|---|
| `pedido.*.creado` | ✅ | ❌ | ❌ |
| `pedido.#` | ✅ | ✅ | ✅ |
| `#.creado` | ✅ | ❌ | ✅ |

### 2.7 Virtual host (vhost)
Espacio lógico aislado dentro del broker (colas, exchanges y permisos propios). Por defecto: `/`.

### 2.8 Connection y Channel
- **Connection**: conexión TCP con el broker (costosa).
- **Channel**: conexión lógica ligera dentro de una connection. Se usan varios channels sobre una sola connection.

### 2.9 Acknowledgements (ACK)
Confirmación que el consumidor envía al broker al terminar de procesar un mensaje.
- `ack`: procesado correctamente → el broker lo elimina.
- `nack` / `reject`: falló → se puede reencolar o descartar (o enviar a una DLQ).
- Si el consumidor muere sin hacer ack, el mensaje se reentrega a otro consumidor.

### 2.10 Dead Letter Queue (DLQ)
Cola a la que van los mensajes rechazados, expirados (TTL) o que exceden el largo de la cola. Muy útil para reintentos y diagnóstico de errores.

---

## 3. Patrones de mensajería

### 3.1 Work Queue (cola de trabajo)
Un productor, una cola, **varios consumidores** que compiten. Cada mensaje lo procesa **un solo** consumidor. Sirve para repartir carga.

```
Producer ──► [ Queue ] ──┬──► Consumer 1
                         └──► Consumer 2
```

### 3.2 Publish/Subscribe (pub/sub)
Un mensaje llega a **todos** los suscriptores. Se implementa con un exchange **fanout** (o topic) y **una cola por suscriptor**.

```
                    ┌──► [Queue A] ──► Servicio Email
Producer ──► Fanout ┼──► [Queue B] ──► Servicio Inventario
                    └──► [Queue C] ──► Servicio Auditoría
```

### 3.3 Routing
Exchange **direct**: el consumidor recibe solo los mensajes de las routing keys a las que se enlaza.

### 3.4 Topics
Exchange **topic**: enrutamiento por patrones con comodines.

### 3.5 RPC (Request/Reply)
El cliente envía una petición con `replyTo` y `correlationId`; el servidor responde en la cola indicada.

---

## 4. Instalación rápida con Docker

```bash
docker run -d --name rabbitmq \
  -p 5672:5672 -p 15672:15672 \
  -e RABBITMQ_DEFAULT_USER=guest \
  -e RABBITMQ_DEFAULT_PASS=guest \
  rabbitmq:3-management
```

- `5672`: puerto AMQP (las aplicaciones se conectan aquí).
- `15672`: consola web de administración → <http://localhost:15672> (usuario/clave: `guest` / `guest`).

Con **docker-compose.yml**:

```yaml
services:
  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    ports:
      - "5672:5672"
      - "15672:15672"
    environment:
      RABBITMQ_DEFAULT_USER: guest
      RABBITMQ_DEFAULT_PASS: guest
```

---

## 5. Ejemplo con Java y Spring Boot (Spring AMQP)

Se construye un flujo de **pedidos**: un servicio publica un evento cuando se crea un pedido y otro lo consume. Usa un exchange **topic**.

```
POST /pedidos ──► Producer ──► [pedidos.exchange] ──(pedido.creado)──► [pedidos.cola] ──► Consumer
                                                                              │ (si falla)
                                                                              ▼
                                                                       [pedidos.dlq]
```

### 5.1 Estructura del proyecto

```
src/main/java/com/splai/rabbit/
├── RabbitApplication.java
├── config/RabbitConfig.java
├── dto/PedidoEvent.java
├── producer/PedidoProducer.java
├── consumer/PedidoConsumer.java
└── controller/PedidoController.java
src/main/resources/application.yml
```

### 5.2 Dependencias (Gradle)

```groovy
dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-amqp'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
    testImplementation 'org.springframework.amqp:spring-rabbit-test'
}
```

### 5.3 Configuración (`application.yml`)

```yaml
spring:
  rabbitmq:
    host: localhost
    port: 5672
    username: guest
    password: guest
    listener:
      simple:
        acknowledge-mode: auto      # ack automático si el método termina sin excepción
        default-requeue-rejected: false  # no reencolar infinitamente: ir a la DLQ
        retry:
          enabled: true
          initial-interval: 2s
          max-attempts: 3
          multiplier: 2
```

### 5.4 Mensaje (DTO)

```java
package com.splai.rabbit.dto;

public record PedidoEvent(Long id, String cliente, double total) {}
```

### 5.5 Declaración de exchange, colas y bindings

Spring crea automáticamente en el broker los elementos declarados como `@Bean`.

```java
package com.splai.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    public static final String EXCHANGE = "pedidos.exchange";
    public static final String QUEUE = "pedidos.cola";
    public static final String ROUTING_KEY = "pedido.creado";

    public static final String DLX = "pedidos.dlx";
    public static final String DLQ = "pedidos.dlq";

    @Bean
    public TopicExchange pedidosExchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue pedidosQueue() {
        return QueueBuilder.durable(QUEUE)
                .withArgument("x-dead-letter-exchange", DLX)       // a dónde van los fallidos
                .withArgument("x-dead-letter-routing-key", "pedido.fallido")
                .build();
    }

    @Bean
    public Binding pedidosBinding() {
        return BindingBuilder.bind(pedidosQueue())
                .to(pedidosExchange())
                .with(ROUTING_KEY);
    }

    // --- Dead Letter ---
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX);
    }

    @Bean
    public Queue deadLetterQueue() {
        return QueueBuilder.durable(DLQ).build();
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with("pedido.fallido");
    }

    // Serializa/deserializa los mensajes como JSON en vez de serialización Java
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
```

> Spring Boot detecta el bean `MessageConverter` y lo aplica automáticamente a `RabbitTemplate` y a los `@RabbitListener`.

### 5.6 Productor

```java
package com.splai.rabbit.producer;

import com.splai.rabbit.config.RabbitConfig;
import com.splai.rabbit.dto.PedidoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoProducer {

    private static final Logger log = LoggerFactory.getLogger(PedidoProducer.class);

    private final RabbitTemplate rabbitTemplate;

    public PedidoProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviar(PedidoEvent evento) {
        log.info("Publicando pedido: {}", evento);
        rabbitTemplate.convertAndSend(
                RabbitConfig.EXCHANGE,      // exchange
                RabbitConfig.ROUTING_KEY,   // routing key
                evento                      // payload (se convierte a JSON)
        );
    }
}
```

### 5.7 Consumidor

```java
package com.splai.rabbit.consumer;

import com.splai.rabbit.config.RabbitConfig;
import com.splai.rabbit.dto.PedidoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PedidoConsumer {

    private static final Logger log = LoggerFactory.getLogger(PedidoConsumer.class);

    @RabbitListener(queues = RabbitConfig.QUEUE)
    public void procesar(PedidoEvent evento) {
        log.info("Pedido recibido: {}", evento);

        if (evento.total() < 0) {
            // Lanzar excepción => reintentos => DLQ al agotarlos
            throw new IllegalArgumentException("Total inválido");
        }
        // ... lógica de negocio: enviar email, descontar stock, etc.
    }

    // Opcional: revisar qué llega a la cola de errores
    @RabbitListener(queues = RabbitConfig.DLQ)
    public void procesarFallidos(PedidoEvent evento) {
        log.error("Pedido en DLQ: {}", evento);
    }
}
```

### 5.8 Controlador REST para probar

```java
package com.splai.rabbit.controller;

import com.splai.rabbit.dto.PedidoEvent;
import com.splai.rabbit.producer.PedidoProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoProducer producer;

    public PedidoController(PedidoProducer producer) {
        this.producer = producer;
    }

    @PostMapping
    public ResponseEntity<String> crear(@RequestBody PedidoEvent pedido) {
        producer.enviar(pedido);
        return ResponseEntity.accepted().body("Pedido enviado a la cola");
    }
}
```

### 5.9 Clase principal

```java
package com.splai.rabbit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitApplication {
    public static void main(String[] args) {
        SpringApplication.run(RabbitApplication.class, args);
    }
}
```

### 5.10 Ejecutar y probar

1. Levantar RabbitMQ (sección 4).
2. Arrancar la app: `./gradlew bootRun`
3. Enviar un pedido:

```bash
curl -X POST http://localhost:8080/pedidos \
  -H "Content-Type: application/json" \
  -d '{"id": 1, "cliente": "Ana", "total": 150.5}'
```

4. En los logs verás la publicación y la recepción del mensaje.
5. Para probar la DLQ, envía `"total": -10`: tras 3 intentos el mensaje terminará en `pedidos.dlq`.
6. Revisa exchanges, colas y mensajes en <http://localhost:15672>.

---

## 6. Ejemplo adicional: Pub/Sub con Fanout

Para que **varios servicios reciban el mismo mensaje**:

```java
@Configuration
public class FanoutConfig {

    @Bean
    FanoutExchange notificacionesExchange() {
        return new FanoutExchange("notificaciones.exchange");
    }

    @Bean
    Queue colaEmail() { return new Queue("notificaciones.email", true); }

    @Bean
    Queue colaSms() { return new Queue("notificaciones.sms", true); }

    @Bean
    Binding bindEmail(FanoutExchange notificacionesExchange, Queue colaEmail) {
        return BindingBuilder.bind(colaEmail).to(notificacionesExchange);
    }

    @Bean
    Binding bindSms(FanoutExchange notificacionesExchange, Queue colaSms) {
        return BindingBuilder.bind(colaSms).to(notificacionesExchange);
    }
}
```

```java
// Publicar (la routing key se ignora en fanout)
rabbitTemplate.convertAndSend("notificaciones.exchange", "", "Hola a todos");

// Cada servicio escucha su propia cola
@RabbitListener(queues = "notificaciones.email")
public void email(String msg) { /* enviar email */ }

@RabbitListener(queues = "notificaciones.sms")
public void sms(String msg) { /* enviar SMS */ }
```

---

## 7. Buenas prácticas

- **Colas y mensajes durables**: `durable = true` y mensajes persistentes (por defecto con `RabbitTemplate`).
- **ACK manual o automático bien configurado**: no perder mensajes si el consumidor falla.
- **Usar DLQ** y limitar reintentos para evitar bucles infinitos de reentrega.
- **Idempotencia**: un mensaje puede entregarse más de una vez (*at-least-once*); el consumidor debe tolerarlo.
- **JSON como formato** en lugar de serialización Java (interoperabilidad y seguridad).
- **Prefetch** (`spring.rabbitmq.listener.simple.prefetch`): limita cuántos mensajes sin ack recibe cada consumidor para repartir mejor la carga.
- **No crear una connection por mensaje**: reutiliza `RabbitTemplate` (Spring gestiona el pool de channels).
- **Nombres descriptivos y consistentes** para exchanges, colas y routing keys (`dominio.entidad.evento`).
- **No guardar secretos** (usuario/clave) en el código: usa variables de entorno o un gestor de secretos.
- **Monitorear** la consola de administración o métricas (Prometheus/Actuator) para detectar colas que crecen sin consumirse.

---

## 8. RabbitMQ vs otras tecnologías

| Característica | RabbitMQ | Apache Kafka |
|---|---|---|
| Modelo | Broker inteligente, cola de mensajes | Log distribuido de eventos |
| Mensaje tras consumirse | Se elimina | Se conserva (retención configurable) |
| Enrutamiento | Muy flexible (exchanges) | Por topics/particiones |
| Mejor para | Tareas, RPC, enrutamiento complejo | Streaming masivo, reprocesar histórico |

---

## 9. Resumen

- **Producer** → publica en un **Exchange** con una **Routing Key**.
- El **Exchange** (direct, fanout, topic, headers) enruta a **Queues** mediante **Bindings**.
- El **Consumer** lee de la cola y confirma con **ACK**.
- Los fallos se gestionan con reintentos y **Dead Letter Queues**.
- Con **Spring Boot** basta `spring-boot-starter-amqp`, beans `Queue/Exchange/Binding`, `RabbitTemplate` para enviar y `@RabbitListener` para recibir.

### Referencias
- Documentación oficial: <https://www.rabbitmq.com/docs>
- Tutoriales oficiales: <https://www.rabbitmq.com/tutorials>
- Spring AMQP: <https://docs.spring.io/spring-amqp/reference/>
