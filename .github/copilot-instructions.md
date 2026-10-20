# Repository instructions

## Repository shape

This repository is a set of independent bootcamp exercises and applications, not a single multi-project build. Run each tool from that project's directory; there is no root-level build, test, or lint command.

- `basicjava/` contains standalone Java exercises, organized by topic/package, including object-oriented banking examples and JUnit tests.
- `banco/` is a small Spring MVC learning app. Its client examples are in-memory and implemented directly in controllers.
- `springframework-boot/` is a separate Spring Boot REST application with controller, service, repository, entity, and exception packages. Its build includes Spring Data JPA, Redis, validation, and RabbitMQ.
- `rabbit-project/spring-rabbitmq/demo/` is another standalone Spring Boot app: the `/messages` web endpoint delegates to a producer, which publishes JSON to the direct exchange and queue declared in `config/RabbitConfig`; a listener consumes from that queue. Its Compose file supplies PostgreSQL and RabbitMQ.
- `react/` is a Vite + React + TypeScript app. UI components use the `useTodos` hook and shared types; API calls are grouped in `src/api`. Vite proxies `/api` to `http://localhost:8080` for local Spring integration.
- `my-react-router-app/` is a separate React Router framework-mode app with route configuration in `app/routes.ts` and route components in `app/routes/`.
- `formularios/` and `taller/` contain standalone HTML/CSS/JavaScript and SQL/Docker learning material. Root-level Spanish Markdown guides are course references, not build documentation.

## Build, test, and lint

### Java / Gradle

Each Java project has its own Gradle wrapper. Run commands from the project directory:

```bash
./gradlew build
./gradlew test
```

Run a single test class with `./gradlew test --tests 'fully.qualified.ClassName'`, or a method with `./gradlew test --tests 'fully.qualified.ClassName.methodName'`. For example, in `basicjava/`:

```bash
./gradlew test --tests 'poo.banca.CuentaTest'
./gradlew test --tests 'poo.banca.CuentaTest.depositar'
```

The other current test classes are `org.han.banco.BancoApplicationTests` in `banco/`, and `com.example.demo.DemoApplicationTests` in each Spring Boot project. The Spring Boot Gradle builds declare a Java 25 toolchain. No Java lint task is configured.

### `react/`

Run npm commands from `react/`:

```bash
npm run dev
npm run build       # TypeScript project build and Vite production build
npm test            # Vitest once
npm test -- src/App.test.tsx
npm run lint        # oxlint
```

### `my-react-router-app/`

Run npm commands from `my-react-router-app/`:

```bash
npm run dev
npm run build
npm run typecheck   # React Router type generation and TypeScript check
npm start           # Serve the production build
```

This package currently defines no test or lint script.

## Codebase conventions

- Java exercise and API code uses Spanish domain names and package segments; follow the language and terminology of the surrounding project rather than translating existing concepts.
- Keep changes within the selected application. The similarly named banking and Spring projects are separate Gradle builds with different persistence and application boundaries.
- In `springframework-boot/`, keep HTTP handling in `controller`, business operations in `servicio`, persistence access in `datos`, and mapped domain objects in `entidades`. The simpler `banco/` demo intentionally keeps its examples in the controller package.
- In `rabbit-project/spring-rabbitmq/demo/`, use the queue, exchange, and routing-key constants in `RabbitConfig` so the producer and listener remain aligned; message payloads are converted as JSON.
- In `react/`, keep shared domain shapes in `src/types`, reusable task state in `src/hooks`, presentation in `src/components`, and backend requests in `src/api`. Preserve the `/api` development proxy when changing API integration.
- `my-react-router-app/` uses React Router's generated route types and file-based route component organization; update the route config when adding routes.
- Formatting differs between exercises and apps. Match the neighboring files instead of applying a repository-wide formatter or style conversion.
