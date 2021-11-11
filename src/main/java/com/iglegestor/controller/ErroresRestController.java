package com.iglegestor.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.Errores;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Utilidades;

@RestController
@CrossOrigin(origins = "*")
public class ErroresRestController {

	@Autowired
	private ErroresDao repo;
	
	@GetMapping(value = "api/errores/listar")
	public List<Errores> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/errores/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Errores per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/errores/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Errores per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/errores/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/errores/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Errores> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "api/errores/listar/usuario/{usuario}", method = RequestMethod.GET)
	public List<Errores> listarPorUsuario(@PathVariable("usuario") String usuario){
		return repo.obtenerRegistrosPorUsuario(usuario);
	}
	
	@RequestMapping(value = "api/errores/listar/fecha/{fecha}", method = RequestMethod.GET)
	public List<Errores> listarPorFecha(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFecha(fechaInicio,fechaFinal);
	}
	
}
