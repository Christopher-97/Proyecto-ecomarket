package com.example.Cliente.assembler;

import com.example.Cliente.controller.clienteController;
import com.example.Cliente.model.Cliente;

import ch.qos.logback.core.net.server.Client;

public class ClienteModelAssembler {
    public static Client assembler(Cliente cliente) {
        Client clienteModel = new ClienteModel();
        clienteModel.setId(cliente.getId());
        clienteModel.setNombre(Cliente.getNombre());
        clienteModel.setApPaterno(Cliente.getApPaterno());
        clienteModel.setApMaterno(Cliente.getApMaterno());
        clienteModel.setRun(Cliente.getRun());
        clienteModel.setMail(Cliente.getMail());
        clienteModel.setRol(Cliente.getRol());

        clienteModel.add(linkTo(methodOn(clienteController.class).buscarPorId(Cliente.getId())).withSelfRel());
        clienteModel.add(linkTo(methodOn(clienteController.class).listarUsuarios()).withRel("usuarios"));
        clienteModel.add(linkTo(methodOn(clienteController.class).eliminar(Cliente.getId())).withRel("eliminar"));
        clienteModel.add(linkTo(methodOn(clienteController.class).actualizar(Cliente.getId(), Cliente)).withRel("actualizar"));

        return clienteModel;
    }
    
}
