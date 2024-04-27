package com.example.ventas.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.ArgumentMatchers.any;

import com.example.ventas.DTO.VentaData;
import com.example.ventas.model.Producto;
import com.example.ventas.model.Venta;
import com.example.ventas.service.ProductoService;
import com.example.ventas.service.VentaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(VentaController.class)
public class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;
    
    @MockBean
    private ProductoService productoService;

    @Test
    public void testGetAllVentas() throws Exception {
        mockMvc.perform(get("/ventas"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaTypes.HAL_JSON));
    }


@Test
public void testGetVentaById() throws Exception {
    Long ventaId = 1L;
    Venta venta = new Venta(); // Configura la venta como sea necesario

    given(ventaService.getById(ventaId)).willReturn(venta);

    mockMvc.perform(get("/ventas/{id}", ventaId))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON));
}

@Test
public void testDeleteById() throws Exception {
    Long ventaId = 1L;

    doNothing().when(ventaService).deleteById(ventaId);

    mockMvc.perform(delete("/ventas/{id}", ventaId))
        .andExpect(status().isOk());
}

}
