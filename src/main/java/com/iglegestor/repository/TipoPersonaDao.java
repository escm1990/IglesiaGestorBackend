package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.TipoPersona;

public interface TipoPersonaDao extends JpaRepository<TipoPersona, Integer>{

	//Listar registros de TipoPersona por filtro por iglesia
	@Query("SELECT a FROM TipoPersona a WHERE a.iglesia.id = ?1")
	List<TipoPersona> obtenerRegistrosPorIglesia(int id_iglesia);
}
