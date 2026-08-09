package com.example.demo;

import com.example.demo.entity.UsuarioAdmin;
import com.example.demo.repository.UsuarioAdminRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(UsuarioAdminRepository repository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Verifica si el usuario ya existe para no duplicarlo
			if (repository.findByUsernameAndEstadoTrue("admin").isEmpty()) {
				UsuarioAdmin admin = new UsuarioAdmin();
				admin.setUsername("admin");
				admin.setPasswordHash(passwordEncoder.encode("admin123")); // Encripta la contraseña
				admin.setRol("ROLE_ADMIN");
				admin.setEstado(true);

				repository.save(admin);
				System.out.println("Usuario de prueba creado exitosamente.");
			}
		};
	}

}
