package com.universidad.inventario.repository;

import com.universidad.inventario.model.Prestamo;
import com.universidad.inventario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import  java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo>findByUsuario(Usuario usuario);
    List<Prestamo> findByEstado(Prestamo.EstadoPrestamo estado);

}
