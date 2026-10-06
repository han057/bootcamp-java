import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import { Clientes } from './components/Clientes.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <App />
    <Clientes />
  </StrictMode>,
)
