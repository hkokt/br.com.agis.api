package com.br.fatec.AGIS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.fatec.AGIS.model.GradeCurricular;

@Repository
public interface GradeCurricularRepository extends JpaRepository<GradeCurricular, Long> {
	
}
