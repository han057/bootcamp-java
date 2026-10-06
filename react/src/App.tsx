import { TodoFilters } from './components/TodoFilters'
import { TodoForm } from './components/TodoForm'
import { TodoItem } from './components/TodoItem'
import { useTodos } from './hooks/useTodos'

export default function App() {
  const { todos, filter, setFilter, pending, addTodo, toggleTodo, removeTodo } = useTodos()

  return (
    <main className="app">
      <h1>Lista de tareas</h1>
      <TodoForm onAdd={addTodo} />
      <TodoFilters current={filter} onChange={setFilter} />
      <ul>
        {todos.map((todo) => (
          <TodoItem key={todo.id} todo={todo} onToggle={toggleTodo} onRemove={removeTodo} />
        ))}
      </ul>
      <p className="counter">{pending} tarea(s) pendiente(s)</p>
    </main>
  )
}
