ALTER TABLE Turma ADD metodo_avaliativo VARCHAR(50);

CREATE TABLE Secretario (
    id TEXT PRIMARY KEY,
    cpf VARCHAR(11) NOT NULL,
    senha TEXT NOT NULL,
    roles TEXT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    data_nasc DATE NOT NULL,
    email_pessoal VARCHAR(30) UNIQUE NOT NULL,
    email_corp VARCHAR(30) UNIQUE NOT NULL,
    situacao VARCHAR(20) NOT NULL
);