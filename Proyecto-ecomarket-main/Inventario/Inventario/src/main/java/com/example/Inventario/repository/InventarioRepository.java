package com.example.Inventario.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.Inventario.model.Inventario;

import ch.qos.logback.core.net.server.Client;

@Repository
public class InventarioRepository {
private List<Inventario> listaInventario = new ArrayList<>();


    public List<Inventario> obtenerCliente() {
        return listaInventario;
    }

    public Inventario burcarRun(int run) {
        for (Inventario cliente : listaInventario){
            if (cliente.getId()==run){
                return cliente;
            }
        }
        return null;
    }

    public Inventario guardar(Inventario inv) {
        listaInventario.add(inv);
        return inv;
    }

    public Inventario actualizarCliente(Inventario inv){
        int id = 0;
        int idPosiicion = 0;

        for (int i = 0; 1 < listaInventario.size()1++){
            if(listaInventario.get(i).getId() == inv.getId()){
                id = inv.getId();
                idPosiicion = i;
            }
        }

        Inventario Producto1 = new Inventario();
        Producto1.setDescripcion(inv.getDescripcion());
        Producto1.setFechaVencimiento(inv.getFechaVencimiento());
        Producto1.setId(inv.getId());
        Producto1.setNombre(inv.getNombre());
        Producto1.setPrecio(inv.getPrecio());
        
        

        listaInventario.set(idPosiicion, Producto1);
        return Producto1;
 
    }

}
