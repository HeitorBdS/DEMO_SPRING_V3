package com.exemplo.demo.repository;

import com.exemplo.demo.model.Mensagem;
import com.exemplo.demo.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByUsuario(Usuario usuario);
}