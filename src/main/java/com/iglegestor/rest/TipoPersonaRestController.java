package com.iglegestor.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.TipoPersona;
import com.iglegestor.repository.TipoPersonaDao;

@RestController
@CrossOrigin(origins = "*")
public class TipoPersonaRestController {

	@Autowired
	private TipoPersonaDao repo;
	
	@GetMapping(value = "api/tipopersona/listar")
	public List<TipoPersona> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/tipopersona/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody TipoPersona per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/tipopersona/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody TipoPersona per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/tipopersona/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/tipopersona/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<TipoPersona> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
}
