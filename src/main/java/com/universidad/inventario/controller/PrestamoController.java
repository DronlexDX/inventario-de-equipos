package com.universidad.inventario.controller;

import com.universidad.inventario.model.Equipo;
import com.universidad.inventario.model.Usuario;
import com.universidad.inventario.repository.UsuarioRepository;
import com.universidad.inventario.service.EquipoService;
import com.universidad.inventario.service.PrestamoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/prestamos")
public class PrestamoController {
    @Autowired
    private PrestamoService prestamoService;

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/mis-prestamos")
    public String misPrestamos(Model model, Principal principal){
        String correoSesion = principal.getName();
        Usuario usuarioLogeado  = usuarioRepository.findByCorreo(correoSesion).orElse(null);

        if(usuarioLogeado != null){
            model.addAttribute("prestamos",prestamoService.obtenerPrestamosPorUsuario(usuarioLogeado));
        }
        return "prestamos/lista-estudiante";
    }

    @PostMapping("/solicitar")
    public String solicitarEquipo(@RequestParam("equipoId")Long equipoId, Principal principal){
        String correoSesion = principal.getName();
        Usuario usuarioLogeado = usuarioRepository.findByCorreo(correoSesion).orElse(null);
        Equipo equipoSolicitado = equipoService.obtenerPorId(equipoId);

        if(usuarioLogeado != null && equipoSolicitado != null){
            try {
                prestamoService.crearPrestamo(usuarioLogeado, equipoSolicitado, 3);
            } catch (RuntimeException e){
                return "redirect:/equipos?error=" + e.getMessage();
            }
        }
        return "redirect:/prestamos/mis-prestamos";
    }
    @PostMapping("/devolver")
    public String devolverEquipo(@RequestParam("prestamoId")Long prestamoId){
        try {
            prestamoService.devolverPrestamo(prestamoId);
        } catch (RuntimeException e){
            return "redirect:/prestamos/mis-prestamos?error=NoSePudoDevolver";
        }
        return "redirect:/prestamos/mis-prestamos";
    }
}
