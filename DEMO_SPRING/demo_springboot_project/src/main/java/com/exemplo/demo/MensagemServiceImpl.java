package com.exemplo.demo;

import com.exemplo.demo.model.Mensagem;
import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MensagemServiceImpl implements MensagemService {

    private final MensagemRepository mensagemRepository;

    @Autowired
    public MensagemServiceImpl(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    @Override
    public Mensagem salvarMensagem(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    @Override
    public List<Mensagem> listarTodasMensagens() {
        return mensagemRepository.findAll();
    }

    @Override
    public List<Mensagem> listarMensagensPorUsuario(Usuario usuario) {
        return mensagemRepository.findByUsuario(usuario);
    }

    @Override
    public Optional<Mensagem> buscarMensagemPorId(Long id) {
        return mensagemRepository.findById(id);
    }

    @Override
    public void deletarMensagem(Long id) {
        mensagemRepository.deleteById(id);
    }
}