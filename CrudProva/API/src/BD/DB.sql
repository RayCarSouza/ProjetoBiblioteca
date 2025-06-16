-- Criação do banco de dados
CREATE DATABASE IF NOT EXISTS catalogo_livros;
USE catalogo_livros;

-- Tabela de Bibliotecários
CREATE TABLE IF NOT EXISTS bibliotecario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- Tabela de Livros
CREATE TABLE IF NOT EXISTS livro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    bibliotecario_id BIGINT NOT NULL,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'Disponível',
    data_cadastro DATE NOT NULL,

    FOREIGN KEY (bibliotecario_id) REFERENCES bibliotecario(id)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
