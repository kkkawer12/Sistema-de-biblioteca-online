-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS biblioteca_online;
USE biblioteca_online;

-- Criar a tabela de usuários
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    rg VARCHAR(12) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    faculdade VARCHAR(100) NOT NULL,
    ra VARCHAR(20) NOT NULL,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uk_cpf UNIQUE (cpf),
    CONSTRAINT uk_rg UNIQUE (rg),
    CONSTRAINT uk_email UNIQUE (email)
); 