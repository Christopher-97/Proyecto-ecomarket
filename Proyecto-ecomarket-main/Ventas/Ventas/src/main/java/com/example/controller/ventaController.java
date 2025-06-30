package com.ventas.ventas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ventas.ventas.model.Ventas;
import com.ventas.ventas.service.ventasService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v0/ventas/")
public class ventasController {

    @Autowired
    private ventasService ventasService;

    @GetMapping("")
    public ResponseEntity<List<Ventas>> getVentas() {
        List<Ventas> ventasList = ventasService.getAllVentas();
        if (ventasList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ventasList);
    }
    @GetMapping("mail/{mailCliente}")
    public ResponseEntity<List<Ventas>> getVentasByMailCliente(@PathVariable String mailCliente) {
        List<Ventas> ventasList = ventasService.getVentasByMailCliente(mailCliente);
        if (ventasList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ventasList);
    }

    @PostMapping("{mailCliente}")
    public ResponseEntity<String> postVenta(@RequestBody Ventas venta, @PathVariable String mailCliente) {
        try {
            ventasService.saveVenta(venta, mailCliente);
            return ResponseEntity.ok("Venta guardada correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
    


}
