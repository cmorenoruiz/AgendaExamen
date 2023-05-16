CREATE DATABASE agenda;

USE agenda;

CREATE TABLE contactos (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50),
    telefono VARCHAR(20)
);

INSERT INTO contactos (nombre, telefono) VALUES ('Isabel Allende', '555-1234');
INSERT INTO contactos (nombre, telefono) VALUES ('Gabriela Mistral', '555-5678');
INSERT INTO contactos (nombre, telefono) VALUES ('Frida Kahlo', '555-9012');
INSERT INTO contactos (nombre, telefono) VALUES ('Sor Juana Inés de la Cruz', '555-3456');
INSERT INTO contactos (nombre, telefono) VALUES ('Carmen Laforet', '555-7890');
INSERT INTO contactos (nombre, telefono) VALUES ('Gloria Fuertes', '555-2345');
INSERT INTO contactos (nombre, telefono) VALUES ('Rosalía de Castro', '555-6789');
INSERT INTO contactos (nombre, telefono) VALUES ('Elvira Lindo', '555-4321');
INSERT INTO contactos (nombre, telefono) VALUES ('Barbijaputa', '555-8765');
INSERT INTO contactos (nombre, telefono) VALUES ('Icíar Bollaín', '555-2468');
INSERT INTO contactos (nombre, telefono) VALUES ('Isabel Coixet', '555-1357');
INSERT INTO contactos (nombre, telefono) VALUES ('Leticia Dolera', '555-3698');

