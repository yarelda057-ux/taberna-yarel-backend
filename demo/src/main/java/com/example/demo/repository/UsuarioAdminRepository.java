package com.example.demo.repository;

import com.example.demo.entity.UsuarioAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioAdminRepository extends JpaRepository<UsuarioAdmin, Integer> {

    // Este método será vital para el Login: buscará si existe un usuario con ese nombre
    Optional<UsuarioAdmin> findByUsernameAndEstadoTrue(String username);
}