import { useState } from "react";
import type { Task } from "./ListTarea";

interface CrearTaskProps {
  tasks: Task[];
  createTaskHandler: (task: Task) => void;
}

export const CrearTask = ({ tasks, createTaskHandler }: CrearTaskProps) => {
  const [nombreTarea, setNombreTarea] = useState("");
  const crearTarea = () => {
    if (nombreTarea.trim() === "") return;
    const nuevaTarea: Task = {
      id: tasks.length + 1,
      name: nombreTarea,
      completed: false,
    };
    createTaskHandler(nuevaTarea);
    setNombreTarea("");
  };

  return (
    <div>
      <h3>Crear Tarea</h3>
      <form>
        <input type="text"
          placeholder="Nombre de la tarea"
          value={nombreTarea}
          onChange={(e) => {
            setNombreTarea(e.target.value); 
            e.preventDefault(); 
          }}
          onClick={(e) => e.stopPropagation()}
          onKeyDown={(e) => {
            if (e.key === "Enter") {
              crearTarea();
              e.preventDefault();
            }
          }}
        />
        <button onClick={(e) => {
          crearTarea();
          e.preventDefault();
        }}>
          Crear
        </button>
      </form>
    </div>
  );
};