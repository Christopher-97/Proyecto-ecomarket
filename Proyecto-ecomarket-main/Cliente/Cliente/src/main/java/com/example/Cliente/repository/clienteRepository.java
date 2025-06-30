package com.example.Cliente.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.Cliente.model.Cliente;

@Repository
public class clienteRepository {

    private List<Cliente> listaClientes = new ArrayList<>();


    public List<Cliente> obtenerCliente() {
        return listaClientes;
    }

    public Cliente burcarRun(int run) {
        for (Cliente cliente : listaClientes){
            if (cliente.getRun()==run){
                return cliente;
            }
        }
        return null;
    }

    public Cliente burcarId(int id) {
        for (Cliente cliente : listaClientes){
            if (cliente.getId()==id){
                return cliente;
            }
        }
        return null;
    }

    public Cliente guardarCliente(Cliente cli) {
        listaClientes.add(cli);
        return cli;
    }

    public Cliente actualizarCliente(Cliente cli){
        int id = 0;
        int idPosiicion = 0;

        for (int i = 0; i < listaClientes.size(); i++){
            if(listaClientes.get(i).getId() == cli.getId()){
                id = cli.getId();
                idPosiicion = i;
            }
        }

        Cliente cliente1 = new Cliente();
        cliente1.setContasenia(cli.getContasenia());
        cliente1.setNombre(cli.getNombre());
        cliente1.setRun(cli.getRun());
        cliente1.setTelefono(cli.getTelefono());
        cliente1.setId(id);

        listaClientes.set(idPosiicion, cliente1);
        return cliente1; 
    }

    public void eliminar(int run){
        Cliente cliente = burcarRun(run);
        if (cliente != null) {
            listaClientes.remove(cliente);
        }
    }
}

