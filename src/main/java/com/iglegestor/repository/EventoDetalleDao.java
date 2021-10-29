package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.EventoDetalle;

public interface EventoDetalleDao extends JpaRepository<EventoDetalle, Integer>{
	
	//Listar registros de EventoDetalle por filtro por evento
	@Query("SELECT a FROM EventoDetalle a WHERE a.evento.id = ?1")
	List<EventoDetalle> obtenerRegistrosPorIdEvento(int id_evento);
	
}
