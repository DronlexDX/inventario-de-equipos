package com.universidad.inventario.config;

import com.universidad.inventario.model.Usuario;
import com.universidad.inventario.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initAdmin(UsuarioRepository usuarioRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {
            String correoAdmin = System.getenv().getOrDefault("ADMIN_CORREO", "admin@correo.com");

            boolean existeAdmin = usuarioRepository.findByCorreo(correoAdmin).isPresent();
            if (existeAdmin) {
                return;
            }

            Usuario admin = new Usuario();
            admin.setNombre(System.getenv().getOrDefault("ADMIN_NOMBRE", "Administrador"));
            admin.setCorreo(correoAdmin);
            admin.setContrasena(passwordEncoder.encode(
                    System.getenv().getOrDefault("ADMIN_PASSWORD", "Admin123*")
            ));
            admin.setRol(Usuario.Rol.ADMIN);

            usuarioRepository.save(admin);
        };
    }
}