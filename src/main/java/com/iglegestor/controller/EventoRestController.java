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

import com.iglegestor.enums.Accion;
import com.iglegestor.enums.Tablas;
import com.iglegestor.model.Auditoria;
import com.iglegestor.model.Errores;
import com.iglegestor.model.Evento;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.EventoDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/evento")
@CrossOrigin(origins = "*")
public class EventoRestController {

	@Autowired
	private EventoDao repo;
	
	@Autowired
	private AuditoriaDao audit;

	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;
	
	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<Evento>
	 */
	@GetMapping(value = "/listar")
	public List<Evento> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Evento
	 * @param id -> Código del Evento
	 * @return ResponseEntity<Evento>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Evento> getEventoByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<Evento> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(Evento.class.toString(), "detalle",
					new NotFoundException("No se encontró Evento con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("No se encontró Evento con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Evento
	 * @param ev - Evento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody Evento ev) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(ev);

			au = new Auditoria(Tablas.EVENTO.toString(), Accion.INSERTAR.toString(), ev.getUltimoUsuario(),
					fechaRegistro, ev.toString(), ev.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Evento insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(Evento.class.toString(), "insertar", ex.getStackTrace().toString(),
					ev.getUltimoUsuario(), fechaRegistro, ev.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Evento no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Evento
	 * @param id - Identificador del Evento a actualizar
	 * @param ev - Objeto Evento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody Evento ev) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<Evento> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			Evento EventoActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			EventoActualizar.setDescripcion(ev.getDescripcion());
			EventoActualizar.setFecha(ev.getFecha());
			EventoActualizar.setIglesia_id(ev.getIglesia_id());
			EventoActualizar.setUltimoUsuario(ev.getUltimoUsuario());
			// Guadramos en la DB
			Evento evModificado = repo.save(EventoActualizar);

			au = new Auditoria(Tablas.EVENTO.toString(), Accion.MODIFICAR.toString(), ev.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), evModificado.toString(), evModificado.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Evento modificado"), HttpStatus.OK);
		} else {
			er = new Errores(Evento.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					ev.getUltimoUsuario(), fechaRegistro, ev.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Evento no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Evento
	 * @param id - Identificador del Evento a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<Evento> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.EVENTO.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("Evento eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(Evento.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("Evento no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Evento> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "/listar/tipo/{tipo}", method = RequestMethod.GET)
	public List<Evento> listarPorTipo(@PathVariable("tipo") int tipo){
		return repo.obtenerRegistrosPorTipo(tipo);
	}
	
	@RequestMapping(value = "/listar/fecha/{fecha}", method = RequestMethod.GET)
	public List<Evento> listarPorFecha(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFecha(fechaInicio,fechaFinal);
	}
	
}
