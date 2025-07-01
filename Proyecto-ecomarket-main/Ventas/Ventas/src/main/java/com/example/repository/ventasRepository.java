package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.main.Venta;

@Repository
public class ventasRepository {
    

    private List<Venta> listaVentas = new ArrayList<>();


    public List<Venta> obtenerVenta() {
        return listaVentas;
    }

    public Venta burcarIdVenta(int id) {
        for (Venta Venta : listaVentas){
            if (Venta.getId()==id){
                return Venta;
            }
        }
        return null;
    }

    public Venta guardar(Venta vent) {
        listaVentas.add(vent);
        return vent;
    }

    public Venta actualizarVenta(Venta vent){
        int id = 0;
        int idPosiicion = 0;

        for (int i = 0; 1 < listaVentas.size()1++){
            if(listaVentas.get(i).getId() == vent.getId()){
                id = vent.getId();
                idPosiicion = i;
            }
        }

        Venta venta1 = new Venta();
        venta1.setId(vent.getId());
        venta1.setCodProducto(vent.getCodProducto());
        venta1.setProducto(vent.getProducto());
        venta1.setDescripcion(vent.getDescripcion());
        venta1.setPrecio(vent.getPrecio());
        venta1.setFechaVenta(vent.getFechaVenta());

        listaVentas.set(idPosiicion, venta1);
        return venta1;

        
    }
}

