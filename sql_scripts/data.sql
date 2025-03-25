-- Inserindo na tabela tipo_usuario
INSERT INTO tipo_usuario (nome) VALUES
  ('Administrador'),
  ('Cliente'),
  ('Dono de Restaurante');

-- Inserindo na tabela usuario
INSERT INTO usuario (nome, email, senha, tipo_usuario_id) VALUES
  ('Administrador', 'admin@example.com', 'adminpass', 1);   -- tipo_usuario_id = 1 -> "Administrador"
