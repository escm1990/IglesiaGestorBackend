package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iglegestor.model.Cierre;

public interface CierreDao extends JpaRepository<Cierre, Integer>{
	
	//Listar registros de Cierre por filtro por fecha
	@Query("SELECT a FROM Cierre a WHERE a.fecha >= :fechaInicio AND a.fecha <= :fechaFinal")
	List<Cierre> obtenerRegistrosPorFecha(@Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);
	
	//Listar registros de Evento por filtro por usuario
	@Query("SELECT a FROM Cierre a WHERE a.iglesia.id = ?1")
	List<Cierre> obtenerRegistrosPorIglesia(int id_iglesia);
}
