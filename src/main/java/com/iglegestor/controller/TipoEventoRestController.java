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

import com.iglegestor.model.TipoEvento;
import com.iglegestor.repository.TipoEventoDao;

@RestController
@CrossOrigin(origins = "*")
public class TipoEventoRestController {

	@Autowired
	private TipoEventoDao repo;
	
	@GetMapping(value = "api/tipoevento/listar")
	public List<TipoEvento> listar(){
		return repo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/tipoevento/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody TipoEvento per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/tipoevento/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody TipoEvento per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/tipoevento/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/tipoevento/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<TipoEvento> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
}
