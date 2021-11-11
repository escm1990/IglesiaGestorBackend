package com.iglegestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.security.model.Usuarios;
import com.iglegestor.security.repository.UsuariosDao;

@RestController
@CrossOrigin(origins = "*")
public class UsuariosRestController {
	
	@Autowired
	private UsuariosDao repo;

	@GetMapping(value = "api/usuarios/listar")
	public List<Usuarios> listar(){
		return repo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/usuarios/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Usuarios per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/usuarios/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Usuarios per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/usuarios/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}

}
