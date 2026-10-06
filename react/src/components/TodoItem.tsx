import type { Todo } from '../types/todo'

interface Props {
  todo: Todo
  onToggle: (id: number) => void
  onRemove: (id: number) => void
}

export function TodoItem({ todo, onToggle, onRemove }: Props) {
  return (
    <li className={todo.completed ? 'done' : ''}>
      <label>
        <input type="checkbox" checked={todo.completed} onChange={() => onToggle(todo.id)} />
        <span>{todo.title}</span>
      </label>
      <button onClick={() => onRemove(todo.id)} aria-label={`Eliminar ${todo.title}`}>
        ✕
      </button>
    </li>
  )
}
