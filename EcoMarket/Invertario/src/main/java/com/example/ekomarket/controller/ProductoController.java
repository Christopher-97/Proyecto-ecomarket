package com.example.ekomarket.controller;

import com.example.ekomarket.model.Producto;
import com.example.ekomarket.services.ProductoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/productos")
@Api(tags = "Productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    @ApiOperation(value = "Listar todos los productos")
    public ResponseEntity<List<EntityModel<Producto>>> listarProductos() {
        List<EntityModel<Producto>> productos = productoService.listarProductos().stream()
                .map(producto -> EntityModel.of(producto,
                        WebMvcLinkBuilder.linkTo(methodOn(ProductoController.class).buscarPorId(producto.getId())).withSelfRel(),
                        WebMvcLinkBuilder.linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos")))
                .collect(Collectors.toList());
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "Crear un nuevo producto")
    public ResponseEntity<EntityModel<Producto>> crearProducto(@RequestBody Producto producto) {
        if (producto.getStock() < 0) {
            return ResponseEntity.badRequest().build();
        }
        Producto nuevoProducto = productoService.crearProducto(producto);
        EntityModel<Producto> resource = EntityModel.of(nuevoProducto,
                WebMvcLinkBuilder.linkTo(methodOn(ProductoController.class).buscarPorId(nuevoProducto.getId())).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"));
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Buscar producto por ID")
    public ResponseEntity<EntityModel<Producto>> buscarPorId(@PathVariable Long id) {
        Producto producto = productoService.buscarPorId(id);
        EntityModel<Producto> resource = EntityModel.of(producto,
                WebMvcLinkBuilder.linkTo(methodOn(ProductoController.class).buscarPorId(id)).withSelfRel(),
                WebMvcLinkBuilder.linkTo(methodOn(ProductoController.class).listarProductos()).withRel("productos"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @PutMapping("/{id}/stock")
    @ApiOperation(value = "Actualizar stock de un producto")
    public ResponseEntity<Void> actualizarStock(@PathVariable Long id, @RequestBody int nuevoStock) {
        if (nuevoStock < 0) {
            return ResponseEntity.badRequest().build();
        }
        productoService.actualizarStock(id, nuevoStock);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
