package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iglegestor.model.Evento;

public interface EventoDao extends JpaRepository<Evento, Integer>{
	
	//Listar registros de Evento por filtro por iglesia
	@Query("SELECT a FROM Evento a WHERE a.iglesia.id = ?1")
	List<Evento> obtenerRegistrosPorIglesia(int id_iglesia);
	
	//Listar registros de Evento por filtro por Tipo
	@Query("SELECT a FROM Evento a WHERE a.tipoEvento.id = ?1")
	List<Evento> obtenerRegistrosPorTipo(int id_tipo_evento);
	
	//Listar registros de auditoria por filtro por fecha
	 @Query("SELECT a FROM Evento a WHERE a.fecha >= :fechaInicio AND a.fecha <= :fechaFinal")
	List<Evento> obtenerRegistrosPorFecha(@Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);

}
