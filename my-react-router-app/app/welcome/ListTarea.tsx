export interface Task {
    id: number;
    name: string;
    completed: boolean;
}

export const ListTarea = 
    ({ tasks }: { tasks: Task[] }) => {

    return (
        <div>
            <h3>Lista de Tareas</h3>
            <ul>
                {tasks.map((task) => (
                    <li key={task.id}>{task.name}</li>
                ))}
            </ul>
        </div>
    );
}