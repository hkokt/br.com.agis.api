package com.br.fatec.AGIS.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.br.fatec.AGIS.model.Data;

@Repository
public interface DataRepository extends JpaRepository<Data, LocalDate> {
	
	@Query(value = "select * from data where descricao = ?", nativeQuery = true)
	Optional<Data> findByDesc(@Param("desc") String desc);
}