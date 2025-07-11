package com.example.ventas_service.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long productoId;  // ID del producto vendido (relación con Inventario)
    private int cantidad;
    private LocalDateTime fecha = LocalDateTime.now();
}