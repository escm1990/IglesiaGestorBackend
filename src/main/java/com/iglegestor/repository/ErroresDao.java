package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iglegestor.model.Errores;

public interface ErroresDao extends JpaRepository<Errores, Integer>{
	
	//Listar registros de Errores por filtro por usuario
	 @Query("SELECT a FROM Errores a WHERE a.usuario = ?1")
	List<Errores> obtenerRegistrosPorUsuario(String usuario);
	
	//Listar registros de Errores por filtro por fecha
	 @Query("SELECT a FROM Errores a WHERE a.fecha >= :fechaInicio AND a.fecha <= :fechaFinal")
	List<Errores> obtenerRegistrosPorFecha(@Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);
	 
	//Listar registros de Evento por filtro por usuario
	@Query("SELECT a FROM Errores a WHERE a.iglesia_id = ?1")
	List<Errores> obtenerRegistrosPorIglesia(int id_iglesia);
}
