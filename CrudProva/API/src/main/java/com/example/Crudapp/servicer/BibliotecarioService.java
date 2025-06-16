package com.example.Crudapp.servicer;

import com.example.Crudapp.model.Bibliotecario;
import com.example.Crudapp.repository.BibliotecarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BibliotecarioService {

    @Autowired
    private BibliotecarioRepository repository;

    /** Lista todos os bibliotecários */
    public List<Bibliotecario> listarTodos() {
        return repository.findAll();
    }

    /** Busca um bibliotecário por ID */
    public Optional<Bibliotecario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /** Salva (ou atualiza) um bibliotecário */
    public Bibliotecario salvar(Bibliotecario b) {
        return repository.save(b);
    }

    /** Remove um bibliotecário por ID */
    public void excluir(Long id) {
        repository.deleteById(id);
    }
}
