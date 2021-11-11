package com.iglegestor.security.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iglegestor.security.model.Usuarios;
import com.iglegestor.security.repository.UsuariosDao;

@Service
@Transactional
public class UsuariosService {

	@Autowired
	private UsuariosDao repo;
	
	public Optional<Usuarios> getByUsuario(String usuario){
		return repo.findByUsuario(usuario);
	}
	
	public boolean existsByUsuario(String usuario){
		return repo.existsByUsuario(usuario);
	}
	
	public boolean existsByCorreo(String correo){
		return repo.existsByCorreo(correo);
	}
	
	public void save(Usuarios usuario) {
		repo.save(usuario);
	}
}
