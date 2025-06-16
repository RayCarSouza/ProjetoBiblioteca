package com.example.Crudapp.controller;

import com.example.Crudapp.model.Bibliotecario;
import com.example.Crudapp.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecarios")
@CrossOrigin(origins = "*") // permite chamadas do front-end
public class BibliotecarioController {

    @Autowired
    private BibliotecarioRepository bibliotecarioRepository;

    // Listar todos
    @GetMapping
    public List<Bibliotecario> listarTodos() {
        return bibliotecarioRepository.findAll();
    }

    // Buscar por ID
    @GetMapping("/{id}")
    public Bibliotecario buscarPorId(@PathVariable Long id) {
        return bibliotecarioRepository.findById(id).orElse(null);
    }

    // Criar novo
    @PostMapping
    public Bibliotecario criar(@RequestBody Bibliotecario bibliotecario) {
        return bibliotecarioRepository.save(bibliotecario);
    }

    // Atualizar
    @PutMapping("/{id}")
    public Bibliotecario atualizar(@PathVariable Long id, @RequestBody Bibliotecario novo) {
        return bibliotecarioRepository.findById(id)
            .map(b -> {
                b.setNome(novo.getNome());
                b.setEmail(novo.getEmail());
                return bibliotecarioRepository.save(b);
            })
            .orElse(null);
    }

    // Deletar
    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        bibliotecarioRepository.deleteById(id);
    }
}
