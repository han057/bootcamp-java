import { useState } from "react";
import type { Task } from "./ListTarea";


export const CrearTask = (
  { tasks, setTasks }: { tasks: Task[]; setTasks: React.Dispatch<React.SetStateAction<Task[]>> }
) => {
  const [nombreTarea, setNombreTarea] = useState("");
  const crearTarea = () => {
    if (nombreTarea.trim() === "") return;
    const nuevaTarea: Task = {
      id: tasks.length + 1,
      name: nombreTarea,
      completed: false,
    };
    setTasks([...tasks, nuevaTarea]);
    setNombreTarea("");
  };
  
  return (
    <div>
      <h3>Crear Tarea</h3>
      <form>
        <input type="text" 
        placeholder="Nombre de la tarea" 
        value={nombreTarea}
        onChange={(e) => setNombreTarea(e.target.value)} />
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