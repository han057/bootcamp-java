import type { Route } from "./+types/home";
import { Welcome } from "../welcome/welcome";
import { Header } from "~/welcome/header";
import { CrearTask } from "~/welcome/creartask";
import { ListTarea, type Task } from "~/welcome/ListTarea";
import { useState } from "react";

export function meta({}: Route.MetaArgs) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Home() {

  const [tasks, setTasks] = useState([
    { id: 1, name: "Tarea 1", completed: false },
    { id: 2, name: "Tarea 2", completed: true },
    { id: 3, name: "Tarea 3", completed: false },
  ]);

  function createTaskHandler(task: Task) {
    //post to backend
    
    setTasks([...tasks, task]);
  }

  return <>
  <Header />
  <CrearTask
    tasks={tasks}
    createTaskHandler={createTaskHandler}
  />
  <ListTarea tasks={tasks} />
  </>;
}
