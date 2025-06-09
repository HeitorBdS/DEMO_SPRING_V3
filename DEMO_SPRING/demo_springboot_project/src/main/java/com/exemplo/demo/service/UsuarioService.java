package com.exemplo.demo.service;

import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.UsuarioRepository;
import com.exemplo.demo.dto.UsuarioDTO;
import com.exemplo.demo.dto.UsuarioRegisterDTO;
import com.exemplo.demo.dto.UsuarioUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO cadastrar(UsuarioRegisterDTO registerDTO) {
        Usuario usuario = new Usuario();
        usuario.setNome(registerDTO.getNome());
        usuario.setEmail(registerDTO.getEmail());
        usuario.setTelefone(registerDTO.getTelefone());
        usuario.setSenha(registerDTO.getSenha());

        Usuario novoUsuario = usuarioRepository.save(usuario);
        return new UsuarioDTO(novoUsuario.getId(), novoUsuario.getNome(), novoUsuario.getEmail(), novoUsuario.getTelefone());
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public UsuarioDTO atualizarUsuario(Long id, UsuarioUpdateDTO usuarioAtualizacaoDTO) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();

            if (usuarioAtualizacaoDTO.getNome() != null) {
                usuarioExistente.setNome(usuarioAtualizacaoDTO.getNome());
            }
            if (usuarioAtualizacaoDTO.getTelefone() != null) {
                usuarioExistente.setTelefone(usuarioAtualizacaoDTO.getTelefone());
            }

            Usuario usuarioAtualizado = usuarioRepository.save(usuarioExistente);
            return new UsuarioDTO(usuarioAtualizado.getId(), usuarioAtualizado.getNome(), usuarioAtualizado.getEmail(), usuarioAtualizado.getTelefone());
        }
        return null;
    }
}