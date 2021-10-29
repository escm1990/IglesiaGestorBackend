package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.Usuarios;

public interface UsuariosDao extends JpaRepository<Usuarios, Integer>{

	//Listar registros de Uusarios por filtro por rol
	@Query("SELECT a FROM Usuarios a WHERE a.rol.id = ?1")
	List<Usuarios> obtenerRegistrosPorRol(int id_rol);
	
}
