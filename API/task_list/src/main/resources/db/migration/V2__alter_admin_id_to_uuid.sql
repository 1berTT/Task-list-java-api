-- Altera a coluna id de SERIAL para UUID
-- Primeiro, remove a constraint de primary key
ALTER TABLE admin DROP CONSTRAINT admin_pkey;

-- Remove a coluna id antiga
ALTER TABLE admin DROP COLUMN id;

-- Adiciona a nova coluna id como UUID
ALTER TABLE admin ADD COLUMN id UUID PRIMARY KEY DEFAULT gen_random_uuid();

-- Cria índice para melhor performance
CREATE INDEX idx_admin_id ON admin(id);
