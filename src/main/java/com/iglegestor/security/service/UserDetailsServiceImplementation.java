package com.iglegestor.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.iglegestor.security.model.UsuarioPrincipal;
import com.iglegestor.security.model.Usuarios;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{

	@Autowired
	UsuariosService uService;	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuarios usuario = uService.getByUsuario(username).get();
		return UsuarioPrincipal.build(usuario);
	}

}
