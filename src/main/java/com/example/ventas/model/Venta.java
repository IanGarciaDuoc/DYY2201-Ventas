
package com.example.ventas.model;

import jakarta.persistence.*;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;


@Entity
@Table(name = "VENTA")
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Identificador único de la venta

    @Column(name = "FECHA")
    private Date fecha; // Fecha en la que se realizó la venta
    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "venta_producto", // Nombre de la tabla de unión
        joinColumns = @JoinColumn(name = "venta_id"), // Columna de unión en la tabla Venta
        inverseJoinColumns = @JoinColumn(name = "producto_id") // Columna de unión en la tabla Producto
    )
    private List<Producto> productos; // Lista de productos asociados a la venta

    // Constructor vacío
    public Venta() {
    }

    // Constructor con todos los campos
    public Venta(Date fecha, List<Producto> productos) {
        
        this.fecha = fecha;
        this.productos = productos;
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = (List<Producto>) productos;
    }
}
