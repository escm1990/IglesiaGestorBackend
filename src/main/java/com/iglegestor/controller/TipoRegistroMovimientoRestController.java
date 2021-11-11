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

import com.iglegestor.model.TipoRegistroMovimiento;
import com.iglegestor.repository.TipoRegistroMovimientoDao;

@RestController
@CrossOrigin(origins = "*")
public class TipoRegistroMovimientoRestController {

	@Autowired
	private TipoRegistroMovimientoDao repo;
	
	@GetMapping(value = "api/tiporegistromovimiento/listar")
	public List<TipoRegistroMovimiento> listar(){
		return repo.findAll();
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/tiporegistromovimiento/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody TipoRegistroMovimiento per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/tiporegistromovimiento/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody TipoRegistroMovimiento per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "api/tiporegistromovimiento/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/tiporegistromovimiento/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<TipoRegistroMovimiento> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	
}
