package com.exemplo.demo;

import com.exemplo.demo.model.Usuario;
import java.util.Optional;

public interface UsuarioService {
    Usuario cadastrar(Usuario usuario);
    Optional<Usuario> autenticar(String email, String senha);
    Optional<Usuario> buscarUsuarioPorId(Long id);
}