package com.universidad.inventario.service;

import com.universidad.inventario.model.Equipo;
import com.universidad.inventario.model.Prestamo;
import com.universidad.inventario.model.Usuario;
import com.universidad.inventario.repository.EquipoRepository;
import com.universidad.inventario.repository.PrestamoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrestamoService {
    @Autowired
    private  PrestamoRepository prestamoRepository;

    @Autowired
    private EquipoRepository equipoRepository;

    @Transactional
    public Prestamo crearPrestamo(Usuario usuario, Equipo equipo, int diasPrestamo){
        if(equipo.getCantidadDisponible()<= 0){
            throw new RuntimeException("El equipo" + equipo.getNombre() + "No tiene unidades disponibles.");
        }
        equipo.setCantidadDisponible(equipo.getCantidadDisponible()-1);
        equipoRepository.save(equipo);

        Prestamo nuevoPrestamo = new Prestamo();
        nuevoPrestamo.setUsuario(usuario);
        nuevoPrestamo.setEquipo(equipo);
        nuevoPrestamo.setFechaPrestamo(LocalDate.now());
        nuevoPrestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(diasPrestamo));
        nuevoPrestamo.setEstado(Prestamo.EstadoPrestamo.ACTIVO);

        return  prestamoRepository.save(nuevoPrestamo);
    }
    @Transactional
    public void devolverPrestamo(Long prestamoId){
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Prestamo no encontrado"));
        if(prestamo.getEstado() == Prestamo.EstadoPrestamo.DEVUELTO){
            throw new RuntimeException("Este equipo ya fue devuelto.");
        }
        prestamo.setEstado(Prestamo.EstadoPrestamo.DEVUELTO);
        prestamoRepository.save(prestamo);

        Equipo equipo = prestamo.getEquipo();
        equipo.setCantidadDisponible(equipo.getCantidadDisponible()+1);
        equipoRepository.save(equipo);
    }
    public List<Prestamo> obtenerPrestamosPorUsuario(Usuario usuario){
        return prestamoRepository.findByUsuario(usuario);
    }
    public List<Prestamo>obtenerTodos(){
        return prestamoRepository.findAll();
    }
}
