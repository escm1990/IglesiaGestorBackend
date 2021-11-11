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

import com.iglegestor.model.Evento;
import com.iglegestor.repository.EventoDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Utilidades;

@RestController
@CrossOrigin(origins = "*")
public class EventoRestController {

	@Autowired
	private EventoDao repo;
	
	@GetMapping(value = "api/evento/listar")
	public List<Evento> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/evento/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Evento per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/evento/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Evento per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/evento/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}

	@RequestMapping(value = "api/evento/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Evento> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "api/evento/listar/tipo/{tipo}", method = RequestMethod.GET)
	public List<Evento> listarPorTipo(@PathVariable("tipo") int tipo){
		return repo.obtenerRegistrosPorTipo(tipo);
	}
	
	@RequestMapping(value = "api/evento/listar/fecha/{fecha}", method = RequestMethod.GET)
	public List<Evento> listarPorFecha(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFecha(fechaInicio,fechaFinal);
	}
	
}
