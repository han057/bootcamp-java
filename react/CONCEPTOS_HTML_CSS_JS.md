# Conceptos de HTML, CSS y JavaScript

Guía de referencia para el bootcamp. Es la base previa a React (ver `CONCEPTOS_REACT.md`).

## Índice

1. [Cómo funciona la web](#1-cómo-funciona-la-web)
2. [HTML](#2-html)
3. [CSS](#3-css)
4. [JavaScript](#4-javascript)
5. [El DOM](#5-el-dom)
6. [Eventos](#6-eventos)
7. [Asincronía y peticiones HTTP](#7-asincronía-y-peticiones-http)
8. [Almacenamiento en el navegador](#8-almacenamiento-en-el-navegador)
9. [Herramientas](#9-herramientas)
10. [Buenas prácticas](#10-buenas-prácticas)
11. [Errores comunes](#11-errores-comunes)
12. [Ruta de aprendizaje](#12-ruta-de-aprendizaje)

---

## 1. Cómo funciona la web

- **Cliente–servidor**: el navegador (cliente) pide recursos y un servidor responde.
- **HTTP/HTTPS**: protocolo de comunicación. Métodos: `GET`, `POST`, `PUT`, `PATCH`, `DELETE`.
- **Códigos de estado**: `200` OK, `201` creado, `400` petición inválida, `401/403` sin autorización, `404` no encontrado, `500` error del servidor.
- **URL**: `https://dominio.com:443/ruta?clave=valor#ancla`
- Cada página se compone de tres capas:

| Capa | Lenguaje | Responsabilidad |
|---|---|---|
| Estructura | HTML | Qué contenido hay |
| Presentación | CSS | Cómo se ve |
| Comportamiento | JavaScript | Cómo reacciona |

## 2. HTML

### Estructura básica

```html
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Mi página</title>
    <link rel="stylesheet" href="estilos.css" />
  </head>
  <body>
    <h1>Hola mundo</h1>
    <script src="app.js" defer></script>
  </body>
</html>
```

- `<head>`: metadatos, título, hojas de estilo. `<body>`: contenido visible.
- `defer` en el script: se ejecuta cuando el HTML ya fue analizado.

### Elementos y atributos

```html
<a href="https://ejemplo.com" target="_blank" rel="noopener">Enlace</a>
<img src="foto.jpg" alt="Descripción de la foto" />
```

- **Elemento** = etiqueta de apertura + contenido + etiqueta de cierre.
- **Atributos**: `id` (único), `class` (reutilizable), `src`, `href`, `alt`, `data-*`.

### Etiquetas frecuentes

| Categoría | Etiquetas |
|---|---|
| Texto | `h1`–`h6`, `p`, `span`, `strong`, `em`, `br`, `hr` |
| Listas | `ul`, `ol`, `li`, `dl` |
| Enlaces y media | `a`, `img`, `video`, `audio`, `picture` |
| Tablas | `table`, `thead`, `tbody`, `tr`, `th`, `td` |
| Contenedores | `div` (genérico de bloque), `span` (genérico en línea) |

### HTML semántico

Usar etiquetas con significado mejora accesibilidad y SEO:

```html
<header>...</header>
<nav>...</nav>
<main>
  <article>
    <section>...</section>
  </article>
  <aside>...</aside>
</main>
<footer>...</footer>
```

### Formularios

```html
<form action="/enviar" method="post">
  <label for="email">Correo</label>
  <input type="email" id="email" name="email" required />

  <label for="pais">País</label>
  <select id="pais" name="pais">
    <option value="co">Colombia</option>
    <option value="es">España</option>
  </select>

  <textarea name="mensaje" rows="4"></textarea>

  <input type="checkbox" id="acepto" name="acepto" />
  <label for="acepto">Acepto los términos</label>

  <button type="submit">Enviar</button>
</form>
```

- Tipos de `input`: `text`, `email`, `password`, `number`, `date`, `checkbox`, `radio`, `file`, `range`.
- Validación nativa: `required`, `minlength`, `maxlength`, `min`, `max`, `pattern`.
- Cada `input` debe tener su `label` asociado (accesibilidad).

### Accesibilidad básica

- `alt` descriptivo en imágenes.
- Jerarquía de títulos ordenada (un solo `h1`).
- Poder usar todo con teclado; atributos `aria-*` solo cuando el HTML semántico no basta.

## 3. CSS

### Cómo aplicarlo

```html
<link rel="stylesheet" href="estilos.css" />   <!-- recomendado -->
<style> p { color: red; } </style>              <!-- interno -->
<p style="color: red;">...</p>                  <!-- en línea (evitar) -->
```

### Sintaxis y selectores

```css
selector {
  propiedad: valor;
}
```

| Selector | Ejemplo | Selecciona |
|---|---|---|
| Elemento | `p` | Todos los `<p>` |
| Clase | `.tarjeta` | `class="tarjeta"` |
| Id | `#menu` | `id="menu"` |
| Descendiente | `nav a` | Enlaces dentro de `nav` |
| Hijo directo | `ul > li` | `li` hijos directos |
| Hermano adyacente | `h2 + p` | `p` justo después de `h2` |
| Atributo | `input[type="text"]` | Inputs de texto |
| Pseudo-clase | `a:hover`, `li:first-child`, `input:focus` | Según estado o posición |
| Pseudo-elemento | `p::first-line`, `.x::before` | Partes de un elemento |
| Grupo | `h1, h2, h3` | Varios a la vez |

### Cascada, herencia y especificidad

- **Cascada**: si dos reglas chocan, gana la de mayor **especificidad**; a igualdad, la que aparece **después**.
- Especificidad (de menor a mayor): elemento < clase < id < estilo en línea < `!important`.
- **Herencia**: propiedades como `color` y `font-family` pasan a los hijos; `margin` o `border` no.
- Evita `!important` y selectores demasiado específicos.

### Unidades

| Unidad | Significado |
|---|---|
| `px` | Píxel (absoluta) |
| `%` | Relativa al padre |
| `em` | Relativa al tamaño de fuente del elemento |
| `rem` | Relativa al tamaño de fuente de la raíz |
| `vw` / `vh` | 1% del ancho / alto del viewport |
| `fr` | Fracción del espacio en Grid |

### Colores y tipografía

```css
color: #333;
background-color: rgb(255 0 0 / 0.5);
color: hsl(210 50% 40%);

font-family: 'Inter', system-ui, sans-serif;
font-size: 1rem;
font-weight: 600;
line-height: 1.5;
text-align: center;
```

### Variables CSS

```css
:root {
  --color-primario: #2563eb;
  --espacio: 1rem;
}

.boton {
  background: var(--color-primario);
  padding: var(--espacio);
}
```

### Modelo de caja (*box model*)

Cada elemento es una caja: **contenido → padding → border → margin**.

```css
* { box-sizing: border-box; }   /* width incluye padding y border */

.caja {
  width: 300px;
  padding: 16px;
  border: 1px solid #ccc;
  margin: 0 auto;               /* centrar horizontalmente */
}
```

### Display y posicionamiento

- `display`: `block` (ocupa toda la línea), `inline` (fluye con el texto), `inline-block`, `none`, `flex`, `grid`.
- `position`:

| Valor | Comportamiento |
|---|---|
| `static` | Por defecto, flujo normal |
| `relative` | Desplazable respecto a su posición normal; sirve de referencia |
| `absolute` | Respecto al ancestro posicionado más cercano |
| `fixed` | Respecto al viewport |
| `sticky` | Normal hasta alcanzar un umbral y luego se queda fijo |

- `z-index` controla el apilamiento (solo en elementos posicionados).

### Flexbox (una dimensión)

```css
.contenedor {
  display: flex;
  flex-direction: row;            /* o column */
  justify-content: space-between; /* eje principal */
  align-items: center;            /* eje transversal */
  gap: 1rem;
  flex-wrap: wrap;
}

.item { flex: 1; }                /* crece para ocupar el espacio */
```

### Grid (dos dimensiones)

```css
.galeria {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 1rem;
}

/* Responsivo sin media queries */
.auto {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
}
```

### Diseño responsivo

```css
/* Mobile first: estilos base para móvil, se amplía hacia arriba */
.contenedor { padding: 1rem; }

@media (min-width: 768px) {
  .contenedor { padding: 2rem; display: flex; }
}
```

- Requiere `<meta name="viewport" ...>` en el HTML.
- Imágenes fluidas: `img { max-width: 100%; height: auto; }`.

### Transiciones y animaciones

```css
.boton {
  transition: background-color 0.3s ease, transform 0.2s;
}
.boton:hover { transform: scale(1.05); }

@keyframes girar {
  from { transform: rotate(0deg); }
  to   { transform: rotate(360deg); }
}
.cargando { animation: girar 1s linear infinite; }
```

### Metodologías y organización

- **BEM**: `bloque__elemento--modificador` (ejemplo: `.tarjeta__titulo--grande`).
- Preprocesadores (Sass) y frameworks (Bootstrap, Tailwind) como siguiente paso.

## 4. JavaScript

### Variables y tipos

```js
const nombre = 'Ana';      // no reasignable (preferido)
let edad = 30;             // reasignable
// var: evitar (scope de función, hoisting)
```

- **Primitivos**: `string`, `number`, `boolean`, `null`, `undefined`, `bigint`, `symbol`.
- **Objetos**: objetos, arreglos, funciones, fechas.
- `typeof x` devuelve el tipo.
- **Igualdad**: usar `===` / `!==` (estricta) en lugar de `==`.
- Valores *falsy*: `false`, `0`, `''`, `null`, `undefined`, `NaN`. El resto es *truthy*.

### Operadores útiles

```js
const nombreFinal = usuario?.nombre ?? 'Invitado'; // optional chaining + nullish
const etiqueta = edad >= 18 ? 'adulto' : 'menor';
const activo = a && b || c;
```

### Control de flujo

```js
if (x > 0) { ... } else if (x === 0) { ... } else { ... }

switch (opcion) {
  case 'a': ...; break;
  default: ...
}

for (let i = 0; i < 5; i++) { ... }
for (const item of lista) { ... }        // valores de un iterable
for (const clave in objeto) { ... }      // claves de un objeto
while (condicion) { ... }
```

### Funciones

```js
function sumar(a, b) { return a + b; }
const restar = (a, b) => a - b;
const saludar = (nombre = 'mundo') => `Hola ${nombre}`;
const total = (...numeros) => numeros.reduce((acc, n) => acc + n, 0);
```

- Son valores: se pueden pasar como argumento y devolver (*first-class*).
- **Closure**: una función recuerda las variables del ámbito donde se creó.

```js
function crearContador() {
  let cuenta = 0;
  return () => ++cuenta;
}
const contar = crearContador();
contar(); // 1
contar(); // 2
```

### Objetos

```js
const persona = {
  nombre: 'Ana',
  edad: 30,
  saludar() { return `Hola, soy ${this.nombre}`; },
};

persona.nombre;           // acceso con punto
persona['edad'];          // acceso con corchetes
const { nombre, edad } = persona;      // desestructuración
const copia = { ...persona, edad: 31 }; // spread
Object.keys(persona);  Object.values(persona);  Object.entries(persona);
```

### Arreglos

```js
const nums = [1, 2, 3, 4];

nums.map(n => n * 2);              // [2, 4, 6, 8]   transforma
nums.filter(n => n % 2 === 0);     // [2, 4]          filtra
nums.find(n => n > 2);             // 3               primer match
nums.some(n => n > 3);             // true
nums.every(n => n > 0);            // true
nums.reduce((acc, n) => acc + n, 0); // 10            acumula
nums.includes(3);                  // true
[...nums, 5];                      // nuevo arreglo con 5 al final
nums.slice(1, 3);                  // [2, 3]  (no muta)
nums.sort((a, b) => a - b);        // ordena (¡muta!)
```

`map`, `filter` y `reduce` **no mutan** el original; `push`, `pop`, `splice`, `sort` **sí**.

### `this`

- En métodos, apunta al objeto que los llama.
- Las **arrow functions** no tienen `this` propio; usan el del ámbito donde se definieron.
- `bind`, `call` y `apply` fijan `this` manualmente.

### Clases

```js
class Cuenta {
  #saldo = 0;                       // campo privado

  constructor(titular) {
    this.titular = titular;
  }

  depositar(monto) {
    if (monto <= 0) throw new Error('Monto inválido');
    this.#saldo += monto;
  }

  get saldo() { return this.#saldo; }
}

class CuentaAhorro extends Cuenta {
  constructor(titular, interes) {
    super(titular);
    this.interes = interes;
  }
}
```

### Módulos

```js
// utilidades.js
export const PI = 3.14159;
export function doble(n) { return n * 2; }
export default function principal() {}

// app.js
import principal, { PI, doble } from './utilidades.js';
```

En HTML: `<script type="module" src="app.js"></script>`.

### Errores

```js
try {
  JSON.parse('{ inválido');
} catch (error) {
  console.error(error.message);
} finally {
  console.log('Siempre se ejecuta');
}

throw new Error('Algo salió mal');
```

### JSON

```js
const texto = JSON.stringify({ a: 1 });   // objeto → string
const objeto = JSON.parse('{"a":1}');     // string → objeto
```

## 5. El DOM

El **DOM** (*Document Object Model*) es el árbol de objetos que representa la página; JavaScript lo lee y modifica.

### Seleccionar

```js
document.getElementById('menu');
document.querySelector('.tarjeta');        // primero que coincide
document.querySelectorAll('li.activo');    // NodeList con todos
```

### Modificar

```js
const el = document.querySelector('#titulo');

el.textContent = 'Nuevo texto';            // seguro
el.innerHTML = '<b>Negrita</b>';           // ¡cuidado con XSS si viene del usuario!
el.setAttribute('data-id', '5');
el.dataset.id;                             // "5"
el.style.color = 'red';

el.classList.add('activo');
el.classList.remove('activo');
el.classList.toggle('activo');
el.classList.contains('activo');
```

### Crear y eliminar

```js
const li = document.createElement('li');
li.textContent = 'Nuevo item';
document.querySelector('ul').append(li);

li.remove();
```

### Recorrer el árbol

`parentElement`, `children`, `firstElementChild`, `nextElementSibling`, `closest('.selector')`.

## 6. Eventos

```js
const boton = document.querySelector('#guardar');

boton.addEventListener('click', (evento) => {
  console.log('Click en', evento.target);
});
```

| Categoría | Eventos |
|---|---|
| Ratón | `click`, `dblclick`, `mouseover`, `mouseout` |
| Teclado | `keydown`, `keyup` |
| Formulario | `submit`, `input`, `change`, `focus`, `blur` |
| Documento | `DOMContentLoaded`, `load`, `scroll`, `resize` |

- **Formularios**: `evento.preventDefault()` evita el envío por defecto.

```js
form.addEventListener('submit', (e) => {
  e.preventDefault();
  const datos = Object.fromEntries(new FormData(form));
  console.log(datos);
});
```

- **Propagación (*bubbling*)**: el evento sube del elemento hijo hacia sus ancestros. `stopPropagation()` lo detiene.
- **Delegación de eventos**: un único listener en el padre para muchos hijos, incluso los creados después.

```js
document.querySelector('ul').addEventListener('click', (e) => {
  const item = e.target.closest('li');
  if (item) console.log('Item', item.dataset.id);
});
```

## 7. Asincronía y peticiones HTTP

JavaScript ejecuta en **un solo hilo**. Las operaciones lentas (red, temporizadores) son asíncronas y se gestionan con el **event loop**.

```js
setTimeout(() => console.log('después de 1s'), 1000);
setInterval(() => console.log('cada 1s'), 1000);
```

### Promesas y `async/await`

```js
async function cargarCuentas() {
  try {
    const respuesta = await fetch('http://localhost:8080/api/cuentas');
    if (!respuesta.ok) throw new Error(`HTTP ${respuesta.status}`);
    const cuentas = await respuesta.json();
    return cuentas;
  } catch (error) {
    console.error('Fallo la petición:', error);
  }
}
```

- `fetch` **no lanza error con 404 o 500**; hay que revisar `respuesta.ok`.
- Varias en paralelo: `await Promise.all([fetch(a), fetch(b)])`.

### Enviar datos

```js
await fetch('http://localhost:8080/api/cuentas', {
  method: 'POST',
  headers: { 'Content-Type': 'application/json' },
  body: JSON.stringify({ titular: 'Ana', saldo: 100 }),
});
```

### CORS

El navegador bloquea peticiones a otro origen (dominio, puerto o protocolo) salvo que el servidor lo permita con cabeceras `Access-Control-Allow-*`. En Spring se configura con `@CrossOrigin` o de forma global.

## 8. Almacenamiento en el navegador

| Mecanismo | Duración | Uso típico |
|---|---|---|
| `localStorage` | Persistente | Preferencias, tema |
| `sessionStorage` | Hasta cerrar la pestaña | Datos temporales |
| Cookies | Configurable; se envían al servidor | Sesión (con `HttpOnly`) |
| IndexedDB | Persistente, grande | Datos estructurados |

```js
localStorage.setItem('tema', 'oscuro');
localStorage.getItem('tema');                        // "oscuro"
localStorage.setItem('usuario', JSON.stringify({ nombre: 'Ana' }));
JSON.parse(localStorage.getItem('usuario'));
localStorage.removeItem('tema');
```

Solo guarda **strings**; no almacenes datos sensibles (contraseñas, tokens de larga vida).

## 9. Herramientas

- **Editor**: VS Code (extensiones: Live Server, Prettier, ESLint).
- **DevTools del navegador** (F12): pestañas *Elements*, *Console*, *Network*, *Sources*, *Application*.
- **Node.js y npm**: ejecutar JS fuera del navegador e instalar paquetes.
- **Git**: control de versiones.
- **Vite**: servidor de desarrollo y empaquetado para proyectos más grandes.
- **Prettier / ESLint**: formato y análisis del código.

## 10. Buenas prácticas

- HTML semántico y accesible; separar HTML, CSS y JS en archivos distintos.
- CSS: *mobile first*, variables, clases reutilizables, evitar IDs para estilos.
- JS: preferir `const`, `===`, funciones pequeñas y nombres descriptivos.
- Preferir `textContent` a `innerHTML` con datos externos para evitar **XSS**.
- Manejar errores en toda petición de red.
- Validar los datos en el cliente **y** en el servidor.
- No dejar secretos o claves en el código del frontend.

## 11. Errores comunes

| Problema | Causa / solución |
|---|---|
| `Cannot read properties of null` | El elemento no existe aún: usar `defer` o poner el script al final |
| El estilo no se aplica | Especificidad, selector mal escrito o ruta del CSS incorrecta |
| `margin: auto` no centra | El elemento necesita `width` definido o ser `display: block`/flex item |
| La página se recarga al enviar el form | Falta `e.preventDefault()` |
| `fetch` "funciona" con un 404 | No se revisó `respuesta.ok` |
| `undefined` tras `await` | Se olvidó `await` o `return` |
| Error de CORS | Configurar el backend para permitir el origen |
| `this` es `undefined` en un callback | Usar arrow function o `bind` |
| Elementos nuevos no reaccionan al evento | Usar delegación de eventos |
| Imágenes desbordan en móvil | Falta `max-width: 100%` o el `meta viewport` |

## 12. Ruta de aprendizaje

1. HTML: estructura, texto, enlaces, imágenes, formularios, semántica.
2. CSS: selectores, modelo de caja, tipografía y colores.
3. CSS: Flexbox, Grid y diseño responsivo.
4. JavaScript: variables, funciones, arreglos y objetos.
5. DOM y eventos: interactividad en la página.
6. Asincronía: `fetch`, `async/await` y consumo de una API.
7. Almacenamiento y módulos.
8. Proyectos prácticos (lista de tareas, calculadora, consumo de la API de cuentas).
9. Siguiente paso: **TypeScript** y **React** (`CONCEPTOS_REACT.md`).

## Recursos

- MDN Web Docs: <https://developer.mozilla.org/es/>
- JavaScript.info: <https://javascript.info/>
- CSS Tricks (Flexbox y Grid): <https://css-tricks.com/>
- Can I use (compatibilidad): <https://caniuse.com/>
