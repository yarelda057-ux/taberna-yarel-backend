package com.example.demo.repository;

import com.example.demo.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
    // JpaRepository ya incluye métodos como findAll(), findById(), save(), deleteById()
    // Más adelante, si necesitas buscar productos por categoría, podemos agregar métodos aquí.

    Page<Producto> findAllByEstadoTrue(Pageable pageable);
    // El guion bajo (_) le indica a Spring que busque dentro de la relación "Categoria" su propiedad "idCategoria"
    List<Producto> findByCategoria_IdCategoriaAndEstadoTrue(Integer idCategoria);

    // Consulta JPQL personalizada
    @Query("SELECT p FROM Producto p WHERE p.estado = true AND LOWER(p.nombre) LIKE LOWER(CONCAT('%', :termino, '%'))")
    List<Producto> buscarPorNombreActivo(@Param("termino") String termino);
}