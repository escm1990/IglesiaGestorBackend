package com.iglegestor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.Movimiento;
import com.iglegestor.repository.MovimientoDao;

@RestController
@CrossOrigin(origins = "*")
public class MovimientoRestController {

	@Autowired
	private MovimientoDao repo;
	
	@GetMapping(value = "api/movimiento/listar")
	public List<Movimiento> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/movimiento/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Movimiento per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/movimiento/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Movimiento per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/movimiento/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}

	@RequestMapping(value = "api/movimiento/listar/cierre/{id_cierre}", method = RequestMethod.GET)
	public List<Movimiento> listarPorCierre(@PathVariable("id_cierre") int id_cierre){
		return repo.obtenerRegistrosPorCierre(id_cierre);
	}
	
	@RequestMapping(value = "api/movimiento/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Movimiento> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
}
