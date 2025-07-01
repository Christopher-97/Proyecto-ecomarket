package com.example.Inventario.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Inventario {
    private int id;
    private String nombre;
    private String descripcion;
    private int fechaVencimiento;
    private int precio;
}
