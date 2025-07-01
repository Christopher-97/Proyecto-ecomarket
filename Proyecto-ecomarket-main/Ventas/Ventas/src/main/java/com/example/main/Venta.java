package com.ecomarket.ventas.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ventas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long clienteId;
    
    @Column(nullable = false)
    private LocalDateTime fechaVenta;
    
    @Column(nullable = false)
    private Double total;
    
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "venta_id")
    private List<ItemVenta> items;
    
    @PrePersist
    protected void onCreate() {
        this.fechaVenta = LocalDateTime.now();
    }
}