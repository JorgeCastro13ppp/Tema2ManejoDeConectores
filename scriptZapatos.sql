-- Borrar la base de datos si existe
DROP DATABASE IF EXISTS BDZapatos;

-- Crear la base de datos
CREATE DATABASE BDZapatos;

-- Usar la base de datos
USE BDZapatos;

-- Crear la tabla zapato
CREATE TABLE zapato (
    id INT AUTO_INCREMENT PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    tamano VARCHAR(10),
    color VARCHAR(20),
    stock INT,
    precio DECIMAL(10,2)
);
