package com.exemplo.demo.controller;

import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.service.UsuarioService;
import com.exemplo.demo.dto.UsuarioDTO;
import com.exemplo.demo.dto.UsuarioRegisterDTO;
import com.exemplo.demo.dto.UsuarioUpdateDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<UsuarioDTO> cadastrar(@RequestBody UsuarioRegisterDTO registerDTO) {
        UsuarioDTO novoUsuarioDTO = usuarioService.cadastrar(registerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuarioDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        return usuarioService.autenticar(usuario.getEmail(), usuario.getSenha())
                .map(u -> ResponseEntity.ok("Login realizado com sucesso!"))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getUsuarioById(@PathVariable Long id) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        return usuarioOptional
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsuarioDTO> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorEmail(email);
        return usuarioOptional
                .map(usuario -> new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTelefone()))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> updateUsuario(@PathVariable Long id, @RequestBody UsuarioUpdateDTO usuarioAtualizacaoDTO) {
        UsuarioDTO usuarioAtualizadoDTO = usuarioService.atualizarUsuario(id, usuarioAtualizacaoDTO);
        if (usuarioAtualizadoDTO != null) {
            return ResponseEntity.ok(usuarioAtualizadoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
