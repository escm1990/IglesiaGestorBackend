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
import com.iglegestor.model.EventoDetalle;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.EventoDetalleDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/eventodetalle")
@CrossOrigin(origins = "*")
public class EventoDetalleRestController {

	@Autowired
	private EventoDetalleDao repo;
	
	@Autowired
	private AuditoriaDao audit;

	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;
	
	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<EventoDetalle>
	 */
	@GetMapping(value = "/listar")
	public List<EventoDetalle> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Evento Detalle
	 * @param id -> Código del Evento Detalle
	 * @return ResponseEntity<EventoDetalle>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<EventoDetalle> getEventoDetalleByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<EventoDetalle> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(EventoDetalle.class.toString(), "detalle",
					new NotFoundException("No se encontró EventoDetalle con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("No se encontró EventoDetalle con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Evento Detalle
	 * @param te - Evento Detalle a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody EventoDetalle ed) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(ed);

			au = new Auditoria(Tablas.EVENTO_DETALLE.toString(), Accion.INSERTAR.toString(), ed.getUltimoUsuario(),
					fechaRegistro, ed.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("EventoDetalle insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(EventoDetalle.class.toString(), "insertar", ex.getStackTrace().toString(),
					ed.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("EventoDetalle no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Evento Detalle
	 * @param id - Identificador del Evento Detalle a actualizar
	 * @param te - Objeto Evento Detalle a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody EventoDetalle ed) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<EventoDetalle> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			EventoDetalle EventoDetalleActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			EventoDetalleActualizar.setComentario(ed.getComentario());
			EventoDetalleActualizar.setEvento_id(ed.getEvento_id());
			EventoDetalleActualizar.setMiembro_id(ed.getMiembro_id());
			EventoDetalleActualizar.setUltimoUsuario(ed.getUltimoUsuario());
			// Guadramos en la DB
			EventoDetalle edModificado = repo.save(EventoDetalleActualizar);

			au = new Auditoria(Tablas.EVENTO_DETALLE.toString(), Accion.MODIFICAR.toString(), ed.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), edModificado.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("EventoDetalle modificado"), HttpStatus.OK);
		} else {
			er = new Errores(EventoDetalle.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					ed.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("EventoDetalle no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Evento Detalle
	 * @param id - Identificador del Evento Detalle a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<EventoDetalle> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.EVENTO_DETALLE.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("EventoDetalle eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(EventoDetalle.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("EventoDetalle no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/listar/evento/{id_evento}", method = RequestMethod.GET)
	public List<EventoDetalle> listarPorEvento(@PathVariable("id_evento") int id_evento){
		return repo.obtenerRegistrosPorIdEvento(id_evento);
	}
	
}
