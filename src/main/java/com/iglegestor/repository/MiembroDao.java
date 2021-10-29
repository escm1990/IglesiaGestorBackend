package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iglegestor.model.Miembro;

public interface MiembroDao extends JpaRepository<Miembro, Integer>{

	//Listar registros de Miembro por filtro por iglesia
	@Query("SELECT a FROM Miembro a WHERE a.iglesia.id = ?1")
	List<Miembro> obtenerRegistrosPorIglesia(int id_iglesia);

	//Listar registros de Miembro por filtro por sexo
	@Query("SELECT a FROM Miembro a WHERE a.sexo = ?1")
	List<Miembro> obtenerRegistrosPorSexo(String sexo);
	
	//Listar registros de Miembro por filtro por estado civil
	@Query("SELECT a FROM Miembro a WHERE a.estadoCivil = ?1")
	List<Miembro> obtenerRegistrosPorEstadoCivil(String estadoCivil);
	
	//Listar registros de Miembro por filtro por Nombre
	@Query("SELECT a FROM Miembro a WHERE CONCAT(TRIM(a.nombre), ' ', TRIM(a.apellido)) like %:criterio%")
	List<Miembro> obtenerRegistrosPorNombreCompleto(@Param("criterio") String criterio);
	
	//Listar registros de miembros por filtro por fecha de Nacimiento
	 @Query("SELECT a FROM Miembro a WHERE a.fechaNacimiento >= :fechaInicio AND a.fechaNacimiento <= :fechaFinal")
	List<Miembro> obtenerRegistrosPorFechaNacimiento(@Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);
	 
	//Listar registros de miembros por filtro por fecha de Bautismo
	 @Query("SELECT a FROM Miembro a WHERE a.fechaBautismo >= :fechaInicio AND a.fechaBautismo <= :fechaFinal")
	List<Miembro> obtenerRegistrosPorFechaBautismo(@Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);

	 //Listar registros de miembros por filtro por fecha de Conversion
	 @Query("SELECT a FROM Miembro a WHERE a.fechaConversion>= :fechaInicio AND a.fechaConversion <= :fechaFinal")
	List<Miembro> obtenerRegistrosPorFechaConversion(@Param("fechaInicio") Long fechaInicio, @Param("fechaFinal") Long fechaFinal);

	//Listar registros de Miembro por filtro por tipo persona
	@Query("SELECT a FROM Miembro a WHERE a.tipoPersona.id = ?1")
	List<Miembro> obtenerRegistrosPorTipoPersona(int id_tipo_persona);
}
