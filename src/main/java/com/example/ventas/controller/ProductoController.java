package com.example.ventas.controller;

import com.example.ventas.model.Producto;

import com.example.ventas.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> getAllProductos() {
    List<EntityModel<Producto>> productos = productoService.getAll().stream()
            .map(producto -> {
                try {
                    return EntityModel.of(producto,
                            linkTo(methodOn(ProductoController.class).getById(producto.getId())).withSelfRel());
                            
                } catch (Exception e) {
                    
                    e.printStackTrace();
                }
                return null;
            })
            .collect(Collectors.toList());
    
    CollectionModel<EntityModel<Producto>> collectionModel = CollectionModel.of(productos,
            linkTo(methodOn(ProductoController.class).getAllProductos()).withSelfRel());

    return ResponseEntity.ok(collectionModel);
}
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Producto>> getById(@PathVariable Long id) throws Exception {
    Producto producto = productoService.getById(id);
    if (producto == null) {
        return ResponseEntity.notFound().build();
    }
    EntityModel<Producto> resource = EntityModel.of(producto,
            linkTo(methodOn(ProductoController.class).getById(id)).withSelfRel());
    return ResponseEntity.ok(resource);
    }
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) throws Exception {
        Producto nuevoProducto = productoService.create(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoProducto);
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        productoService.deleteById(id);    
    }
    
}