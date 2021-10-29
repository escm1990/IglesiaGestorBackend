package com.iglegestor.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.CierreDetalle;
import com.iglegestor.repository.CierreDetalleDao;

@RestController
public class CierreDetalleRestController {

	@Autowired
	private CierreDetalleDao repo;
	
	@GetMapping(value = "api/cierredetalle/listar")
	public List<CierreDetalle> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/cierredetalle/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody CierreDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/cierredetalle/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody CierreDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/cierredetalle/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/cierredetalle/listar/cierre/{id_cierre}", method = RequestMethod.GET)
	public List<CierreDetalle> listarPorIdCierre(@PathVariable("id_iglesia") int id_cierre){
		return repo.obtenerRegistrosPorIdCierre(id_cierre);
	}
	
}
