package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iglegestor.model.Auditoria;

public interface AuditoriaDao extends JpaRepository<Auditoria, Integer>{
	
	//Listar registros de auditoria por filtro por nombre de Tabla
	 @Query("SELECT a FROM Auditoria a WHERE a.tabla = ?1")
	List<Auditoria> obtenerRegistrosPorTabla(String tabla);
	
	//Listar registros de auditoria por filtro por accion
	 @Query("SELECT a FROM Auditoria a WHERE a.accion = ?1")
	List<Auditoria> obtenerRegistrosPorAccion(String accion);
	
	//Listar registros de auditoria por filtro por usuario
	 @Query("SELECT a FROM Auditoria a WHERE a.usuario = ?1")
	List<Auditoria> obtenerRegistrosPorUsuario(String usuario);
	
	//Listar registros de auditoria por filtro por fecha
	 @Query("SELECT a FROM Auditoria a WHERE a.fecha >= :fechaInicio AND a.fecha <= :fechaFinal")
	List<Auditoria> obtenerRegistrosPorFecha(@Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);
	 
	//Listar registros de Evento por filtro por usuario
	@Query("SELECT a FROM Auditoria a WHERE a.iglesia.id = ?1")
	List<Auditoria> obtenerRegistrosPorIglesia(int id_iglesia);

}
