package com.example.ventas_service.service;

import org.springframework.stereotype.Service;
import com.example.ventas_service.client.InventarioClient;
import com.example.ventas_service.model.Venta;
import com.example.ventas_service.repository.VentaRepository;
import com.example.ventas_service.model.Producto;
import java.util.List;

@Service
public class VentaService {

    private final VentaRepository ventaRepository;
    private final InventarioClient inventarioClient;

    public VentaService(VentaRepository ventaRepository, InventarioClient inventarioClient) {
        this.ventaRepository = ventaRepository;
        this.inventarioClient = inventarioClient;
    }

    public Venta registrarVenta(Venta venta) {
        // 1. Verificar stock en inventario-service
        Producto producto = inventarioClient.getProducto(venta.getProductoId());
        if (producto.getStock() < venta.getCantidad()) {
            throw new RuntimeException("Stock insuficiente");
        }
        
        // 2. Actualizar stock
        inventarioClient.actualizarStock(venta.getProductoId(), producto.getStock() - venta.getCantidad());
        
        // 3. Guardar venta
        return ventaRepository.save(venta);
    }

    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }
}
