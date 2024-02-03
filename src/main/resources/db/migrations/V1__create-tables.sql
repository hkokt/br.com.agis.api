CREATE TABLE Curso (
    cod BIGSERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    sigla VARCHAR(10) NOT NULL,
    enade NUMERIC(3, 2) NOT NULL,
    turno VARCHAR(10) NOT NULL
);

CREATE TABLE Disciplina (
    cod BIGSERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(100) NOT NULL,
    qtd_aulas INT NOT NULL,
    semestre INT NOT NULL,
    cod_curso BIGINT NOT NULL,
    CONSTRAINT fk_curso FOREIGN KEY (cod_curso) REFERENCES Curso(cod)
);

CREATE TABLE Grade_Curricular (
    cod BIGSERIAL PRIMARY KEY NOT NULL,
    ano INT NOT NULL,
    semestre INT NOT NULL,
    cod_curso BIGINT NOT NULL,
    CONSTRAINT fk_curso_gc FOREIGN KEY (cod_curso) REFERENCES Curso(cod)
);

CREATE TABLE Usuario (
    cpf VARCHAR(11) PRIMARY KEY NOT NULL,
    data_nasc DATE NOT NULL,
    email_corp VARCHAR(30) NOT NULL,
    email_pessoal VARCHAR(30) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    situacao VARCHAR(20) NOT NULL,
    senha VARCHAR(30) NOT NULL
);

CREATE TABLE Professor (
    cod BIGSERIAL PRIMARY KEY NOT NULL,
    titulacao VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    CONSTRAINT fk_usuario_professor FOREIGN KEY (cpf) REFERENCES Usuario(cpf)
);

CREATE TABLE Aluno (
    ra VARCHAR(15) PRIMARY KEY NOT NULL,
    data_conc_2grau DATE NOT NULL,
    data_limite_matricula DATE NOT NULL,
    data_matricula DATE NOT NULL,
    inst_conc_2grau VARCHAR(100) NOT NULL,
    nome_social VARCHAR(100) NOT NULL,
    pos_vestibular INT NOT NULL,
    pt_vestibular INT NOT NULL,
    cod_curso BIGINT NOT NULL,
    cpf VARCHAR(11) NOT NULL,
    CONSTRAINT fk_curso_aluno FOREIGN KEY (cod_curso) REFERENCES Curso(cod),
    CONSTRAINT fk_usuario_aluno FOREIGN KEY (cpf) REFERENCES Usuario(cpf)
);

CREATE TABLE Turma (
    cod BIGSERIAL PRIMARY KEY NOT NULL,
    dia_da_semana VARCHAR(20) NOT NULL,
    horario_fim TIME NOT NULL,
    horario_inicio TIME NOT NULL,
    situacao VARCHAR(20) NOT NULL,
    cod_disciplina BIGINT NOT NULL,
    cod_grade BIGINT NOT NULL,
    cod_professor BIGINT NOT NULL,
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
    cod_turma BIGINT NOT NULL,
    CONSTRAINT fk_aluno_matricula FOREIGN KEY (aluno_ra) REFERENCES Aluno(ra),
    CONSTRAINT fk_turma_matricula FOREIGN KEY (cod_turma) REFERENCES Turma(cod)
);

CREATE TABLE Chamada (
    data_chamada DATE NOT NULL,
    qtd_faltas INT NOT NULL,
    aluno_ra VARCHAR(15) NOT NULL,
    cod_turma BIGINT NOT NULL,
    PRIMARY KEY (data_chamada, aluno_ra, cod_turma),
    CONSTRAINT fk_aluno_chamada FOREIGN KEY (aluno_ra) REFERENCES Aluno(ra),
    CONSTRAINT fk_turma_chamada FOREIGN KEY (cod_turma) REFERENCES Turma(cod)
);

CREATE TABLE Data (
    cod BIGSERIAL PRIMARY KEY NOT NULL,
    ano INT NOT NULL,
    data DATE NOT NULL,
    descricao VARCHAR(100) NOT NULL,
    eh_feriado BOOLEAN NOT NULL
);

ALTER TABLE Data DROP COLUMN  cod;
ALTER TABLE Data ADD PRIMARY KEY (data);

CREATE OR REPLACE FUNCTION matricula_semestre_um()
RETURNS TRIGGER AS $$
DECLARE
    semestre INTEGER;
BEGIN
    IF EXTRACT(MONTH FROM CURRENT_DATE) > 6 THEN
        semestre := 2;
    ELSE
        semestre := 1;
    END IF;

    INSERT INTO Matricula(ano, semestre, nota_final, situacao, aluno_ra, cod_turma)
    SELECT
        EXTRACT(YEAR FROM CURRENT_DATE),
        semestre,
        0.00,
        'cursando',
        a.ra,
        t.cod
    FROM
        inserted a
        INNER JOIN Curso c ON a.cod_curso = c.cod
        INNER JOIN Disciplina d ON d.cod_curso = c.cod
        INNER JOIN Grade_Curricular gc ON gc.cod_curso = c.cod
        INNER JOIN Turma t ON t.cod_disciplina = d.cod
    WHERE
        d.semestre = 1 
        AND gc.ano = EXTRACT(YEAR FROM CURRENT_DATE)
        AND gc.semestre = semestre;
        
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER matricula_semestre_um
AFTER INSERT ON Aluno
FOR EACH ROW
EXECUTE FUNCTION matricula_semestre_um();