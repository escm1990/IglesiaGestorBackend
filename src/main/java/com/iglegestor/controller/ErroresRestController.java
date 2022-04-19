package com.iglegestor.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/errores")
@CrossOrigin(origins = "*")
public class ErroresRestController {

	@Autowired
	private ErroresDao repo;
	
	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<Errores>
	 */
	@GetMapping(value = "/listar")
	public List<Errores> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Errores
	 * @param id -> Código del Errores
	 * @return ResponseEntity<Errores>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Errores> getErroresByID(@PathVariable int id) throws NotFoundException, ParseException {
		// Es un Optional<T>
		Optional<Errores> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			throw new NotFoundException("No se encontró Errores con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Errores
	 * @param te - Errores a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody Errores te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			te.setFecha(fechaRegistro);
			repo.save(te);

			return new ResponseEntity(new Mensaje("Errores insertado"), HttpStatus.OK);
		} catch (Exception ex) {

			return new ResponseEntity(new Mensaje("Errores no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Errores> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "/listar/usuario/{usuario}", method = RequestMethod.GET)
	public List<Errores> listarPorUsuario(@PathVariable("usuario") String usuario){
		return repo.obtenerRegistrosPorUsuario(usuario);
	}
	
	@RequestMapping(value = "/listar/fecha/{fecha}", method = RequestMethod.GET)
	public List<Errores> listarPorFecha(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFecha(fechaInicio,fechaFinal);
	}
	
}
