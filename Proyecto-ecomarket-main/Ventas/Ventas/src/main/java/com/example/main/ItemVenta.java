package com.ecomarket.ventas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items_venta")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long productoId;
    
    @Column(nullable = false)
    private String nombreProducto;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = false)
    private Double precioUnitario;
    
    @Column(nullable = false)
    private Double subtotal;
}