package com.example.demo.repository;

import com.example.demo.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    // Si más adelante necesitamos buscar categorías por nombre, lo declararemos aquí.
    // Por ahora, JpaRepository ya tiene el método findAll() que necesitamos.
}