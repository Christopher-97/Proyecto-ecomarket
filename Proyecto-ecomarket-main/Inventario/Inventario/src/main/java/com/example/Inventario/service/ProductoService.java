package com.ecomarket.inventario.service;

import com.ecomarket.inventario.exception.ProductoNotFoundException;
import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con ID: " + id));
    }

    @Transactional
    public Producto save(Producto producto) {
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Transactional
    public Producto update(Long id, Producto productoDetails) {
        Producto existingProducto = findById(id);
        actualizarCamposProducto(existingProducto, productoDetails);
        return productoRepository.save(existingProducto);
    }

    @Transactional
    public void deleteById(Long id) {
        Producto producto = findById(id);
        productoRepository.delete(producto);
    }

    @Transactional(readOnly = true)
    public List<Producto> findByCategoria(String categoria) {
        return productoRepository.findByCategoria(categoria);
    }

    @Transactional(readOnly = true)
    public List<Producto> buscarProductosConStockBajo(int cantidadMinima) {
        return productoRepository.findByStockLessThan(cantidadMinima);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return productoRepository.existsById(id);
    }

    @Transactional
    public void disminuirStock(Long productoId, int cantidad) {
        validarCantidad(cantidad);
        Producto producto = findById(productoId);
        validarStockDisponible(producto, cantidad);
        producto.setStock(producto.getStock() - cantidad);
        productoRepository.save(producto);
    }

    @Transactional
    public void aumentarStock(Long productoId, int cantidad) {
        validarCantidad(cantidad);
        Producto producto = findById(productoId);
        producto.setStock(producto.getStock() + cantidad);
        productoRepository.save(producto);
    }

    // === Métodos privados de ayuda ===
    private void validarProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto no puede estar vacío");
        }
        if (producto.getPrecio() == null || producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if (producto.getCategoria() == null || producto.getCategoria().trim().isEmpty()) {
            throw new IllegalArgumentException("La categoría no puede estar vacía");
        }
    }

    private void actualizarCamposProducto(Producto existente, Producto actualizacion) {
        if (actualizacion.getNombre() != null) {
            existente.setNombre(actualizacion.getNombre());
        }
        if (actualizacion.getDescripcion() != null) {
            existente.setDescripcion(actualizacion.getDescripcion());
        }
        if (actualizacion.getPrecio() != null) {
            if (actualizacion.getPrecio() <= 0) {
                throw new IllegalArgumentException("El precio debe ser mayor que cero");
            }
            existente.setPrecio(actualizacion.getPrecio());
        }
        if (actualizacion.getStock() != null) {
            if (actualizacion.getStock() < 0) {
                throw new IllegalArgumentException("El stock no puede ser negativo");
            }
            existente.setStock(actualizacion.getStock());
        }
        if (actualizacion.getCategoria() != null) {
            existente.setCategoria(actualizacion.getCategoria());
        }
    }

    private void validarCantidad(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser positiva");
        }
    }

    private void validarStockDisponible(Producto producto, int cantidad) {
        if (producto.getStock() < cantidad) {
            throw new IllegalStateException(
                String.format("Stock insuficiente para %s (Stock actual: %d, Se requieren: %d)",
                    producto.getNombre(), producto.getStock(), cantidad)
            );
        }
    }
}