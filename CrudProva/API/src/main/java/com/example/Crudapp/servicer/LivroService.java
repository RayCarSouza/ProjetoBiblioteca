package com.example.Crudapp.servicer;

import com.example.Crudapp.model.Livro;
import com.example.Crudapp.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository repository;

    public List<Livro> listarTodos() {
        return repository.findAll();
    }

    public Optional<Livro> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Livro salvar(Livro livro) {
        if (livro.getDataCadastro() == null) {
            livro.setDataCadastro(LocalDate.now());
        }
        if (livro.getStatus() == null || livro.getStatus().isBlank()) {
            livro.setStatus("Disponível");
        }
        return repository.save(livro);
    }

    public Optional<Livro> atualizar(Long id, Livro livroAtualizado) {
        return repository.findById(id)
                .map(livro -> {
                    livro.setTitulo(livroAtualizado.getTitulo());
                    livro.setAutor(livroAtualizado.getAutor());
                    livro.setGenero(livroAtualizado.getGenero());
                    livro.setStatus(livroAtualizado.getStatus());
                    return repository.save(livro);
                });
    }

    public boolean excluir(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}