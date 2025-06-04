package com.exemplo.demo.controller;

import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.cadastrar(usuario);
        return ResponseEntity.ok(novoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        return usuarioService.autenticar(usuario.getEmail(), usuario.getSenha())
                .map(u -> ResponseEntity.ok("Login realizado com sucesso!"))
                .orElse(ResponseEntity.status(401).body("Credenciais inválidas"));
    }
}
