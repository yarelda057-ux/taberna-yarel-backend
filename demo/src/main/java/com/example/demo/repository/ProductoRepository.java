package com.example.demo.repository;

import com.example.demo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // JpaRepository ya incluye métodos como findAll(), findById(), save(), deleteById()
    // Más adelante, si necesitas buscar productos por categoría, podemos agregar métodos aquí.
}