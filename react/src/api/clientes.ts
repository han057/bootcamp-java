import type { Cliente, ClienteInput } from '../types/cliente'

// En desarrollo Vite redirige /api al backend Spring (ver vite.config.ts).
const BASE = '/api/clientes'

async function request<T>(url: string, init?: RequestInit): Promise<T> {
  const res = await fetch(url, {
    ...init,
    headers: { 'Content-Type': 'application/json', ...init?.headers },
  })
  if (!res.ok) throw new Error((await res.text()) || `Error ${res.status}`)
  return res.json() as Promise<T>
}

export const listarClientes = () => request<Cliente[]>(BASE)

export const crearCliente = (data: ClienteInput) =>
  request<Cliente>(BASE, { method: 'POST', body: JSON.stringify(data) })

export const actualizarCliente = (id: number, data: ClienteInput) =>
  request<Cliente>(`${BASE}/${id}`, { method: 'PUT', body: JSON.stringify(data) })
