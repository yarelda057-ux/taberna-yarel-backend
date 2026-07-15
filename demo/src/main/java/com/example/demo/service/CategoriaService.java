package com.example.demo.service;

import com.example.demo.entity.Categoria;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    // Inyección de dependencias por constructor (Buena práctica de arquitectura)
    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    // Método para listar todas las categorías activas
    public List<Categoria> obtenerTodasLasCategorias() {
        return categoriaRepository.findAll();
    }
}