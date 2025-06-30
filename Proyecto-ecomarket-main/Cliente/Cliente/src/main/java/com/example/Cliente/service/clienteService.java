package com.example.Cliente.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Cliente.model.Cliente;
import com.example.Cliente.repository.clienteRepository;

@Service
public class clienteService {
    @Autowired
    private clienteRepository clienteRepository;

    public List<Cliente> getClientes(){
        return clienteRepository.obtenerCliente();
    }

    public Cliente burcarRun (int run){
        return clienteRepository.burcarRun(run);
    }

    public Cliente burcarId (int id){
        return clienteRepository.burcarId(id);
    }

    public Cliente guardarCliente (Cliente cliente){
        return clienteRepository.guardarCliente(cliente);
    }

    public Cliente actualizarCliente (Cliente cliente){
        return clienteRepository.actualizarCliente(cliente);
    }

    public String eliminar (int run){
        clienteRepository.eliminar(run);
        return "Cliente eliminado";
    }



}
