import type { Filter } from '../types/todo'

interface Props {
  current: Filter
  onChange: (filter: Filter) => void
}

const FILTERS: { value: Filter; label: string }[] = [
  { value: 'all', label: 'Todas' },
  { value: 'active', label: 'Pendientes' },
  { value: 'completed', label: 'Completadas' },
]

export function TodoFilters({ current, onChange }: Props) {
  return (
    <div className="filters">
      {FILTERS.map(({ value, label }) => (
        <button
          key={value}
          className={current === value ? 'active' : ''}
          aria-pressed={current === value}
          onClick={() => onChange(value)}
        >
          {label}
        </button>
      ))}
    </div>
  )
}
