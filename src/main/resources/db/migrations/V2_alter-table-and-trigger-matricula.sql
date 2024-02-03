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