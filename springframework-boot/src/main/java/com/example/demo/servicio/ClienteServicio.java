package com.example.demo.servicio;

import com.example.demo.datos.ClienteRepository;
import com.example.demo.entidades.Cliente;
import com.example.demo.exceptions.EntidadNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServicio {

    private final ClienteRepository clienteRepository;

    public List<Cliente> obtenerTodos() {
        return clienteRepository.findAll();
    }

    public Cliente obtenerPorId(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new EntidadNoEncontradaException("Cliente no encontrado"));
    }

    public Cliente guardar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    public Cliente actualizar(Long id, Cliente clienteActualizado) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNombre(clienteActualizado.getNombre());
                    cliente.setApellido(clienteActualizado.getApellido());
                    return clienteRepository.save(cliente);
                })
                .orElseThrow(() -> new EntidadNoEncontradaException("Cliente no encontrado"));
    }

    public void eliminar(Long id) {
        clienteRepository.deleteById(id);
    }
}
