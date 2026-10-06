import { render, screen } from '@testing-library/react'
import userEvent from '@testing-library/user-event'
import App from './App'

describe('App', () => {
  it('agrega, completa y elimina tareas', async () => {
    const user = userEvent.setup()
    render(<App />)

    await user.type(screen.getByLabelText('Nueva tarea'), 'Aprender React')
    await user.click(screen.getByRole('button', { name: 'Agregar' }))
    expect(screen.getByText('Aprender React')).toBeInTheDocument()
    expect(screen.getByText('1 tarea(s) pendiente(s)')).toBeInTheDocument()

    await user.click(screen.getByRole('checkbox'))
    expect(screen.getByText('0 tarea(s) pendiente(s)')).toBeInTheDocument()

    await user.click(screen.getByRole('button', { name: 'Pendientes' }))
    expect(screen.queryByText('Aprender React')).not.toBeInTheDocument()

    await user.click(screen.getByRole('button', { name: 'Todas' }))
    await user.click(screen.getByRole('button', { name: 'Eliminar Aprender React' }))
    expect(screen.queryByText('Aprender React')).not.toBeInTheDocument()
  })

  it('ignora títulos vacíos', async () => {
    const user = userEvent.setup()
    render(<App />)
    await user.click(screen.getByRole('button', { name: 'Agregar' }))
    expect(screen.queryByRole('listitem')).not.toBeInTheDocument()
  })
})
