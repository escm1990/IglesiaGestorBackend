package com.iglegestor.rest;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.Cierre;
import com.iglegestor.repository.CierreDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Utilidades;

@RestController
public class CierreRestController {

	@Autowired
	private CierreDao repo;
	
	@GetMapping(value = "api/cierre/listar")
	public List<Cierre> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/cierre/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Cierre per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/cierre/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Cierre per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/cierre/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/cierre/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Cierre> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "api/cierre/listar/fecha/{fecha}", method = RequestMethod.GET)
	public List<Cierre> listarPorFecha(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFecha(fechaInicio,fechaFinal);
	}
}
