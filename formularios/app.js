const ciudadesPorPais = {
  co: ['Bogotá', 'Medellín', 'Cali', 'Barranquilla'],
  mx: ['Ciudad de México', 'Guadalajara', 'Monterrey'],
  ar: ['Buenos Aires', 'Córdoba', 'Rosario'],
  es: ['Madrid', 'Barcelona', 'Valencia'],
  cl: ['Santiago', 'Valparaíso', 'Concepción'],
};

const form = document.querySelector('#formulario');
const selectPais = document.querySelector('#pais');
const selectCiudad = document.querySelector('#ciudad');
const comentarios = document.querySelector('#comentarios');
const contador = document.querySelector('#contador');
const resultado = document.querySelector('#resultado');
const salida = document.querySelector('#salida');

// Select dependiente: las ciudades cambian según el país
selectPais.addEventListener('change', () => {
  const ciudades = ciudadesPorPais[selectPais.value] ?? [];
  selectCiudad.replaceChildren();

  if (ciudades.length === 0) {
    selectCiudad.append(new Option('Primero elige un país', ''));
    selectCiudad.disabled = true;
    return;
  }

  selectCiudad.append(new Option('Selecciona una ciudad', ''));
  ciudades.forEach((ciudad) => selectCiudad.append(new Option(ciudad, ciudad)));
  selectCiudad.disabled = false;
});

// Contador de caracteres del textarea
comentarios.addEventListener('input', () => {
  contador.textContent = comentarios.value.length;
});

// Reglas de validación: devuelven un mensaje de error o '' si es válido
const validaciones = {
  nombre: (f) => (f.nombre.value.trim().length < 3 ? 'Ingresa al menos 3 caracteres.' : ''),
  email: (f) => (f.email.validity.valid ? '' : 'Ingresa un correo válido.'),
  telefono: (f) =>
    f.telefono.value && !f.telefono.validity.valid ? 'Usa entre 7 y 10 dígitos.' : '',
  nacimiento: (f) => {
    if (!f.nacimiento.value) return 'Selecciona tu fecha de nacimiento.';
    const edad = (Date.now() - new Date(f.nacimiento.value)) / (365.25 * 24 * 3600 * 1000);
    return edad < 16 ? 'Debes tener al menos 16 años.' : '';
  },
  password: (f) => (f.password.value.length < 8 ? 'Mínimo 8 caracteres.' : ''),
  pais: (f) => (f.pais.value ? '' : 'Selecciona un país.'),
  programa: (f) => (f.programa.value ? '' : 'Selecciona un programa.'),
  nivel: (f) => (f.nivel.value ? '' : 'Selecciona tu nivel.'),
  terminos: (f) => (f.terminos.checked ? '' : 'Debes aceptar los términos.'),
};

function mostrarError(campo, mensaje) {
  const contenedorError = document.querySelector(`#error-${campo}`);
  if (contenedorError) contenedorError.textContent = mensaje;

  const control = form.elements[campo];
  // Los radios devuelven un RadioNodeList; solo marcamos controles individuales
  if (control instanceof HTMLElement) {
    control.classList.toggle('invalido', Boolean(mensaje));
    control.classList.toggle('valido', !mensaje && Boolean(control.value));
  }
}

function validarCampo(campo) {
  const mensaje = validaciones[campo](form.elements);
  mostrarError(campo, mensaje);
  return mensaje === '';
}

function validarTodo() {
  // Se evalúan todos (sin cortocircuito) para mostrar todos los errores a la vez
  const resultados = Object.keys(validaciones).map(validarCampo);
  return resultados.every(Boolean);
}

// Validación en vivo al salir de un campo
Object.keys(validaciones).forEach((campo) => {
  const control = form.elements[campo];
  const eventos = control instanceof HTMLElement ? ['blur', 'change'] : ['change'];
  const destinos = control instanceof HTMLElement ? [control] : control;
  destinos.forEach((el) => eventos.forEach((ev) => el.addEventListener(ev, () => validarCampo(campo))));
});

// Construye un objeto con los datos del formulario
function leerDatos() {
  const datos = new FormData(form);
  const objeto = Object.fromEntries(datos);

  // Los campos con varios valores necesitan getAll
  objeto.horario = datos.getAll('horario');
  objeto.tecnologias = datos.getAll('tecnologias');
  objeto.boletin = form.elements.boletin.checked;
  objeto.terminos = form.elements.terminos.checked;
  delete objeto.password; // nunca mostrar la contraseña

  return objeto;
}

form.addEventListener('submit', (e) => {
  e.preventDefault();

  if (!validarTodo()) {
    form.querySelector('.invalido, .error:not(:empty)')?.scrollIntoView({ behavior: 'smooth', block: 'center' });
    return;
  }

  salida.textContent = JSON.stringify(leerDatos(), null, 2);
  resultado.hidden = false;
  resultado.scrollIntoView({ behavior: 'smooth' });
});

form.addEventListener('reset', () => {
  resultado.hidden = true;
  contador.textContent = '0';
  selectCiudad.replaceChildren(new Option('Primero elige un país', ''));
  selectCiudad.disabled = true;
  form.querySelectorAll('.invalido, .valido').forEach((el) => el.classList.remove('invalido', 'valido'));
  form.querySelectorAll('.error').forEach((el) => (el.textContent = ''));
});
