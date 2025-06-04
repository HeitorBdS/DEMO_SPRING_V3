package com.exemplo.demo;

import com.exemplo.demo.model.Mensagem;
import com.exemplo.demo.model.Usuario;
import java.util.List;
import java.util.Optional;

public interface MensagemService {
    Mensagem salvarMensagem(Mensagem mensagem);
    List<Mensagem> listarTodasMensagens();
    List<Mensagem> listarMensagensPorUsuario(Usuario usuario);
    Optional<Mensagem> buscarMensagemPorId(Long id);
    void deletarMensagem(Long id);
}