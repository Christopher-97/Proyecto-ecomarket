package com.example.ekomarket.services;

import com.example.ekomarket.model.Producto;
import com.example.ekomarket.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listarProductos_debeRetornarLista() {
        Producto p1 = new Producto(1L, "Producto1", 10, 100.0);
        Producto p2 = new Producto(2L, "Producto2", 5, 50.0);
        when(productoRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Producto> productos = productoService.listarProductos();

        assertEquals(2, productos.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void buscarPorId_productoExistente() {
        Producto p = new Producto(1L, "Producto1", 10, 100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));

        Producto resultado = productoService.buscarPorId(1L);

        assertEquals("Producto1", resultado.getNombre());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void buscarPorId_productoNoExistente() {
        when(productoRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.buscarPorId(1L);
        });

        assertEquals("Producto no encontrado", exception.getMessage());
        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void crearProducto_stockValido() {
        Producto p = new Producto(null, "ProductoNuevo", 10, 100.0);
        when(productoRepository.save(p)).thenReturn(new Producto(1L, "ProductoNuevo", 10, 100.0));

        Producto resultado = productoService.crearProducto(p);

        assertNotNull(resultado.getId());
        verify(productoRepository, times(1)).save(p);
    }

    @Test
    void crearProducto_stockNegativo() {
        Producto p = new Producto(null, "ProductoNuevo", -5, 100.0);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.crearProducto(p);
        });

        assertEquals("El stock no puede ser negativo", exception.getMessage());
        verify(productoRepository, never()).save(any());
    }

    @Test
    void actualizarStock_valido() {
        Producto p = new Producto(1L, "Producto1", 10, 100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));
        when(productoRepository.save(p)).thenReturn(p);

        productoService.actualizarStock(1L, 20);

        assertEquals(20, p.getStock());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, times(1)).save(p);
    }

    @Test
    void actualizarStock_stockNegativo() {
        Producto p = new Producto(1L, "Producto1", 10, 100.0);
        when(productoRepository.findById(1L)).thenReturn(Optional.of(p));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productoService.actualizarStock(1L, -10);
        });

        assertEquals("El stock no puede ser negativo", exception.getMessage());
        verify(productoRepository, times(1)).findById(1L);
        verify(productoRepository, never()).save(any());
    }
}
