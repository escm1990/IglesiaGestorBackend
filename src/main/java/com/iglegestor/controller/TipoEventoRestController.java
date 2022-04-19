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
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.TipoEventoDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/tipoevento")
@CrossOrigin(origins = "*")
public class TipoEventoRestController {

	/**
	 * Sección de importaciones
	 */
	@Autowired
	private TipoEventoDao repo;

	@Autowired
	private AuditoriaDao audit;

	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;

	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<TipoEvento>
	 */
	@GetMapping(value = "/listar")
	public List<TipoEvento> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un tipo de evento
	 * @param id -> Código del Tipo de Evento
	 * @return ResponseEntity<TipoEvento>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<TipoEvento> getTipoEventoByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<TipoEvento> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(TipoEvento.class.toString(), "detalle",
					new NotFoundException("No se encontró TipoEvento con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("No se encontró TipoEvento con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Tipo de Evento
	 * @param te - Tipo de Evento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody TipoEvento te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(te);

			au = new Auditoria(Tablas.TIPO_EVENTO.toString(), Accion.INSERTAR.toString(), te.getUltimoUsuario(),
					fechaRegistro, te.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("TipoEvento insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(TipoEvento.class.toString(), "insertar", ex.getStackTrace().toString(),
					te.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoEvento no se pudo insertar"), HttpStatus.BAD_REQUEST);
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
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody TipoEvento te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<TipoEvento> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			TipoEvento tipoEventoActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			tipoEventoActualizar.setDescripcion(te.getDescripcion());
			tipoEventoActualizar.setEstado(te.getEstado());
			tipoEventoActualizar.setUltimoUsuario(te.getUltimoUsuario());
			// Guadramos en la DB
			TipoEvento teModificada = repo.save(tipoEventoActualizar);

			au = new Auditoria(Tablas.TIPO_EVENTO.toString(), Accion.MODIFICAR.toString(), te.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), teModificada.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("TipoEvento modificado"), HttpStatus.OK);
		} else {
			er = new Errores(TipoEvento.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					te.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoEvento no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Tipo de Evento
	 * @param id - Identificador del Tipo de Evento a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<TipoEvento> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.TIPO_EVENTO.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("TipoEvento eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(TipoEvento.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoEvento no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

}
