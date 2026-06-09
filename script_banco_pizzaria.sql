-- =========================================================
-- SCRIPT DE CRIAÇÃO DO BANCO DE DADOS - PIZZARIA
-- Banco: pizzaria  |  SGBD: PostgreSQL
-- Aluno: Kauã Lorenseti  |  ADS - Tópicos Especiais III
-- =========================================================

-- 1. Criar as tabelas

CREATE TABLE usuario (
    id    SERIAL        NOT NULL,
    nome  TEXT          NOT NULL,
    email TEXT          NOT NULL UNIQUE,
    senha TEXT          NOT NULL,
    CONSTRAINT pk_usuario PRIMARY KEY (id)
);

CREATE TABLE cliente (
    id        SERIAL NOT NULL,
    nome      TEXT   NOT NULL,
    email     TEXT   NOT NULL,
    telefone  TEXT   NOT NULL,
    endereco  TEXT,
    CONSTRAINT pk_cliente PRIMARY KEY (id)
);

CREATE TABLE pizza (
    id          SERIAL         NOT NULL,
    nome        TEXT           NOT NULL,
    descricao   TEXT,
    preco       NUMERIC(10, 2) NOT NULL,
    tamanho     TEXT           NOT NULL,  -- enum: PEQUENA, MEDIA, GRANDE, FAMILIA
    disponivel  BOOLEAN        NOT NULL DEFAULT TRUE,
    CONSTRAINT pk_pizza PRIMARY KEY (id)
);

CREATE TABLE pedido (
    id               SERIAL         NOT NULL,
    id_cliente       INTEGER        NOT NULL,
    id_pizza         INTEGER        NOT NULL,
    datahorapedido   TIMESTAMP WITH TIME ZONE NOT NULL,
    quantidade       INTEGER        NOT NULL DEFAULT 1,
    valortotal       NUMERIC(10, 2) NOT NULL,
    status           TEXT           NOT NULL DEFAULT 'PENDENTE',
    CONSTRAINT pk_pedido     PRIMARY KEY (id),
    CONSTRAINT fk_ped_cli    FOREIGN KEY (id_cliente) REFERENCES cliente(id),
    CONSTRAINT fk_ped_pizza  FOREIGN KEY (id_pizza)   REFERENCES pizza(id)
);

CREATE TABLE entrega (
    id               SERIAL  NOT NULL,
    id_pedido        INTEGER NOT NULL,
    enderecoentrega  TEXT    NOT NULL,
    datahoraenvio    TIMESTAMP WITH TIME ZONE,
    datahoraentrega  TIMESTAMP WITH TIME ZONE,
    status           TEXT    NOT NULL DEFAULT 'AGUARDANDO',
    observacao       TEXT,
    CONSTRAINT pk_entrega  PRIMARY KEY (id),
    CONSTRAINT fk_ent_ped  FOREIGN KEY (id_pedido) REFERENCES pedido(id)
);


-- =========================================================
-- 2. Dados iniciais para teste
-- =========================================================

-- Usuário admin (login: admin@pizzaria.com / senha: 123)
INSERT INTO usuario(nome, email, senha)
VALUES ('Administrador', 'admin@pizzaria.com', '123');

-- Clientes de exemplo
INSERT INTO cliente(nome, email, telefone, endereco)
VALUES
    ('João Silva',   'joao@email.com',  '(54) 99999-0001', 'Rua das Flores, 10 - Passo Fundo/RS'),
    ('Maria Santos', 'maria@email.com', '(54) 99999-0002', 'Av. Brasil, 250 - Passo Fundo/RS');

-- Pizzas de exemplo
INSERT INTO pizza(nome, descricao, preco, tamanho, disponivel)
VALUES
    ('Mussarela',      'Molho de tomate, mussarela e orégano',               35.90, 'MEDIA',  TRUE),
    ('Calabresa',      'Molho de tomate, calabresa fatiada e cebola',         38.90, 'MEDIA',  TRUE),
    ('Portuguesa',     'Presunto, ovos, cebola, azeitona e pimentão',         42.90, 'GRANDE', TRUE),
    ('Quatro Queijos', 'Mussarela, provolone, catupiry e parmesão',           45.90, 'GRANDE', TRUE),
    ('Marguerita',     'Molho de tomate, mussarela fresca e manjericão',      40.00, 'MEDIA',  TRUE);

-- Resetar sequências
ALTER SEQUENCE cliente_id_seq RESTART WITH 3;
ALTER SEQUENCE pizza_id_seq   RESTART WITH 6;
