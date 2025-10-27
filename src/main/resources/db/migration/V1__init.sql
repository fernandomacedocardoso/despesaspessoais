CREATE TABLE categorias (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 nome VARCHAR(60) NOT NULL,
 descricao VARCHAR(255),
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL,
 CONSTRAINT uk_categoria_nome UNIQUE (nome)
) ENGINE=InnoDB;

CREATE TABLE despesas (
 id BIGINT PRIMARY KEY AUTO_INCREMENT,
 descricao VARCHAR(120) NOT NULL,
 valor DECIMAL(15,2) NOT NULL,
 data DATE NOT NULL,
 forma_pagamento VARCHAR(20) NOT NULL,
 categoria_id BIGINT NOT NULL,
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL,
 CONSTRAINT fk_despesa_categoria FOREIGN KEY (categoria_id) REFERENCES categorias(id)
) ENGINE=InnoDB;