package com.ecomarket.inventario.service;

import com.ecomarket.inventario.exception.ProductoNotFoundException;
import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Laptop HP");
        producto.setPrecio(1200.0);
        producto.setStock(10);
        producto.setCategoria("Electrónicos");
    }

    @Test
    void guardarProducto_Valido_RetornaProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        
        Producto resultado = productoService.save(producto);
        
        assertNotNull(resultado);
        assertEquals("Laptop HP", resultado.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void buscarPorId_Existente_RetornaProducto() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        
        Producto resultado = productoService.findById(1L);
        
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void buscarPorId_Inexistente_LanzaExcepcion() {
        when(productoRepository.findById(2L)).thenReturn(Optional.empty());
        
        assertThrows(ProductoNotFoundException.class, () -> {
            productoService.findById(2L);
        });
    }

    @Test
    void actualizarProducto_Valido_RetornaActualizado() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);
        
        Producto actualizacion = new Producto();
        actualizacion.setNombre("Laptop Actualizada");
        
        Producto resultado = productoService.update(1L, actualizacion);
        
        assertEquals("Laptop Actualizada", resultado.getNombre());
    }
}