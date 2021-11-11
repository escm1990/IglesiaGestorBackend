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

import com.iglegestor.model.EventoDetalle;
import com.iglegestor.repository.EventoDetalleDao;

@RestController
@CrossOrigin(origins = "*")
public class EventoDetalleRestController {

	@Autowired
	private EventoDetalleDao repo;
	
	@GetMapping(value = "api/eventodetalle/listar")
	public List<EventoDetalle> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/eventodetalle/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody EventoDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/eventodetalle/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody EventoDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/eventodetalle/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/eventodetalle/listar/evento/{id_evento}", method = RequestMethod.GET)
	public List<EventoDetalle> listarPorEvento(@PathVariable("id_evento") int id_evento){
		return repo.obtenerRegistrosPorIdEvento(id_evento);
	}
	
}
