package com.iglegestor.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.iglegestor.enums.EstadoRegistro;
import com.iglegestor.enums.Roles;
import com.iglegestor.security.model.Rol;
import com.iglegestor.security.service.RolService;

@Component
public class CrearRoles implements CommandLineRunner{

	@Autowired
	RolService repo;
	
	@Override
	public void run(String... args) throws Exception {
	
		Rol roleAdmin = new Rol(Roles.ROLE_ADMIN);
		Rol roleUser = new Rol(Roles.ROLE_USER);
		Rol roleConsulta = new Rol(Roles.ROLE_CONSULTA);
		/*
		repo.save(roleAdmin);
		repo.save(roleUser);
		repo.save(roleConsulta);
		 */

	 
	}

}
