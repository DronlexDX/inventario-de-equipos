package com.universidad.inventario.controller;

import com.universidad.inventario.model.Usuario;
import com.universidad.inventario.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model){
        model.addAttribute("usuario",new Usuario());
        return "usuarios/registro";
    }

    @PostMapping("/registro")
    public String procesarRegistro(@ModelAttribute("usuario")Usuario usuario){
        if (usuario.getRol()== null){
            usuario.setRol(Usuario.Rol.ESTUDIANTE);
        }
        usuarioService.registrar(usuario);
        return "redirect:/usuarios/login?exito";
    }

    @GetMapping("/login")
    public String mostrarFormularioLogin(){
        return "usuarios/login";
    }
}
