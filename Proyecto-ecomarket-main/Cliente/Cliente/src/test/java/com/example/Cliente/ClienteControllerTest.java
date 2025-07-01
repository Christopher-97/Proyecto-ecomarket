package com.example.cliente.controller;

import com.example.cliente.controller.ClienteController;
import com.example.cliente.dto.Cliente;
import com.example.cliente.dto.ClienteModel;
import com.example.cliente.assembler.ClienteModelAssembler;
import com.example.cliente.service.ClienteService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService service;

    @MockBean
    private ClienteModelAssembler assembler;

    @Autowired
    private ObjectMapper objectMapper;

    // Dummy model para simular respuestas HATEOAS
    public static class DummyClienteModel extends ClienteModel {
        public DummyClienteModel(Cliente cli) {
            this.setId(cli.getId());
            this.setNombre(cli.getNombre());
            this.setContrasenia(cli.getContrasenia());
            this.setRun(cli.getRun());
            this.setTelefono(cli.getTelefono());
            this.add(Link.of("http://localhost/api/v0/clientes/" + cli.getId()).withSelfRel());
        }
    }

    @Test
    @DisplayName("GET /api/v0/clientes retorna 404 si no hay clientes")
    public void testListarClientesVacio() throws Exception {
        when(service.findAll()).thenReturn(List.of());

        mockMvc.perform(get("/api/v0/clientes/"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/v0/clientes/{id} retorna 404 si no existe")
    public void testBuscarPorIdNoExistente() throws Exception {
        when(service.findById(99L)).thenReturn(null);

        mockMvc.perform(get("/api/v0/clientes/99"))
            .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("DELETE /api/v0/clientes/{id} elimina cliente existente")
    public void testEliminarCliente() throws Exception {
        doNothing().when(service).deleteCliente(6L);

        mockMvc.perform(delete("/api/v0/clientes/6"))
            .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("POST /api/v0/clientes crea un cliente")
    public void testAgregarCliente() throws Exception {
        Cliente nuevo = new Cliente(null, "Luis", "1234", 11111111L, "912345678");
        Cliente guardado = new Cliente(3L, "Luis", "1234", 11111111L, "912345678");

        when(service.saveCliente(any(Cliente.class))).thenReturn(guardado);
        when(assembler.toModel(any(Cliente.class))).thenReturn(new DummyClienteModel(guardado));

        mockMvc.perform(post("/api/v0/clientes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nuevo)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombre").value("Luis"))
            .andExpect(jsonPath("$.id").value(3));
    }

    @Test
    @DisplayName("GET /api/v0/clientes/{id} retorna cliente existente")
    public void testBuscarPorIdExistente() throws Exception {
        Cliente cli = new Cliente(4L, "Clara", "abcd", 22222222L, "987654321");

        when(service.findById(4L)).thenReturn(cli);
        when(assembler.toModel(any(Cliente.class))).thenReturn(new DummyClienteModel(cli));

        mockMvc.perform(get("/api/v0/clientes/4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Clara"));
    }

    @Test
    @DisplayName("PUT /api/v0/clientes/{id} actualiza cliente existente")
    public void testActualizarCliente() throws Exception {
        Cliente actualizado = new Cliente(5L, "Marco", "pass", 33333333L, "912345679");

        when(service.update(any(Cliente.class), eq(5L))).thenReturn(actualizado);
        when(assembler.toModel(any(Cliente.class))).thenReturn(new DummyClienteModel(actualizado));

        mockMvc.perform(put("/api/v0/clientes/5")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Marco"));
    }

    @Test
    @DisplayName("PUT /api/v0/clientes/{id} retorna 404 si no se encuentra cliente")
    public void testActualizarClienteNoEncontrado() throws Exception {
        when(service.update(any(Cliente.class), eq(88L))).thenReturn(null);

        mockMvc.perform(put("/api/v0/clientes/88")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new Cliente())))
            .andExpect(status().isNotFound());
    }
}