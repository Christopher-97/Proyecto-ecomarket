package com.ecomarket.inventario.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    private Double precio;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
    
    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;
    
    @Column(updatable = false)
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaActualizacion;
    
    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }
}