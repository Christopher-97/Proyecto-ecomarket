package com.example.Inventario;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InventarioApplicationTests {

    @Autowired
    private ProductoController productoController;

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
        assertThat(productoController).isNotNull();
    }
}