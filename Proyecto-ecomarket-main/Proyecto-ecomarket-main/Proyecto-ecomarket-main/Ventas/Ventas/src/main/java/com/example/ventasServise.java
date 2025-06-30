package com.ventas.ventas.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.ventas.ventas.model.Ventas;
import com.ventas.ventas.model.user;
import com.ventas.ventas.repository.ventasRepository;

import jakarta.transaction.Transactional;

@Transactional
@Service
public class ventasService {
    
    @Autowired
    private ventasRepository ventasRepository;

    @Autowired
    private RestTemplate restTemplate;

    private final String BASE_URL = "http://localhost:8080/api/v0/usuarios/";

    public List<Ventas> getAllVentas() {
        // Aquí puedes implementar la lógica para obtener todas las Ventas
        // Por ejemplo, retornar una lista de Ventas desde el repositorio
        return ventasRepository.findAll();
    }

    public void saveVenta(Ventas venta, String mail) {
        try{
            ResponseEntity<user> cliente = restTemplate.getForEntity(BASE_URL + "mail/" + mail, user.class);
            if (cliente.getStatusCode() == HttpStatus.OK) {
                venta.setIdCliente(cliente.getBody().getId());
                ventasRepository.save(venta);
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RuntimeException("Cliente no encontrado: " + mail);
        }
        
    }
    public void deleteVenta(Integer id) {
        // Aquí puedes implementar la lógica para eliminar una venta por su ID
        // Por ejemplo, usar el repositorio para eliminar la venta
        ventasRepository.deleteById(id);
    }
    public void updateVenta(Ventas venta) {
        // Aquí puedes implementar la lógica para actualizar una venta existente
        // Por ejemplo, usar el repositorio para guardar la venta actualizada
        ventasRepository.save(venta);
    }
    public Ventas getVentaById(Integer id) {
        // Aquí puedes implementar la lógica para obtener una venta por su ID
        // Por ejemplo, usar el repositorio para encontrar la venta
        return ventasRepository.findById(id).orElse(null);
    }

    public List<Ventas> getVentasByMailCliente(String mailCliente){
        try {
            ResponseEntity<user> cliente = restTemplate.getForEntity(BASE_URL + "mail/" + mailCliente, user.class);
            if (cliente.getStatusCode() == HttpStatus.OK) {
                return ventasRepository.findByIdCliente(cliente.getBody().getId());
            }
            return Collections.emptyList();
        } catch (HttpClientErrorException.NotFound ex) {
            return Collections.emptyList();
        }
    }

}
