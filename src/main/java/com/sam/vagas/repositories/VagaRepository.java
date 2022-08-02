package com.sam.vagas.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sam.vagas.entities.Vaga;

public interface VagaRepository extends JpaRepository<Vaga, UUID> {

	@Query("SELECT v FROM Vaga v join v.usuario u WHERE u.id = :id")
	List<Vaga> findAllVagasById(@Param("id") UUID id);
}
