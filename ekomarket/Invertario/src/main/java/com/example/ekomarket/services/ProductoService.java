package com.example.ekomarket.services;

import com.example.ekomarket.model.Producto;
import com.example.ekomarket.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    // 1. Listar todos los productos
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    // 2. Buscar producto por ID
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    // 3. Crear producto (con validación de stock)
    public Producto crearProducto(Producto producto) {
        if (producto.getStock() < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }
        return productoRepository.save(producto);
    }

    // 4. Actualizar stock
    public void actualizarStock(Long id, int nuevoStock) {
        Producto producto = buscarPorId(id);
        if (nuevoStock < 0) {
            throw new RuntimeException("El stock no puede ser negativo");
        }
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
    }
}
