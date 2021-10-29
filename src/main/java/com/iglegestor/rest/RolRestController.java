package com.iglegestor.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.Rol;
import com.iglegestor.repository.RolDao;

@RestController
public class RolRestController {

	@Autowired
	private RolDao repo;
	
	@GetMapping(value = "api/rol/listar")
	public List<Rol> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/Rol/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Rol per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/rol/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Rol per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/rol/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
}
