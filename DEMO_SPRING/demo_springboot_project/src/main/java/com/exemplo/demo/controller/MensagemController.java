package com.exemplo.demo.controller;

import com.exemplo.demo.dto.MensagemDTO;
import com.exemplo.demo.dto.MensagemRequestDTO;
import com.exemplo.demo.service.MensagemService;
import com.exemplo.demo.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<MensagemDTO> enviarMensagem(@RequestBody MensagemRequestDTO mensagemRequestDTO) {
        try {
            MensagemDTO novaMensagem = mensagemService.salvarMensagem(mensagemRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(novaMensagem);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<MensagemDTO>> listarTodasMensagens() {
        List<MensagemDTO> mensagens = mensagemService.listarTodasMensagens();
        if (mensagens.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(mensagens);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<MensagemDTO>> listarMensagensPorUsuario(@PathVariable Long usuarioId) {
        try {
            List<MensagemDTO> mensagensDoUsuario = mensagemService.listarMensagensPorUsuario(usuarioId);
            if (mensagensDoUsuario.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(mensagensDoUsuario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensagemDTO> buscarMensagemPorId(@PathVariable Long id) {
        return mensagemService.buscarMensagemPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarMensagem(@PathVariable Long id) {
        mensagemService.deletarMensagem(id);
        return ResponseEntity.noContent().build();
    }
}