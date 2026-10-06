# Conceptos necesarios para trabajar con React

Guía de referencia para el bootcamp. El proyecto usa **Vite + TypeScript**, así que los ejemplos están en TSX.

## Índice

1. [Prerrequisitos de JavaScript](#1-prerrequisitos-de-javascript)
2. [Qué es React](#2-qué-es-react)
3. [Entorno y herramientas](#3-entorno-y-herramientas)
4. [JSX / TSX](#4-jsx--tsx)
5. [Componentes](#5-componentes)
6. [Props](#6-props)
7. [Estado con `useState`](#7-estado-con-usestate)
8. [Eventos](#8-eventos)
9. [Renderizado condicional y listas](#9-renderizado-condicional-y-listas)
10. [Efectos con `useEffect`](#10-efectos-con-useeffect)
11. [Otros hooks importantes](#11-otros-hooks-importantes)
12. [Formularios](#12-formularios)
13. [Comunicación con una API](#13-comunicación-con-una-api)
14. [Enrutamiento](#14-enrutamiento)
15. [Estado global](#15-estado-global)
16. [TypeScript con React](#16-typescript-con-react)
17. [Estilos](#17-estilos)
18. [Buenas prácticas](#18-buenas-prácticas)
19. [Testing](#19-testing)
20. [Errores comunes](#20-errores-comunes)
21. [Ruta de aprendizaje](#21-ruta-de-aprendizaje)

---

## 1. Prerrequisitos de JavaScript

Antes de React conviene dominar:

- `let` / `const`, scope y *hoisting*.
- **Arrow functions**: `const sumar = (a, b) => a + b;`
- **Desestructuración**: `const { nombre, edad } = persona;` y `const [a, b] = arreglo;`
- **Spread / rest**: `{ ...obj, campo: 1 }`, `[...lista, nuevo]`
- **Template literals**: `` `Hola ${nombre}` ``
- **Métodos de arreglos**: `map`, `filter`, `find`, `reduce`, `some`, `every`.
- **Módulos ES**: `import` / `export` (default y nombrados).
- **Optional chaining y nullish**: `usuario?.direccion?.calle`, `valor ?? 'defecto'`
- **Promesas y `async/await`**, `fetch`, manejo de errores con `try/catch`.
- **Closures** (clave para entender hooks y *stale state*).
- **Inmutabilidad**: copiar en lugar de mutar objetos y arreglos.

## 2. Qué es React

- Librería de JavaScript para construir **interfaces de usuario** a partir de **componentes**.
- Enfoque **declarativo**: describes *cómo debe verse la UI para un estado dado*, no los pasos para modificar el DOM.
- **Virtual DOM / reconciliación**: React compara el árbol nuevo con el anterior y actualiza solo lo que cambió.
- **Flujo de datos unidireccional**: los datos bajan de padres a hijos mediante props.
- **UI = f(estado)**: cuando el estado cambia, el componente se vuelve a renderizar.

## 3. Entorno y herramientas

| Herramienta | Para qué sirve |
|---|---|
| Node.js + npm | Entorno de ejecución y gestor de paquetes |
| Vite | Servidor de desarrollo y *bundler* |
| TypeScript | Tipado estático |
| ESLint / Oxlint | Análisis estático (este proyecto tiene `.oxlintrc.json`) |
| React DevTools | Extensión del navegador para inspeccionar componentes |

Comandos habituales (revisar `package.json` para los scripts exactos):

```bash
npm install        # Instalar dependencias
npm run dev        # Servidor de desarrollo
npm run build      # Build de producción (carpeta dist/)
npm run preview    # Previsualizar el build
```

Crear un proyecto nuevo:

```bash
npm create vite@latest mi-app -- --template react-ts
```

Estructura típica:

```
src/
  main.tsx        # Punto de entrada: monta <App /> en el DOM
  App.tsx         # Componente raíz
  components/     # Componentes reutilizables
  pages/          # Pantallas / rutas
  hooks/          # Hooks personalizados
  services/       # Llamadas a API
  types/          # Tipos e interfaces
```

## 4. JSX / TSX

JSX es una sintaxis que parece HTML pero es JavaScript. Reglas clave:

```tsx
const saludo = (
  <div className="caja">            {/* className, no class */}
    <h1>Hola, {nombre}</h1>          {/* expresiones JS entre llaves */}
    <label htmlFor="x">Nombre</label> {/* htmlFor, no for */}
    <input type="text" />            {/* etiquetas siempre cerradas */}
  </div>
);
```

- Un componente devuelve **un solo elemento raíz**; usa fragmentos `<>...</>` para no añadir nodos extra.
- Atributos en **camelCase**: `onClick`, `tabIndex`, `maxLength`.
- Estilos en línea con objeto: `style={{ color: 'red', fontSize: 14 }}`.
- Dentro de `{}` solo van **expresiones**, no sentencias (`if`, `for`).

## 5. Componentes

Un componente es una función que recibe props y devuelve JSX. El nombre empieza con **mayúscula**.

```tsx
function Tarjeta() {
  return <div className="tarjeta">Contenido</div>;
}

export default Tarjeta;
```

Se usa como etiqueta: `<Tarjeta />`.

Conceptos relacionados:

- **Composición**: componentes dentro de componentes; `children` para contenido anidado.
- **Componentes puros**: mismo input (props/estado) produce el mismo output, sin efectos secundarios durante el render.
- Hoy se usan **componentes de función con hooks**; los de clase son legado.

## 6. Props

Datos que un componente padre pasa a su hijo. Son **de solo lectura**.

```tsx
type SaludoProps = {
  nombre: string;
  edad?: number;               // opcional
};

function Saludo({ nombre, edad = 18 }: SaludoProps) {
  return <p>{nombre} tiene {edad} años</p>;
}

<Saludo nombre="Ana" edad={30} />
```

- **`children`**: contenido entre las etiquetas de apertura y cierre.
- Pasar **funciones** como props permite que el hijo notifique al padre (*lifting state up*).
- No mutar nunca las props.

```tsx
function Panel({ children }: { children: React.ReactNode }) {
  return <section className="panel">{children}</section>;
}
```

## 7. Estado con `useState`

El estado es memoria local del componente. Al cambiarlo, React re-renderiza.

```tsx
import { useState } from 'react';

function Contador() {
  const [cuenta, setCuenta] = useState(0);

  return (
    <button onClick={() => setCuenta(c => c + 1)}>
      Clicks: {cuenta}
    </button>
  );
}
```

Reglas importantes:

- **No mutar el estado directamente**; usa siempre el setter.
- Para objetos/arreglos crea copias:
  ```tsx
  setUsuario({ ...usuario, nombre: 'Luis' });
  setItems([...items, nuevo]);
  setItems(items.filter(i => i.id !== id));
  ```
- Si el nuevo valor depende del anterior, usa la **forma funcional**: `setCuenta(c => c + 1)`.
- Las actualizaciones son **asíncronas y agrupadas** (*batching*): el valor no cambia inmediatamente tras llamar al setter.
- Mantén el estado **mínimo**; lo que se puede calcular a partir de otro estado no debe ser estado.
- **Lifting state up**: si dos componentes necesitan el mismo dato, súbelo a su ancestro común.

## 8. Eventos

```tsx
function Formulario() {
  const manejarClick = (e: React.MouseEvent<HTMLButtonElement>) => {
    console.log('click', e.currentTarget);
  };

  const manejarCambio = (e: React.ChangeEvent<HTMLInputElement>) => {
    console.log(e.target.value);
  };

  return (
    <>
      <input onChange={manejarCambio} />
      <button onClick={manejarClick}>Enviar</button>
    </>
  );
}
```

- Se pasa **la función**, no su resultado: `onClick={manejar}` y **no** `onClick={manejar()}`.
- `e.preventDefault()` evita el comportamiento por defecto (por ejemplo, el envío de un `<form>`).
- `e.stopPropagation()` detiene la propagación (*bubbling*).

## 9. Renderizado condicional y listas

### Condicional

```tsx
{cargando && <Spinner />}
{error ? <Error mensaje={error} /> : <Datos datos={datos} />}
```

Cuidado con `{lista.length && <X />}`: si es `0` se pinta un `0`. Usa `lista.length > 0 && ...`.

### Listas

```tsx
<ul>
  {productos.map(p => (
    <li key={p.id}>{p.nombre}</li>
  ))}
</ul>
```

- **`key`** debe ser **estable y única** entre hermanos (usa el id de los datos).
- Evita usar el índice como `key` si la lista puede reordenarse, insertarse o eliminarse.

## 10. Efectos con `useEffect`

Sirve para **sincronizar el componente con sistemas externos**: peticiones, suscripciones, temporizadores, manipulación del DOM, `localStorage`.

```tsx
useEffect(() => {
  document.title = `Clicks: ${cuenta}`;
}, [cuenta]);                      // arreglo de dependencias
```

| Dependencias | Cuándo se ejecuta |
|---|---|
| Sin arreglo | Después de **cada** render |
| `[]` | Solo al **montar** |
| `[a, b]` | Al montar y cuando `a` o `b` cambian |

**Limpieza** (se ejecuta al desmontar o antes de re-ejecutar el efecto):

```tsx
useEffect(() => {
  const id = setInterval(() => setSegundos(s => s + 1), 1000);
  return () => clearInterval(id);
}, []);
```

Puntos clave:

- Incluye en las dependencias **todo lo reactivo** que uses dentro del efecto.
- **No uses efectos para calcular datos derivados**; calcúlalos durante el render.
- En desarrollo, `<StrictMode>` ejecuta los efectos dos veces para detectar errores de limpieza. Es normal.
- Los eventos del usuario se manejan en *event handlers*, no en efectos.

## 11. Otros hooks importantes

| Hook | Uso |
|---|---|
| `useRef` | Referencia mutable que no provoca re-render; acceso a elementos del DOM |
| `useMemo` | Memoriza un **valor** calculado costoso |
| `useCallback` | Memoriza una **función** (útil con `React.memo`) |
| `useContext` | Lee un contexto (evita *prop drilling*) |
| `useReducer` | Estado complejo con acciones; alternativa a `useState` |
| `useId` | Genera ids únicos y estables para accesibilidad |
| `useTransition` / `useDeferredValue` | Mantener la UI fluida en actualizaciones costosas |
| `useActionState` / `useOptimistic` | Formularios y actualizaciones optimistas (React 19) |
| `use` | Lee una promesa o contexto durante el render (React 19) |

```tsx
// useRef: enfocar un input
const inputRef = useRef<HTMLInputElement>(null);
<input ref={inputRef} />
<button onClick={() => inputRef.current?.focus()}>Enfocar</button>
```

```tsx
// useReducer
type Accion = { tipo: 'inc' } | { tipo: 'dec' };

function reducer(estado: number, accion: Accion) {
  switch (accion.tipo) {
    case 'inc': return estado + 1;
    case 'dec': return estado - 1;
  }
}

const [n, dispatch] = useReducer(reducer, 0);
dispatch({ tipo: 'inc' });
```

**Reglas de los hooks**:

1. Llamarlos solo en el **nivel superior** del componente (no dentro de `if`, bucles ni funciones anidadas).
2. Llamarlos solo desde **componentes de función** o **hooks personalizados**.

### Hooks personalizados

Funciones que empiezan con `use` y encapsulan lógica reutilizable.

```tsx
function useLocalStorage<T>(clave: string, inicial: T) {
  const [valor, setValor] = useState<T>(() => {
    const guardado = localStorage.getItem(clave);
    return guardado ? (JSON.parse(guardado) as T) : inicial;
  });

  useEffect(() => {
    localStorage.setItem(clave, JSON.stringify(valor));
  }, [clave, valor]);

  return [valor, setValor] as const;
}
```

## 12. Formularios

### Componente controlado (el estado manda)

```tsx
function Login() {
  const [email, setEmail] = useState('');

  const enviar = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    console.log(email);
  };

  return (
    <form onSubmit={enviar}>
      <input value={email} onChange={e => setEmail(e.target.value)} />
      <button type="submit">Entrar</button>
    </form>
  );
}
```

### Componente no controlado

Se lee el valor con `ref` o `FormData` al enviar.

```tsx
const datos = new FormData(e.currentTarget);
const email = datos.get('email');
```

Para formularios grandes con validación existen **React Hook Form** (con **Zod** para esquemas).

## 13. Comunicación con una API

Patrón básico con `fetch` (por ejemplo, contra el backend Spring del bootcamp):

```tsx
type Cuenta = { id: number; titular: string; saldo: number };

function ListaCuentas() {
  const [cuentas, setCuentas] = useState<Cuenta[]>([]);
  const [cargando, setCargando] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const controlador = new AbortController();

    fetch('http://localhost:8080/api/cuentas', { signal: controlador.signal })
      .then(r => {
        if (!r.ok) throw new Error(`HTTP ${r.status}`);
        return r.json() as Promise<Cuenta[]>;
      })
      .then(setCuentas)
      .catch(e => {
        if (e.name !== 'AbortError') setError(e.message);
      })
      .finally(() => setCargando(false));

    return () => controlador.abort();
  }, []);

  if (cargando) return <p>Cargando...</p>;
  if (error) return <p>Error: {error}</p>;
  return <ul>{cuentas.map(c => <li key={c.id}>{c.titular}: {c.saldo}</li>)}</ul>;
}
```

Siempre manejar los **tres estados**: cargando, error y éxito.

Conceptos asociados:

- **CORS**: el backend debe permitir el origen del frontend (en Spring: `@CrossOrigin` o configuración global).
- **Variables de entorno en Vite**: se definen en `.env` con prefijo `VITE_` y se leen con `import.meta.env.VITE_API_URL`.
- **Librerías de datos**: **TanStack Query** o **SWR** gestionan caché, reintentos y estados de carga, y evitan escribir `useEffect` + `fetch` a mano.
- **Axios** como alternativa a `fetch`.

## 14. Enrutamiento

React no trae router; se usa **React Router** (o **TanStack Router**).

```tsx
import { BrowserRouter, Routes, Route, Link, useParams } from 'react-router-dom';

function App() {
  return (
    <BrowserRouter>
      <nav><Link to="/">Inicio</Link> | <Link to="/cuentas/1">Cuenta 1</Link></nav>
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="/cuentas/:id" element={<DetalleCuenta />} />
        <Route path="*" element={<NoEncontrado />} />
      </Routes>
    </BrowserRouter>
  );
}

function DetalleCuenta() {
  const { id } = useParams();
  return <p>Cuenta {id}</p>;
}
```

Conceptos: rutas anidadas, parámetros, `useNavigate`, rutas protegidas, *lazy loading* con `React.lazy` + `Suspense`.

## 15. Estado global

Orden recomendado para elegir:

1. **Estado local** (`useState`) siempre que sea posible.
2. **Subir el estado** al ancestro común.
3. **Context API** para datos poco cambiantes (tema, usuario autenticado, idioma).
4. **Librería de estado**: Zustand, Redux Toolkit o Jotai para estado compartido complejo.
5. **Estado del servidor** (datos remotos): TanStack Query, no Redux.

```tsx
const TemaContext = createContext<'claro' | 'oscuro'>('claro');

function App() {
  return (
    <TemaContext.Provider value="oscuro">
      <Hijo />
    </TemaContext.Provider>
  );
}

function Hijo() {
  const tema = useContext(TemaContext);
  return <p>Tema: {tema}</p>;
}
```

## 16. TypeScript con React

```tsx
// Props
type BotonProps = {
  texto: string;
  onClick: () => void;
  variante?: 'primario' | 'secundario';
};

// Estado tipado
const [usuario, setUsuario] = useState<Usuario | null>(null);

// Eventos
const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {};

// children
type Props = { children: React.ReactNode };

// Props de elementos nativos
type InputProps = React.ComponentPropsWithoutRef<'input'>;
```

- Usa `type` o `interface` para modelar los datos de la API.
- Evita `any`; prefiere `unknown` y reduce el tipo.
- Tipos de unión (`'a' | 'b'`) para variantes y estados.
- Con TS estricto: `strict: true` en `tsconfig.app.json`.

## 17. Estilos

| Opción | Notas |
|---|---|
| CSS plano / `index.css` | Simple, estilos globales |
| **CSS Modules** (`*.module.css`) | Estilos con alcance por componente |
| **Tailwind CSS** | Clases utilitarias |
| CSS-in-JS (styled-components, Emotion) | Estilos en JS |
| Librerías de UI (MUI, Chakra, shadcn/ui, Mantine) | Componentes ya diseñados |

Para clases condicionales: `className={`btn ${activo ? 'btn--activo' : ''}`}` o la utilidad `clsx`.

## 18. Buenas prácticas

- **Un componente, una responsabilidad**; extrae cuando crezca demasiado.
- Nombres: componentes en `PascalCase`, hooks en `useCamelCase`, archivos acordes al componente.
- **Separa presentación y lógica**: componentes visuales + hooks/servicios para datos.
- No mutar estado ni props.
- Mantener el estado lo más cerca posible de donde se usa.
- No optimices prematuramente (`useMemo`, `useCallback`, `React.memo`) sin medir antes.
- **Accesibilidad**: HTML semántico, `alt` en imágenes, `label` en inputs, navegación con teclado.
- Manejar siempre estados de **carga y error**.
- Usar **Error Boundaries** para fallos de render.
- No guardar secretos en el frontend; todo lo que llega al navegador es público.

## 19. Testing

- **Vitest** (compatible con Vite) o Jest como *test runner*.
- **React Testing Library**: prueba el comportamiento como lo ve el usuario.
- **Playwright / Cypress** para pruebas end-to-end.
- **MSW** para simular la API.

```tsx
import { render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';

test('incrementa el contador', async () => {
  render(<Contador />);
  await userEvent.click(screen.getByRole('button'));
  expect(screen.getByText(/Clicks: 1/)).toBeInTheDocument();
});
```

## 20. Errores comunes

| Problema | Causa / solución |
|---|---|
| Bucle infinito de renders | Llamar a un setter directamente en el render o en un efecto sin dependencias correctas |
| `Each child in a list should have a unique "key"` | Falta `key` en `map` |
| El estado "no se actualiza" | Leer el valor justo después del setter; usar la forma funcional o un efecto |
| La UI no refleja el cambio | Se mutó el estado en lugar de crear una copia |
| `Cannot read properties of undefined` | Datos aún no cargados; validar con `?.` o renderizado condicional |
| Efecto con datos obsoletos (*stale closure*) | Dependencias incompletas en `useEffect` |
| Funciona en dev pero se duplica | `StrictMode` ejecuta efectos dos veces; falta limpieza |
| `onClick={fn()}` se ejecuta al render | Debe ser `onClick={fn}` o `onClick={() => fn()}` |
| Error de CORS | Configurar el backend para permitir el origen del frontend |

## 21. Ruta de aprendizaje

1. JavaScript moderno (sección 1).
2. JSX, componentes y props.
3. `useState` y eventos.
4. Listas, condicionales y formularios.
5. `useEffect` y consumo de API.
6. React Router.
7. Hooks personalizados, Context y `useReducer`.
8. TypeScript aplicado a React.
9. TanStack Query / estado global.
10. Testing y despliegue (`npm run build`, servir `dist/`).
11. Frameworks sobre React: **Next.js**, **React Router framework mode**, Remix.

## Recursos

- Documentación oficial: <https://react.dev>
- Referencia de TypeScript con React: <https://react.dev/learn/typescript>
- React Router: <https://reactrouter.com>
- TanStack Query: <https://tanstack.com/query>
- Vite: <https://vite.dev>
