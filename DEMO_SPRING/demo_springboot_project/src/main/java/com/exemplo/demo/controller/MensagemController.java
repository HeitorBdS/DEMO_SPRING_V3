package com.exemplo.demo.controller;

import com.exemplo.demo.model.Mensagem;
import com.exemplo.demo.model.Usuario;
import com.exemplo.demo.service.MensagemService;
import com.exemplo.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    private final MensagemService mensagemService;
    private final UsuarioService usuarioService;

    public MensagemController(MensagemService mensagemService, UsuarioService usuarioService) {
        this.mensagemService = mensagemService;
        this.usuarioService = usuarioService;
    }

    @PostMapping("/enviar")
    public ResponseEntity<Mensagem> enviarMensagem(@RequestBody Mensagem mensagem) {

        if (mensagem.getUsuario() == null || mensagem.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().build();
        }

        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(mensagem.getUsuario().getId());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        mensagem.setUsuario(usuarioOpt.get());

        Mensagem novaMensagem = mensagemService.salvarMensagem(mensagem);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaMensagem);
    }

    @GetMapping
    public ResponseEntity<List<Mensagem>> listarTodasMensagens() {
        List<Mensagem> mensagens = mensagemService.listarTodasMensagens();
        if (mensagens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mensagens);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Mensagem>> listarMensagensPorUsuario(@PathVariable Long usuarioId) {
        Optional<Usuario> usuarioOpt = usuarioService.buscarUsuarioPorId(usuarioId);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Mensagem> mensagensDoUsuario = mensagemService.listarMensagensPorUsuario(usuarioOpt.get());
        if (mensagensDoUsuario.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mensagensDoUsuario);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> buscarMensagemPorId(@PathVariable Long id) {
        return mensagemService.buscarMensagemPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMensagem(@PathVariable Long id) {
        mensagemService.deletarMensagem(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

}