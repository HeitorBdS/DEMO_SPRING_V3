package com.exemplo.demo.service;

import com.exemplo.demo.model.Mensagem;
import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.MensagemRepository;
import com.exemplo.demo.dto.MensagemDTO;
import com.exemplo.demo.dto.MensagemRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;
    private final UsuarioService usuarioService;

    public MensagemService(MensagemRepository mensagemRepository, UsuarioService usuarioService) {
        this.mensagemRepository = mensagemRepository;
        this.usuarioService = usuarioService;
    }

    public MensagemDTO salvarMensagem(MensagemRequestDTO mensagemRequestDTO) {
        if (mensagemRequestDTO.getUsuarioId() == null) {
            throw new IllegalArgumentException("Mensagem deve ter um ID de usuário associado.");
        }

        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(mensagemRequestDTO.getUsuarioId());

        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário com ID " + mensagemRequestDTO.getUsuarioId() + " não encontrado.");
        }

        Mensagem mensagem = new Mensagem();
        mensagem.setUsuario(usuarioOpt.get());
        mensagem.setConteudo(mensagemRequestDTO.getConteudo());

        Mensagem novaMensagem = mensagemRepository.save(mensagem);

        return new MensagemDTO(
                novaMensagem.getId(),
                novaMensagem.getUsuario().getId(),
                novaMensagem.getUsuario().getNome(),
                novaMensagem.getConteudo()
        );
    }

    private List<MensagemDTO> mapToListMensagemDTO(List<Mensagem> mensagens) {
        return mensagens.stream()
                .map(msg -> new MensagemDTO(
                        msg.getId(),
                        msg.getUsuario().getId(),
                        msg.getUsuario().getNome(),
                        msg.getConteudo()
                        // msg.getDataEnvio()
                ))
                .collect(Collectors.toList());
    }

    public List<MensagemDTO> listarTodasMensagens() {
        List<Mensagem> mensagens = mensagemRepository.findAll();
        return mapToListMensagemDTO(mensagens);
    }

    public List<MensagemDTO> listarMensagensPorUsuario(Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(usuarioId);
        if (usuarioOpt.isEmpty()) {
            throw new IllegalArgumentException("Usuário com ID " + usuarioId + " não encontrado.");
        }
        List<Mensagem> mensagens = mensagemRepository.findByUsuario(usuarioOpt.get());
        return mapToListMensagemDTO(mensagens);
    }

    public Optional<MensagemDTO> buscarMensagemPorId(Long id) {
        return mensagemRepository.findById(id)
                .map(msg -> new MensagemDTO(
                        msg.getId(),
                        msg.getUsuario().getId(),
                        msg.getUsuario().getNome(),
                        msg.getConteudo()
                ));
    }

    public void deletarMensagem(Long id) {
        mensagemRepository.deleteById(id);
    }
}