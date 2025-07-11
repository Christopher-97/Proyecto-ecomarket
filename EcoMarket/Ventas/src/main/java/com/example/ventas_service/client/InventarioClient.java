package com.example.ventas_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "inventario-client", url = "http://localhost:8080") // Ajusta la URL
public interface InventarioClient {
    
    @GetMapping("/productos/{id}")
    Producto getProducto(@PathVariable Long id);

@PutMapping("/productos/{id}/stock")
void actualizarStock(@PathVariable Long id, @RequestBody int nuevoStock);
}