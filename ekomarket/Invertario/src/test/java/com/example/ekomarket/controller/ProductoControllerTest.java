package com.example.ekomarket.controller;

import com.example.ekomarket.model.Producto;
import com.example.ekomarket.services.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

class ProductoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductoService productoService;

    @InjectMocks
    private ProductoController productoController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    void listarProductos_debeRetornarLista() throws Exception {
        Producto p1 = new Producto(1L, "Producto1", 10, 100.0);
        Producto p2 = new Producto(2L, "Producto2", 5, 50.0);
        when(productoService.listarProductos()).thenReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Producto1"));

        verify(productoService, times(1)).listarProductos();
    }

    @Test
    void crearProducto_stockValido() throws Exception {
        Producto p = new Producto(null, "ProductoNuevo", 10, 100.0);
        Producto pGuardado = new Producto(1L, "ProductoNuevo", 10, 100.0);
        when(productoService.crearProducto(any(Producto.class))).thenReturn(pGuardado);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.nombre").value("ProductoNuevo"));

        verify(productoService, times(1)).crearProducto(any(Producto.class));
    }

    @Test
    void crearProducto_stockNegativo() throws Exception {
        Producto p = new Producto(null, "ProductoNuevo", -5, 100.0);
        when(productoService.crearProducto(any(Producto.class))).thenThrow(new RuntimeException("El stock no puede ser negativo"));

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(p)))
                .andExpect(status().isBadRequest());

        verify(productoService, times(1)).crearProducto(any(Producto.class));
    }

    @Test
    void buscarPorId_productoExistente() throws Exception {
        Producto p = new Producto(1L, "Producto1", 10, 100.0);
        when(productoService.buscarPorId(1L)).thenReturn(p);

        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Producto1"));

        verify(productoService, times(1)).buscarPorId(1L);
    }

    @Test
    void buscarPorId_productoNoExistente() throws Exception {
        when(productoService.buscarPorId(1L)).thenThrow(new RuntimeException("Producto no encontrado"));

        mockMvc.perform(get("/productos/1"))
                .andExpect(status().isNotFound());

        verify(productoService, times(1)).buscarPorId(1L);
    }

    @Test
    void actualizarStock_valido() throws Exception {
        doNothing().when(productoService).actualizarStock(1L, 20);

        mockMvc.perform(put("/productos/1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("20"))
                .andExpect(status().isOk());

        verify(productoService, times(1)).actualizarStock(1L, 20);
    }

    @Test
    void actualizarStock_stockNegativo() throws Exception {
        doThrow(new RuntimeException("El stock no puede ser negativo")).when(productoService).actualizarStock(1L, -10);

        mockMvc.perform(put("/productos/1/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("-10"))
                .andExpect(status().isBadRequest());

        verify(productoService, times(1)).actualizarStock(1L, -10);
    }
}
