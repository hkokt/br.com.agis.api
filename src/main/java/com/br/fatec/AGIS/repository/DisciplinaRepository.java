package com.br.fatec.AGIS.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.fatec.AGIS.model.Disciplina;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long>{
	
	@Query(value = "select * from disciplina where cod_curso = ?", nativeQuery = true)
	List<Disciplina> selectAllCurso(@Param("cod_curso") Long codCurso);
}
