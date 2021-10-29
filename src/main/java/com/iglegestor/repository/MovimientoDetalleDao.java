package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.MovimientoDetalle;

public interface MovimientoDetalleDao extends JpaRepository<MovimientoDetalle, Integer>{

	//Listar registros de Movimiento por filtro por movimiento
	@Query("SELECT a FROM MovimientoDetalle a WHERE a.movimiento.id = ?1")
	List<MovimientoDetalle> obtenerRegistrosPorMovimiento(int id_movimiento);
	
	//Listar registros de Movimiento por filtro por tipo movimiento
	@Query("SELECT a FROM MovimientoDetalle a WHERE a.tipoRegistroMovimiento.id = ?1")
	List<MovimientoDetalle> obtenerRegistrosPorTipoRegistroMovimiento(int id_tipo_registro_movimiento);
	
	//Listar registros de Movimiento por filtro por estado
	@Query("SELECT a FROM MovimientoDetalle a WHERE a.estado = ?1")
	List<MovimientoDetalle> obtenerRegistrosPorEstadoRegistro(String estadoRegistro);
}
