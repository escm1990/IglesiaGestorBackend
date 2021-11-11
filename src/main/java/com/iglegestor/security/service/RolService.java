package com.iglegestor.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iglegestor.enums.Roles;
import com.iglegestor.security.model.Rol;
import com.iglegestor.security.repository.RolDao;

@Service
@Transactional
public class RolService {
	
	@Autowired
	private RolDao repo;
	
	public Optional<Rol> getByNombre(Roles nombre){
		return repo.findByNombre(nombre);
	}
	
	public void save(Rol rol) {
		repo.save(rol);
	}

}
