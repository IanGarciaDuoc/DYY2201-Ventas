package com.example.ventas.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.ventas.model.Producto;
import com.example.ventas.service.ProductoService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

     public void testGetAllProductos() throws Exception {
        Producto producto = new Producto(/* ... inicializa tu producto con un ID ... */);
        producto.setId(1L);  // Asegúrate de asignar un ID
        List<Producto> allProductos = Arrays.asList(producto);

        given(productoService.getAll()).willReturn(allProductos);

        mockMvc.perform(get("/producto").accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$._embedded.productoList", hasSize(1)))
                .andExpect(jsonPath("$._embedded.productoList[0].id", is(producto.getId().intValue())));
    }

    @Test
    public void testGetById() throws Exception {
        Producto producto = new Producto(/* ... inicializa tu producto ... */);
        producto.setId(1L); // Ejemplo de ID

        given(productoService.getById(producto.getId())).willReturn(producto);

        mockMvc.perform(get("/producto/{id}", producto.getId()).accept(MediaTypes.HAL_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaTypes.HAL_JSON))
                .andExpect(jsonPath("$.id", is(producto.getId().intValue())))
                .andExpect(jsonPath("$._links.self.href", is("http://localhost/producto/" + producto.getId())));
    }

    

    @Test
    public void testDeleteById() throws Exception {
        doNothing().when(productoService).deleteById(anyLong());

        mockMvc.perform(delete("/producto/{id}", 1L))
                .andExpect(status().isOk());
    }
}