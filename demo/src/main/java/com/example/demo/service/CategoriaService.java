package com.example.demo.service;

import com.example.demo.dto.CategoriaResponseDTO;
import com.example.demo.entity.Categoria;
import com.example.demo.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;

    public CategoriaService(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaResponseDTO> obtenerTodasLasCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();

        // Transformamos cada Entidad a DTO usando Streams
        return categorias.stream().map(cat -> {
            String nombrePadre = null;
            if (cat.getCategoriaPadre() != null) {
                nombrePadre = cat.getCategoriaPadre().getNombre();
            }

            return new CategoriaResponseDTO(
                    cat.getIdCategoria(),
                    cat.getNombre(),
                    cat.getDescripcion(),
                    nombrePadre,
                    cat.getEstado()
            );
        }).collect(Collectors.toList());
    }
}