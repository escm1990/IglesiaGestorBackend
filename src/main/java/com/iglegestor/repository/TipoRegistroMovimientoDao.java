package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.TipoRegistroMovimiento;

public interface TipoRegistroMovimientoDao extends JpaRepository<TipoRegistroMovimiento, Integer>{

	//Listar registros de TipoRegistroMovimiento por filtro por iglesia
	@Query("SELECT a FROM TipoRegistroMovimiento a WHERE a.iglesia.id = ?1")
	List<TipoRegistroMovimiento> obtenerRegistrosPorIglesia(int id_iglesia);
}
