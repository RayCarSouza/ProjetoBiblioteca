package com.example.Crudapp.repository;

import com.example.Crudapp.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    
    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.bibliotecario")
    List<Livro> findAllWithBibliotecario();
    
    @Query("SELECT l FROM Livro l LEFT JOIN FETCH l.bibliotecario WHERE l.id = :id")
    Optional<Livro> findByIdWithBibliotecario(@Param("id") Long id);
}