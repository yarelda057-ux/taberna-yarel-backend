package com.example.demo.controller;

import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.service.ProductoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping
    public List<ProductoResponseDTO> listarProductos(){
        return productoService.obtenerTodosLosProductos();
    }
}
