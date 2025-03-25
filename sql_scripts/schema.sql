-- Criação da tabela tipo_usuario
CREATE TABLE tipo_usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criação da tabela usuario
CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    tipo_usuario_id BIGINT,
    CONSTRAINT fk_usuario_tipo_usuario
      FOREIGN KEY (tipo_usuario_id)
      REFERENCES tipo_usuario(id)
);

-- Criação da tabela restaurante
CREATE TABLE restaurante (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255),
    tipo_cozinha VARCHAR(255),
    horario_funcionamento VARCHAR(255),
    dono_id BIGINT,
    CONSTRAINT fk_restaurante_dono
      FOREIGN KEY (dono_id)
      REFERENCES usuario(id)
);

-- Criação da tabela cardapio_item
CREATE TABLE cardapio_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    descricao VARCHAR(255),
    preco DECIMAL(10,2),
    disponivel_somente_local BOOLEAN,
    foto_path VARCHAR(255),
    restaurante_id BIGINT,
    CONSTRAINT fk_cardapio_item_restaurante
      FOREIGN KEY (restaurante_id)
      REFERENCES restaurante(id)
);
