package com.exemplo.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "fato_mensagem")
@Data
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mensagem")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @Column(name = "conteudo_mensagem")
    private String conteudo;
}