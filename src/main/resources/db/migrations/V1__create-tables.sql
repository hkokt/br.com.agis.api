CREATE TABLE Curso (
    cod SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    enade NUMERIC(3, 2) NOT NULL,
    turno VARCHAR(10) NOT NULL
);

CREATE TABLE Disciplina (
    cod SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    qtd_aulas INT NOT NULL,
    semestre INT NOT NULL,
    cod_curso INT NOT NULL,
    CONSTRAINT fk_curso FOREIGN KEY (cod_curso) REFERENCES Curso(cod)
);

CREATE TABLE Grade_Curricular (
    cod SERIAL PRIMARY KEY,
    ano INT NOT NULL, 
    semestre INT NOT NULL,
    cod_curso INT NOT NULL,
    CONSTRAINT fk_curso_gc FOREIGN KEY (cod_curso) REFERENCES Curso(cod)
);

CREATE TABLE Professor (
	cod SERIAL PRIMARY KEY,
    titulacao VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nasc DATE NOT NULL,
    email_corp  VARCHAR(30) NOT NULL,
    email_pessoal  VARCHAR(30) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    situacao VARCHAR(20) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    UNIQUE(email_corp, email_pessoal)
);

CREATE TABLE Aluno (
    ra VARCHAR(15) PRIMARY KEY NOT NULL,
    data_conc_2grau DATE NOT NULL,
    data_limite_matricula DATE NOT NULL,
    data_matricula DATE NOT NULL,
    inst_conc_2grau VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100) NOT NULL,
    posicao_vestibular INT NOT NULL,
    pontuacao_vestibular INT NOT NULL,
    cod_curso INT NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    data_nasc DATE NOT NULL,
    email_corp  VARCHAR(30) NOT NULL,
    email_pessoal  VARCHAR(30) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    situacao VARCHAR(20) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    CONSTRAINT fk_curso_aluno FOREIGN KEY (cod_curso) REFERENCES Curso(cod),
    UNIQUE(cpf, email_corp, email_pessoal)
);

CREATE TABLE Turma (
    cod SERIAL PRIMARY KEY,
    dia_da_semana VARCHAR(20) NOT NULL,
    horario_fim TIME NOT NULL,
    horario_inicio TIME NOT NULL,
    situacao VARCHAR(20) NOT NULL,
    cod_disciplina INT NOT NULL,
    cod_grade INT NOT NULL,
    cod_professor INT NOT NULL,
    CONSTRAINT fk_disciplina_turma FOREIGN KEY (cod_disciplina) REFERENCES Disciplina(cod),
    CONSTRAINT fk_professor_turma FOREIGN KEY (cod_professor) REFERENCES Professor(cod),
    CONSTRAINT fk_grade_turma FOREIGN KEY (cod_grade) REFERENCES Grade_Curricular(cod)
);

CREATE TABLE Matricula (
    ano INT NOT NULL,
    semestre INT NOT NULL,
    nota_final NUMERIC(3, 2) NOT NULL,
    situacao VARCHAR(20) NOT NULL,
    aluno_ra VARCHAR(15) NOT NULL,
    cod_turma INT NOT NULL,
    PRIMARY KEY (ano, semestre, aluno_ra, cod_turma),
    CONSTRAINT fk_aluno_matricula FOREIGN KEY (aluno_ra) REFERENCES Aluno(ra),
    CONSTRAINT fk_turma_matricula FOREIGN KEY (cod_turma) REFERENCES Turma(cod)
);

CREATE TABLE Chamada (
    data_chamada DATE NOT NULL,
    qtd_faltas INT NOT NULL,
    aluno_ra VARCHAR(15) NOT NULL,
    cod_turma INT NOT NULL,
    PRIMARY KEY (data_chamada, aluno_ra, cod_turma),
    CONSTRAINT fk_aluno_chamada FOREIGN KEY (aluno_ra) REFERENCES Aluno(ra),
    CONSTRAINT fk_turma_chamada FOREIGN KEY (cod_turma) REFERENCES Turma(cod)
);

CREATE TABLE Data (
    cod SERIAL PRIMARY KEY,
    ano INT NOT NULL,
    data DATE NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    eh_feriado BOOLEAN NOT NULL
);