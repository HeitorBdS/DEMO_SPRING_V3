package com.exemplo.demo.service;

import com.exemplo.demo.model.Mensagem;
import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.MensagemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    private final UsuarioService usuarioService;

    public MensagemService(MensagemRepository mensagemRepository, UsuarioService usuarioService) {
        this.mensagemRepository = mensagemRepository;
        this.usuarioService = usuarioService;
    }

    public Mensagem salvarMensagem(Mensagem mensagem) {
        if (mensagem.getUsuario() != null && mensagem.getUsuario().getId() != null) {
            Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(mensagem.getUsuario().getId());
            if (usuarioOpt.isPresent()) {
                mensagem.setUsuario(usuarioOpt.get());
            } else {
                throw new IllegalArgumentException("Usuário com ID " + mensagem.getUsuario().getId() + " não encontrado.");
            }
        } else {
            throw new IllegalArgumentException("Mensagem deve ter um usuário associado.");
        }
        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> listarTodasMensagens() {
        return mensagemRepository.findAll();
    }

    public List<Mensagem> listarMensagensPorUsuario(Usuario usuario) {
        return mensagemRepository.findByUsuario(usuario);
    }

    public Optional<Mensagem> buscarMensagemPorId(Long id) {
        return mensagemRepository.findById(id);
    }

    public void deletarMensagem(Long id) {
        mensagemRepository.deleteById(id);
    }
}