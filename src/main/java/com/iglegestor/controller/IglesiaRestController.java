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

import com.iglegestor.model.Iglesia;
import com.iglegestor.repository.IglesiaDao;

@RestController
@CrossOrigin(origins = "*")
public class IglesiaRestController {

	@Autowired
	private IglesiaDao repo;
	
	@GetMapping(value = "api/iglesia/listar")
	public List<Iglesia> listar(){
		return repo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/iglesia/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Iglesia per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/iglesia/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Iglesia per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/iglesia/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
}
