package com.exemplo.demo.model; // Este pacote permanece o mesmo

import jakarta.persistence.*;
import lombok.Data; // Certifique-se de que o Lombok está no seu pom.xml

@Entity
@Data // Anotação do Lombok para getters, setters, etc.
public class Usuario {

    @Id // Marca o campo 'id' como a chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura a geração automática do ID
    private Long id;

    private String nome;
    private String email;
    private String telefone;
    private String senha; // Este campo de senha é o que manipulamos nas camadas de serviço/controller
    // para evitar exposição direta.
}