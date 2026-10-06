export interface Cliente {
  id: number
  nombre: string
  apellido: string
}

export type ClienteInput = Omit<Cliente, 'id'>
