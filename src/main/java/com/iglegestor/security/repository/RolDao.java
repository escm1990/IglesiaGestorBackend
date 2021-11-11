package com.iglegestor.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iglegestor.enums.Roles;
import com.iglegestor.security.model.Rol;

@Repository
public interface RolDao extends JpaRepository<Rol, Integer>{

	Optional<Rol> findByNombre(Roles rolNombre);
	
}
