package com.br.fatec.AGIS.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.fatec.AGIS.model.Chamada;
import com.br.fatec.AGIS.model.pk.ChamadaPk;

@Repository
public interface ChamadaRepository extends JpaRepository<Chamada, ChamadaPk> {
	
	@Query(value = "select * from Chamada where dataChamada = ? and cod_turma = ?", nativeQuery = true)
	List<Chamada> selectAllByData(@Param("data") LocalDate data, @Param("cod") Long codTurma);
	
}
