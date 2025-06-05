package com.exemplo.demo.service; // Este é o pacote correto!

import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // Esta é a única classe de serviço que você terá
public class UsuarioService { // Sem implementar interface, é uma classe concreta direta

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrar(Usuario usuario) {
        // Lembre-se: HASHEAR SENHA AQUI ANTES DE SALVAR!
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> autenticar(String email, String senha) {
        // Lembre-se: Comparar HASHES das senhas aqui!
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    public Optional<Usuario> buscarUsuarioPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario atualizarUsuario(Long id, Usuario usuarioAtualizacao) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();

            // CUIDADO AQUI: Copiando manualmente APENAS os campos permitidos.
            if (usuarioAtualizacao.getNome() != null) {
                usuarioExistente.setNome(usuarioAtualizacao.getNome());
            }
            if (usuarioAtualizacao.getTelefone() != null) {
                usuarioExistente.setTelefone(usuarioAtualizacao.getTelefone());
            }

            return usuarioRepository.save(usuarioExistente);
        }
        return null;
    }
}