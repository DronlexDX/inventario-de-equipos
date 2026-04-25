package com.universidad.inventario.controller;

import com.universidad.inventario.model.Equipo;
import com.universidad.inventario.model.Usuario;
import com.universidad.inventario.repository.UsuarioRepository;
import com.universidad.inventario.service.EquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/equipos")
public class EquipoController {

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public String listarEquipos(Model model, Principal principal) {
        model.addAttribute("equipos", equipoService.listarTodos());
        model.addAttribute("esAdmin", esAdmin(principal));
        return "equipos/lista";
    }

    @GetMapping("/nuevo")
    public String nuevoEquipo(Model model, Principal principal) {
        model.addAttribute("equipo", new Equipo());
        model.addAttribute("esAdmin", esAdmin(principal));
        return "equipos/formulario";
    }

    @PostMapping("/guardar")
    public String guardarEquipo(@ModelAttribute("equipo") Equipo equipo) {
        if (equipo.getId() == null) {
            equipo.setCantidadDisponible(equipo.getCantidadTotal());
        }
        equipoService.guardar(equipo);
        return "redirect:/equipos";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarEquipo(@PathVariable Long id) {
        equipoService.eliminar(id);
        return "redirect:/equipos";
    }

    private boolean esAdmin(Principal principal) {
        if (principal == null) {
            return false;
        }

        Usuario usuario = usuarioRepository.findByCorreo(principal.getName()).orElse(null);
        return usuario != null && usuario.getRol() == Usuario.Rol.ADMIN;
    }
}