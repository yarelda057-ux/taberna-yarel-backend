package com.example.demo.controller;

import com.example.demo.dto.CategoriaResponseDTO;
import com.example.demo.service.CategoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/categorias") // Esta es la URL base para este controlador
public class CategoriaController {
    private final CategoriaService categoriaService;

    public CategoriaController(CategoriaService categoriaService) {
        this.categoriaService = categoriaService;
    }

    // Al hacer una petición GET a /api/categorias, se ejecuta esto:
    @GetMapping
    public List<CategoriaResponseDTO> listarCategorias() {
        return categoriaService.obtenerTodasLasCategorias();
    }
}