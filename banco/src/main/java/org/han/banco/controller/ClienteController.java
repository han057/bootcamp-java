package org.han.banco.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    private ArrayList<Cliente> clientes = new ArrayList<>();
    private int idCounter = 1;

    @PostMapping
    public Cliente crearCliente(@RequestParam String nombre,
                                @RequestParam String email,
                                @RequestParam String telefono) {
        Cliente cliente = new Cliente(idCounter++, nombre, email, telefono);
        clientes.add(cliente);
        return cliente;
    }

    @GetMapping
    public List<Cliente> obtenerTodos() {
        return clientes;
    }

    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable int id) {
        Optional<Cliente> cliente = clientes.stream()
                .filter(c -> c.getId() == id)
                .findFirst();
        return cliente.orElse(null);
    }

    @PutMapping("/{id}")
    public Cliente actualizarCliente(@PathVariable int id,
                                     @RequestParam String nombre,
                                     @RequestParam String email,
                                     @RequestParam String telefono) {
        for (Cliente cliente : clientes) {
            if (cliente.getId() == id) {
                cliente.setNombre(nombre);
                cliente.setEmail(email);
                cliente.setTelefono(telefono);
                return cliente;
            }
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public boolean eliminarCliente(@PathVariable int id) {
        return clientes.removeIf(c -> c.getId() == id);
    }
}
