package com.example.demo.controller;

import com.example.demo.dto.ProductoRequestDTO;
import com.example.demo.dto.ProductoResponseDTO;
import com.example.demo.service.ProductoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RestController
@RequestMapping("api/productos")
public class ProductoController {
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Reemplaza tu método @GetMapping principal por este:
    @GetMapping
    public Page<ProductoResponseDTO> listarProductos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "nombre") String sortBy) {

        return productoService.obtenerTodosLosProductos(page, size, sortBy);
    }

    // Endpoint para CREAR un producto
    @PostMapping
    public ProductoResponseDTO crearProducto(@Valid @RequestBody ProductoRequestDTO productoRequest) {
        return productoService.crearProducto(productoRequest);
    }

    // Endpoint para ACTUALIZAR un producto
    @PutMapping("/{id}")
    public ProductoResponseDTO actualizarProducto(
            @PathVariable("id") Integer id,
            @Valid @RequestBody ProductoRequestDTO productoRequest) {
        return productoService.actualizarProducto(id, productoRequest);
    }

    @DeleteMapping("/{id}")
    public void eliminarProducto(@PathVariable("id") Integer idProducto) {
        productoService.eliminarProducto(idProducto);
    }

    @PutMapping("/{id}/activar")
    public void activarProducto(@PathVariable("id") Integer idProducto) {
        productoService.activarProducto(idProducto);
    }

    // Endpoint para buscar productos por categoría
    @GetMapping("/categoria/{idCategoria}")
    public List<ProductoResponseDTO> listarProductosPorCategoria(@PathVariable("idCategoria") Integer idCategoria) {
        return productoService.obtenerProductosPorCategoria(idCategoria);
    }

    @GetMapping("/buscar")
    public List<ProductoResponseDTO> buscarProductos(@RequestParam("nombre") String nombre) {
        return productoService.buscarProductosPorNombre(nombre);
    }

}
