package com.example.Crudapp.repository;

import com.example.Crudapp.model.Bibliotecario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BibliotecarioRepository extends JpaRepository<Bibliotecario, Long> {
}
