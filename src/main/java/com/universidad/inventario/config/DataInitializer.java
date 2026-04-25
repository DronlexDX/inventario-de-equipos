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
            try {

                String correoAdmin = "admin@correo.com";

                Usuario existente = usuarioRepository.findByCorreo(correoAdmin);

                if (existente != null) {
                    System.out.println("Admin ya existe");
                    return;
                }

                Usuario admin = new Usuario();
                admin.setNombre("Administrador");
                admin.setCorreo(correoAdmin);
                admin.setContrasena(passwordEncoder.encode("Admin123*"));
                admin.setRol(Usuario.Rol.ADMIN);

                usuarioRepository.save(admin);

                System.out.println("Admin creado correctamente");

            } catch (Exception e) {
                e.printStackTrace();
            }
        };
    }
}