import { useMemo, useState } from 'react'
import type { Filter, Todo } from '../types/todo'

export function useTodos(initial: Todo[] = []) {
  const [todos, setTodos] = useState<Todo[]>(initial)
  const [filter, setFilter] = useState<Filter>('all')

  const addTodo = (title: string) => {
    const clean = title.trim()
    if (!clean) return
    setTodos((prev) => [...prev, { id: Date.now() + prev.length, title: clean, completed: false }])
  }

  const toggleTodo = (id: number) =>
    setTodos((prev) => prev.map((t) => (t.id === id ? { ...t, completed: !t.completed } : t)))

  const removeTodo = (id: number) => setTodos((prev) => prev.filter((t) => t.id !== id))

  const visibleTodos = useMemo(
    () =>
      todos.filter((t) =>
        filter === 'all' ? true : filter === 'active' ? !t.completed : t.completed,
      ),
    [todos, filter],
  )

  const pending = todos.filter((t) => !t.completed).length

  return { todos: visibleTodos, filter, setFilter, pending, addTodo, toggleTodo, removeTodo }
}
