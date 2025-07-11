package com.example.ventas_service.controller;

import com.example.ventas_service.model.Venta;
import com.example.ventas_service.service.VentaService;
import com.example.ventas_service.client.InventarioClient;
import com.example.ventas_service.model.Producto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@WebMvcTest
public class VentaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @MockBean
    private InventarioClient inventarioClient;

    private Producto producto;
    private Venta venta;

    @BeforeEach
    public void setup() {
        producto = new Producto(1L, "Producto Test", 10, 100.0);
        venta = new Venta();
        venta.setId(1L);
        venta.setProductoId(1L);
        venta.setCantidad(2);
        venta.setFecha(LocalDateTime.now());
    }

    @Test
    public void testListarVentas() throws Exception {
        Mockito.when(ventaService.listarVentas()).thenReturn(Arrays.asList(venta));

        mockMvc.perform(get("/ventas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productoId").value(1L))
                .andExpect(jsonPath("$[0].cantidad").value(2));
    }

    @Test
    public void testRegistrarVenta_StockSuficiente() throws Exception {
        Mockito.when(inventarioClient.getProducto(anyLong())).thenReturn(producto);
        Mockito.when(ventaService.registrarVenta(any(Venta.class))).thenReturn(venta);

        String ventaJson = "{\"productoId\":1,\"cantidad\":2}";

        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ventaJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productoId").value(1L))
                .andExpect(jsonPath("$.cantidad").value(2));
    }

    @Test
    public void testRegistrarVenta_StockInsuficiente() throws Exception {
        Producto productoConStockBajo = new Producto(1L, "Producto Test", 1, 100.0);
        Mockito.when(inventarioClient.getProducto(anyLong())).thenReturn(productoConStockBajo);

        String ventaJson = "{\"productoId\":1,\"cantidad\":2}";

        mockMvc.perform(post("/ventas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ventaJson))
                .andExpect(status().isInternalServerError());
    }
}
