package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.CierreDetalle;

public interface CierreDetalleDao extends JpaRepository<CierreDetalle, Integer>{
	
	//Listar registros de auditoria por filtro por nombre de Tabla
	 @Query("SELECT a FROM CierreDetalle a WHERE a.cierre.id = ?1")
	List<CierreDetalle> obtenerRegistrosPorIdCierre(int cierre_id);
	
}
