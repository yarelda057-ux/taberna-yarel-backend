package com.example.demo.security;

import com.example.demo.entity.UsuarioAdmin;
import com.example.demo.repository.UsuarioAdminRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioAdminRepository usuarioAdminRepository;

    public CustomUserDetailsService(UsuarioAdminRepository usuarioAdminRepository) {
        this.usuarioAdminRepository = usuarioAdminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos el usuario en la base de datos verificando que esté activo
        UsuarioAdmin usuario = usuarioAdminRepository.findByUsernameAndEstadoTrue(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado o inactivo: " + username));

        // 2. Convertimos el rol (texto plano) a un formato que Spring Security reconozca
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(usuario.getRol());

        // 3. Retornamos el objeto UserDetails de Spring Security con los datos extraídos
        return new User(
                usuario.getUsername(),
                usuario.getPasswordHash(),
                Collections.singletonList(authority)
        );
    }
}