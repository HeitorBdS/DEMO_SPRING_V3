package com.exemplo.demo.controller;

import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.service.UsuarioService; // Importa a CLASSE Service do pacote service
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService; // Referencia a CLASSE Service

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Usuario> cadastrar(@RequestBody Usuario usuario) {
        Usuario novoUsuario = usuarioService.cadastrar(usuario);
        novoUsuario.setSenha(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        return usuarioService.autenticar(usuario.getEmail(), usuario.getSenha())
                .map(u -> ResponseEntity.ok("Login realizado com sucesso!"))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setSenha(null);
            return ResponseEntity.ok(usuario);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioAtualizacao) {
        Usuario usuarioAtualizado = usuarioService.atualizarUsuario(id, usuarioAtualizacao);
        if (usuarioAtualizado != null) {
            usuarioAtualizado.setSenha(null);
            return ResponseEntity.ok(usuarioAtualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}