package com.example.ekomarket.controller;

import com.example.ekomarket.model.Producto;
import com.example.ekomarket.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoRepository productoRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setup() {
        productoRepository.deleteAll();
    }

    @Test
    public void testCrearYListarProducto() throws Exception {
        Producto producto = new Producto(null, "Producto Integracion", 15, 150.0);

        // Crear producto
        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is("Producto Integracion")))
                .andExpect(jsonPath("$.stock", is(15)))
                .andExpect(jsonPath("$.precio", is(150.0)));

        // Listar productos
        mockMvc.perform(get("/productos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].nombre", is("Producto Integracion")));
    }

    @Test
    public void testActualizarStock() throws Exception {
        Producto producto = new Producto(null, "Producto Stock", 10, 100.0);
        producto = productoRepository.save(producto);

        // Actualizar stock
        mockMvc.perform(put("/productos/" + producto.getId() + "/stock")
                .contentType(MediaType.APPLICATION_JSON)
                .content("20"))
                .andExpect(status().isOk());

        // Verificar stock actualizado
        mockMvc.perform(get("/productos/" + producto.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.stock", is(20)));
    }

    @Test
    public void testCrearProductoStockNegativo() throws Exception {
        Producto producto = new Producto(null, "Producto Negativo", -5, 100.0);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
                .andExpect(status().isBadRequest());
    }
}
