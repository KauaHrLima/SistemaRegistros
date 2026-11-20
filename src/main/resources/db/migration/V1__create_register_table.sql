-- Active: 1789165062655@@localhost@5432@UserRegister@public
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome_usuario VARCHAR(120) NOT NULL,
    email_usuario VARCHAR(120) UNIQUE NOT NULL,
    telefone_usuario VARCHAR(20) NOT NULL,
    cpf_usuario VARCHAR(20) UNIQUE NOT NULL,
    rg_usuario VARCHAR(20) UNIQUE NOT NULL,
    criado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    atualizado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE OR REPLACE FUNCTION atualizar_timestamp()
RETURNS TRIGGER AS $$
BEGIN
    NEW.atualizado_em = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trg_usuario_atualizado_em
BEFORE UPDATE ON usuarios
FOR EACH ROW
EXECUTE FUNCTION atualizar_timestamp();

CREATE TABLE categoria_produto (
    id SERIAL PRIMARY KEY,
    codigo_categoria VARCHAR(20) NOT NULL UNIQUE,
    nome_categoria VARCHAR(100) NOT NULL
);

CREATE TABLE produto (
    id SERIAL PRIMARY KEY,
    codigo_produto VARCHAR(50) NOT NULL UNIQUE,
    nome_produto VARCHAR(150) NOT NULL,
    categoria_id INTEGER NOT NULL,
    quantidade_produto INTEGER NOT NULL DEFAULT 0 CHECK (quantidade_produto >= 0),
    CONSTRAINT fk_produto_categoria FOREIGN KEY (categoria_id) REFERENCES categoria_produto (id),
    cadastrado_em TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);