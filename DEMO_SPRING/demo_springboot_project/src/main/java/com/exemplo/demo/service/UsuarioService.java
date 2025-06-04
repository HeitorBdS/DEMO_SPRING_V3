package com.exemplo.demo.service;

import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }
}