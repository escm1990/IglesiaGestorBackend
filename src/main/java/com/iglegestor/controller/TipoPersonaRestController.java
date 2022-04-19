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
import com.iglegestor.model.TipoEvento;
import com.iglegestor.model.TipoPersona;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.TipoPersonaDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/tipopersona")
@CrossOrigin(origins = "*")
public class TipoPersonaRestController {

	/**
	 * Sección de importaciones
	 */
	@Autowired
	private TipoPersonaDao repo;
	@Autowired
	private AuditoriaDao audit;
	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;

	/**
	 * Retorna un listado de Tipos de Persona
	 * 
	 * @return List<TipoPersona>
	 */
	@GetMapping(value = "/listar")
	public List<TipoPersona> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un tipo de persona
	 * 
	 * @param id -> Código del Tipo de persona
	 * @return ResponseEntity<TipoPersona>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<TipoPersona> getTipoPersonaByID(@PathVariable int id)
			throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<TipoPersona> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error
		else {
			er = new Errores(TipoEvento.class.toString(), "detalle",
					new NotFoundException("No se encontró TipoPersona con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("Not found TipoPersona by id: " + id);
		}
	}

	/**
	 * Método que guarda un Tipo de Persona
	 * 
	 * @param tp - Tipo de Persona a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody TipoPersona tp) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(tp);

			au = new Auditoria(Tablas.TIPO_PERSONA.toString(), Accion.INSERTAR.toString(), tp.getUltimoUsuario(),
					fechaRegistro, tp.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("TipoPersona insertada"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(TipoPersona.class.toString(), "insertar", ex.getStackTrace().toString(),
					tp.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoPersona no se pudo insertar"), HttpStatus.BAD_REQUEST);

		}
	}

	/**
	 * Método para modificar un Tipo de Persona
	 * 
	 * @param id - Identificador del Tipo de Persona a actualizar
	 * @param tp - Objeto Tipo de Persona a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody TipoPersona tp) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<TipoPersona> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			TipoPersona tipoPersonaActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			tipoPersonaActualizar.setDescripcion(tp.getDescripcion());
			tipoPersonaActualizar.setEstado(tp.getEstado());
			tipoPersonaActualizar.setUltimoUsuario(tp.getUltimoUsuario());
			// Guadramos en la DB
			TipoPersona teModificada = repo.save(tipoPersonaActualizar);

			au = new Auditoria(Tablas.TIPO_PERSONA.toString(), Accion.MODIFICAR.toString(), tp.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), teModificada.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("Iglesia modificada"), HttpStatus.OK);
		} else {
			er = new Errores(TipoPersona.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					tp.getUltimoUsuario(), fechaRegistro, 0);
			return new ResponseEntity(new Mensaje("TipoPersona no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Tipo de Evento
	 * @param id - Identificador del Tipo de Evento a actualizar
	 * @param te - Objeto Tipo de Evento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<TipoPersona> i = repo.findById(id);
		try {
			au = new Auditoria(Tablas.TIPO_PERSONA.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), 0);
			audit.save(au);
			repo.deleteById(id);

			return new ResponseEntity(new Mensaje("TipoPersona eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(TipoPersona.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoPersona no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

}
