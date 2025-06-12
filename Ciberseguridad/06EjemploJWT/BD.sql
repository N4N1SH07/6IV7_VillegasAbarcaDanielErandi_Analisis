-- Crear la base de datos
CREATE DATABASE jwt_db;

-- Usar la base de datos recién creada (corregido: era 'jwt_bd', debe ser 'jwt_db')
USE jwt_db;

-- Crear la tabla de usuarios
CREATE TABLE usuarios (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(255) NOT NULL
);
