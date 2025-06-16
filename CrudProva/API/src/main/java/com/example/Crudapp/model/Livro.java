package com.example.Crudapp.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter @Setter
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bibliotecario_id", nullable = false)
    @JsonBackReference
    private Bibliotecario bibliotecario;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(nullable = false, length = 50)
    private String genero;

    @Column(nullable = false, length = 20)
    private String status = "Disponível";

    @Column(nullable = false)
    private LocalDate dataCadastro;

    // Construtor que inicializa a data de cadastro
    public Livro() {
        this.dataCadastro = LocalDate.now();
    }
}