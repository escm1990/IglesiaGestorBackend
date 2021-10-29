package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.Movimiento;

public interface MovimientoDao extends JpaRepository<Movimiento, Integer>{
	
	//Listar registros de Movimiento por filtro por iglesia
	@Query("SELECT a FROM Movimiento a WHERE a.iglesia.id = ?1")
	List<Movimiento> obtenerRegistrosPorIglesia(int id_iglesia);
	
	//Listar registros de Movimiento por filtro por cierre
	@Query("SELECT a FROM Movimiento a WHERE a.cierre.id = ?1")
	List<Movimiento> obtenerRegistrosPorCierre(int id_cierre);
}
