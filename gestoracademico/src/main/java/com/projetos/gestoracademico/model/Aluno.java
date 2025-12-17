package com.projetos.gestoracademico.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_aluno;
    private String nome;
    private String email;
    private int idade;

    @ManyToOne
    @JoinColumn(name = "id_matricula")
    private Matricula matricula;
}

