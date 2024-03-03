ALTER TABLE Data DROP COLUMN  cod;
ALTER TABLE Data ADD PRIMARY KEY (data);

CREATE OR REPLACE FUNCTION matricula_semestre_um()
RETURNS TRIGGER AS $$
DECLARE
    sem INTEGER;
BEGIN
    IF EXTRACT(MONTH FROM CURRENT_DATE) > 6 THEN
        sem := 2;
    ELSE
        sem := 1;
    END IF;

    INSERT INTO matricula(ano, semestre, nota_final, situacao, cod_turma, aluno_ra)
    SELECT
        EXTRACT(YEAR FROM CURRENT_DATE),
        sem,
        0.00,
        'cursando',
        t.cod,
        NEW.ra
    FROM
        curso c
        INNER JOIN disciplina d ON d.cod_curso = c.cod
        INNER JOIN grade_curricular gc ON gc.cod_curso = c.cod
        INNER JOIN turma t ON t.cod_disciplina = d.cod
    WHERE
        d.semestre = 1 	
        AND gc.cod_curso = NEW.cod_curso
        AND gc.ano = EXTRACT(YEAR FROM CURRENT_DATE)
        AND gc.semestre = sem;
        
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER matricula_semestre_um
AFTER INSERT ON aluno
FOR EACH ROW
EXECUTE FUNCTION matricula_semestre_um();