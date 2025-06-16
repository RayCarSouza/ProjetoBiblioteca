package com.example.Crudapp.controller;

import com.example.Crudapp.model.Bibliotecario;
import com.example.Crudapp.model.Livro;
import com.example.Crudapp.repository.BibliotecarioRepository;
import com.example.Crudapp.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/livros")
@CrossOrigin(origins = "*")
public class LivroController {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    // Listar todos os livros com bibliotecário
    @GetMapping
    public ResponseEntity<List<Livro>> listarTodos() {
        List<Livro> livros = livroRepository.findAllWithBibliotecario();
        return ResponseEntity.ok(livros);
    }

    // Buscar livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> buscarPorId(@PathVariable Long id) {
        return livroRepository.findByIdWithBibliotecario(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo livro
    @PostMapping
    public ResponseEntity<?> criar(@RequestBody LivroRequest dto) {
        Optional<Bibliotecario> bibOpt = bibliotecarioRepository.findById(dto.getBibliotecarioId());
        if (!bibOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bibliotecário não encontrado para o ID: " + dto.getBibliotecarioId());
        }

        Livro livro = new Livro();
        livro.setBibliotecario(bibOpt.get());
        livro.setTitulo(dto.getTitulo());
        livro.setAutor(dto.getAutor());
        livro.setGenero(dto.getGenero());
        livro.setStatus(dto.getStatus() != null && !dto.getStatus().isBlank()
                ? dto.getStatus() : "Disponível");
        // dataCadastro é inicializada no construtor de Livro

        Livro salvo = livroRepository.save(livro);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // Atualizar livro
    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody LivroRequest dto) {
        Optional<Livro> opt = livroRepository.findByIdWithBibliotecario(id);
        if (!opt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Livro livroExistente = opt.get();
        livroExistente.setTitulo(dto.getTitulo());
        livroExistente.setAutor(dto.getAutor());
        livroExistente.setGenero(dto.getGenero());
        livroExistente.setStatus(dto.getStatus());

        // Atualizar bibliotecário se informado
        if (dto.getBibliotecarioId() != null) {
            Optional<Bibliotecario> bibOpt = bibliotecarioRepository.findById(dto.getBibliotecarioId());
            bibOpt.ifPresent(livroExistente::setBibliotecario);
        }

        Livro atualizado = livroRepository.save(livroExistente);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar livro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        if (!livroRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        livroRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // DTO para receber dados de criação/atualização
    public static class LivroRequest {
        private Long bibliotecarioId;
        private String titulo;
        private String autor;
        private String genero;
        private String status;

        public Long getBibliotecarioId() {
            return bibliotecarioId;
        }

        public void setBibliotecarioId(Long bibliotecarioId) {
            this.bibliotecarioId = bibliotecarioId;
        }

        public String getTitulo() {
            return titulo;
        }

        public void setTitulo(String titulo) {
            this.titulo = titulo;
        }

        public String getAutor() {
            return autor;
        }

        public void setAutor(String autor) {
            this.autor = autor;
        }

        public String getGenero() {
            return genero;
        }

        public void setGenero(String genero) {
            this.genero = genero;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
