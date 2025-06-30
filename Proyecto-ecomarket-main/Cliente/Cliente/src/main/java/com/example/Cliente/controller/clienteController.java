package com.example.Cliente.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Cliente.model.Cliente;
import com.example.Cliente.service.clienteService;

@RestController
@RequestMapping("/Cliente")
public class clienteController {

    @Autowired
    private clienteService clienteService;

    // localhost:8080/Cliente
    @GetMapping
    public List<Cliente> listaClientes(){
        return clienteService.getClientes();
    }

    // localhost:8080/Cliente/guardar
    @PostMapping 
    public Cliente guardarCliente(@RequestBody Cliente cliente){
        return clienteService.guardarCliente(cliente);
    }

    // localhost:8080/Cliente/BuscarRun/{id}
    @GetMapping
    public Cliente burcarRun (@PathVariable int id){
        return clienteService.burcarRun(id);
    }

    // localhost:8080/Cliente/BuscarId/{id}
    @GetMapping
    public Cliente burcarId (@PathVariable int id){
        return clienteService.burcarId(id);
    }

    // localhost:8080/Cliente/Actualizar/{id}
    @PutMapping
    public Cliente actualizarCliente(@PathVariable int id,@RequestBody Cliente cliente){
        return clienteService.actualizarCliente(cliente);
    }

    // localhost:8080/Cliente/Eliminar/{id}
    @DeleteMapping
    public String eliminar(@PathVariable int id){
        return clienteService.eliminar(id);
    }


}
