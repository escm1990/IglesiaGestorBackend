package com.iglegestor.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iglegestor.security.model.Usuarios;

@Repository
public interface UsuariosDao extends JpaRepository<Usuarios, Integer>{
	
	Optional<Usuarios> findByUsuario(String usuario);
	
	boolean existsByUsuario(String usuario);
	
	boolean existsByCorreo(String correo);

	
}
