package com.ecomarket.inventario.controller;

import com.ecomarket.inventario.model.Producto;
import com.ecomarket.inventario.service.ProductoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Test
    void listarProductos_RetornaLista() throws Exception {
        Producto producto = new Producto();
        producto.setNombre("Teclado");
        
        given(productoService.findAll()).willReturn(Collections.singletonList(producto));
        
        mockMvc.perform(get("/api/productos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Teclado"));
    }

    @Test
    void crearProducto_Valido_RetornaCreated() throws Exception {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Mouse");
        
        given(productoService.save(any())).willReturn(producto);
        
        mockMvc.perform(post("/api/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"nombre\":\"Mouse\",\"precio\":25.99,\"stock\":50,\"categoria\":\"Periféricos\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Mouse"));
    }
}