package com.br.fatec.AGIS.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.fatec.AGIS.model.Data;

@Repository
public interface DataRepository extends JpaRepository<Data, LocalDate> {

}
