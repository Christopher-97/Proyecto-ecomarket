package com.example.Cliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    private String nombre;
    private String contasenia;
    private int telefono;
    private int run;
    private int id;
    
}
