package com.universidad.inventario.service;

import com.universidad.inventario.model.Equipo;
import com.universidad.inventario.repository.EquipoRepository;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EquipoService {
    @Autowired
    private EquipoRepository equipoRepository;

    public List<Equipo>listarTodos() {return equipoRepository.findAll();}
    public void guardar(Equipo equipo){equipoRepository.save(equipo);}
    public Equipo obtenerPorId(Long id){return equipoRepository.findById(id).orElse(null);}
    public void eliminar(Long id){equipoRepository.deleteById(id);}
}
