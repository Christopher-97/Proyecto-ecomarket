package com.example.ventas_service.controller;

import org.springframework.web.bind.annotation.*;
import com.ekomarket.model.Venta;
import com.ekomarket.service.VentaService;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    private final VentaService ventaService;

    public VentaController(VentaService ventaService) {
        this.ventaService = ventaService;
    }

    @PostMapping
    public Venta registrarVenta(@RequestBody Venta venta) {
        return ventaService.registrarVenta(venta);
    }

    @GetMapping
    public List<Venta> listarVentas() {
        return ventaService.listarVentas();
    }
}