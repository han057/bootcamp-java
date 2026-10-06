import { useState, type FormEvent } from 'react'

interface Props {
  onAdd: (title: string) => void
}

export function TodoForm({ onAdd }: Props) {
  const [title, setTitle] = useState('')

  const handleSubmit = (e: FormEvent) => {
    e.preventDefault()
    onAdd(title)
    setTitle('')
  }

  return (
    <form onSubmit={handleSubmit} className="todo-form">
      <input
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        placeholder="Nueva tarea..."
        aria-label="Nueva tarea"
      />
      <button type="submit">Agregar</button>
    </form>
  )
}
