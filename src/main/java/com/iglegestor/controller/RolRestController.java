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

import com.iglegestor.security.model.Rol;
import com.iglegestor.security.repository.RolDao;

@RestController
@CrossOrigin(origins = "*")
public class RolRestController {

	@Autowired
	private RolDao repo;
	
	@GetMapping(value = "api/rol/listar")
	public List<Rol> listar(){
		return repo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/rol/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Rol per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/rol/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Rol per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/rol/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
}
