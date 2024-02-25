package com.br.fatec.AGIS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.fatec.AGIS.model.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {
	
	//PSEUDO LOGIN
	@Query(value = "select * from professor where cpf = ?", nativeQuery = true)
	Professor loginProf(@Param("cpf") String cpf);
}	
