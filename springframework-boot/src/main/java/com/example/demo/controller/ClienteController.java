package com.example.demo.controller;

import com.example.demo.datos.ClienteRepository;
import com.example.demo.entidades.Cliente;
import com.example.demo.servicio.ClienteServicio;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    final private ClienteServicio clienteServicio;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteServicio.obtenerTodos();
    }

    @PostMapping
    public Cliente createCliente(@RequestBody Cliente cliente) {
        return clienteServicio.guardar(cliente);
    }

    @PutMapping("/{id}")
    public Cliente updateCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        return clienteServicio.actualizar(id, cliente);
    }

    @GetMapping("/{id}")
    public Cliente obtenerPorId(@PathVariable("id") Long id) {
        return clienteServicio.obtenerPorId(id);
    }


}
