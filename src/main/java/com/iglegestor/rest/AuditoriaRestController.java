package com.iglegestor.rest;

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

import com.iglegestor.model.Auditoria;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Utilidades;

@RestController
@CrossOrigin(origins = "*")
public class AuditoriaRestController {
	
	@Autowired
	private AuditoriaDao repo;
	
	@GetMapping(value = "api/auditoria/listar")
	public List<Auditoria> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/auditoria/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Auditoria per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/auditoria/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Auditoria per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/auditoria/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}

	@RequestMapping(value = "api/auditoria/listar/tabla/{tabla}", method = RequestMethod.GET)
	public List<Auditoria> listarPorTabla(@PathVariable("tabla") String tabla){
		return repo.obtenerRegistrosPorTabla(tabla);
	}
	
	@RequestMapping(value = "api/auditoria/listar/accion/{accion}", method = RequestMethod.GET)
	public List<Auditoria> listarPorAccion(@PathVariable("accion") String accion){
		return repo.obtenerRegistrosPorAccion(accion);
	}
	
	@RequestMapping(value = "api/auditoria/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Auditoria> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "api/auditoria/listar/usuario/{usuario}", method = RequestMethod.GET)
	public List<Auditoria> listarPorUsuario(@PathVariable("usuario") String usuario){
		return repo.obtenerRegistrosPorUsuario(usuario);
	}
	
	@RequestMapping(value = "api/auditoria/listar/fecha/{fecha}", method = RequestMethod.GET)
	public List<Auditoria> listarPorFecha(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFecha(fechaInicio,fechaFinal);
	}
}
