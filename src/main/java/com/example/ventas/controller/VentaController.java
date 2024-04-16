package com.example.ventas.controller;

import com.example.ventas.DTO.VentaData;
import com.example.ventas.model.Producto;
import com.example.ventas.model.Venta;
import com.example.ventas.service.ProductoService;
import com.example.ventas.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;
    @Autowired
    private ProductoService productoService;
   

    // Endpoint para obtener todas las ventas
    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        List<Venta> ventas = ventaService.getAll();
        return ResponseEntity.ok(ventas);
    }

    // Endpoint para obtener una venta por su ID
    @GetMapping("/{id}")
    public Venta getVentaById(@PathVariable Long id) throws Exception{
        return ventaService.getById(id);
    }

    @PostMapping
    public Venta createVenta(@RequestBody VentaData ventadata) throws Exception{
        
       
        

        ArrayList<Producto> listproductos =new ArrayList<>();
        for (Long  producto :ventadata.getProductos()) {
            listproductos.add(productoService.getById(producto));
        }
        Venta  newVenta = new Venta(ventadata.getFecha(),listproductos);
        newVenta = ventaService.create(newVenta);
        return newVenta;
    }
    
    // Endpoint para crear una nueva venta
    //@PostMapping
    //public ResponseEntity<Venta> crearVenta(@RequestBody Venta venta) {
      //  Venta nuevaVenta = ventaService.create(venta);
        //return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
   // }


    // Endpoint para eliminar una venta por su ID
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        ventaService.deleteById(id);    
    }
    
}
