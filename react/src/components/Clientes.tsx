import { useEffect, useState, type FormEvent } from 'react'
import { actualizarCliente, crearCliente, listarClientes } from '../api/clientes'
import type { Cliente } from '../types/cliente'

export function Clientes() {
  const [clientes, setClientes] = useState<Cliente[]>([])
  const [error, setError] = useState<string | null>(null)
  const [editando, setEditando] = useState<Cliente | null>(null)
  const [nombre, setNombre] = useState('')
  const [apellido, setApellido] = useState('')

  useEffect(() => {
    listarClientes()
      .then(setClientes)
      .catch((e: Error) => setError(e.message))
  }, [])

  const limpiar = () => {
    setEditando(null)
    setNombre('')
    setApellido('')
  }

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault()
    setError(null)
    const data = { nombre: nombre.trim(), apellido: apellido.trim() }
    try {
      if (editando) {
        const actualizado = await actualizarCliente(editando.id, data)
        setClientes((prev) => prev.map((c) => (c.id === actualizado.id ? actualizado : c)))
      } else {
        const creado = await crearCliente(data)
        setClientes((prev) => [...prev, creado])
      }
      limpiar()
    } catch (err) {
      setError((err as Error).message)
    }
  }

  const editar = (c: Cliente) => {
    setEditando(c)
    setNombre(c.nombre)
    setApellido(c.apellido)
  }

  return (
    <section className="app">
      <h1>Clientes</h1>
      <form onSubmit={handleSubmit} className="todo-form">
        <input
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          placeholder="Nombre"
          aria-label="Nombre"
          required
        />
        <input
          value={apellido}
          onChange={(e) => setApellido(e.target.value)}
          placeholder="Apellido"
          aria-label="Apellido"
          required
        />
        <button type="submit">{editando ? 'Guardar' : 'Agregar'}</button>
        {editando && (
          <button type="button" onClick={limpiar}>
            Cancelar
          </button>
        )}
      </form>
      {error && <p role="alert">{error}</p>}
      <ul>
        {clientes.map((c) => (
          <li key={c.id}>
            <span>
              {c.nombre} {c.apellido}
            </span>
            <button onClick={() => editar(c)} aria-label={`Editar ${c.nombre}`}>
              Editar
            </button>
          </li>
        ))}
      </ul>
    </section>
  )
}
