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
import com.iglegestor.model.Iglesia;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.IglesiaDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/iglesia")
@CrossOrigin(origins = "*")
public class IglesiaRestController {

	@Autowired
	private IglesiaDao repo;

	@Autowired
	private AuditoriaDao audit;

	Auditoria au;

	@Autowired
	private ErroresDao err;

	Errores er;

	/**
	 * Retorna un listado de Iglesias
	 * 
	 * @return List<Iglesia>
	 */
	@GetMapping(value = "/listar")
	public List<Iglesia> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de una iglesia
	 * 
	 * @param id -> Código del Iglesia
	 * @return ResponseEntity<TipoEvento>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Iglesia> getUserByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<Iglesia> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error
		else {
			er = new Errores(Iglesia.class.toString(), "detalle",
					new NotFoundException("No se encontró Iglesia con el id: " + id).toString(), "", fechaRegistro, 0);
			err.save(er);
			throw new NotFoundException("No se encontró la iglesia con id: " + id);
		}
	}

	/**
	 * Método que guarda un Iglesia
	 * 
	 * @param te - Iglesia a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody Iglesia igle) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(igle);
			au = new Auditoria(Tablas.IGLESIA.toString(), Accion.INSERTAR.toString(), igle.getUltimoUsuario(),
					fechaRegistro, igle.toString(), igle.getId());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Iglesia insertada"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(Iglesia.class.toString(), "insertar", ex.getStackTrace().toString(),
					igle.getUltimoUsuario(), fechaRegistro, igle.getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("Iglesia no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * Método para modificar un Iglesia
	 * 
	 * @param id - Identificador del Iglesia a actualizar
	 * @param te - Objeto Iglesia a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody Iglesia igle) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<Iglesia> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			Iglesia iglesiaActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			iglesiaActualizar.setNombre(igle.getNombre());
			iglesiaActualizar.setDireccion(igle.getDireccion());
			iglesiaActualizar.setCorreo(igle.getCorreo());
			iglesiaActualizar.setFechaFundacion(igle.getFechaFundacion());
			iglesiaActualizar.setEstado(igle.getEstado());
			iglesiaActualizar.setLogo(igle.getLogo());
			iglesiaActualizar.setPais(igle.getPais());
			iglesiaActualizar.setTelefono(igle.getTelefono());
			iglesiaActualizar.setUltimoUsuario(igle.getUltimoUsuario());
			// Guadramos en la DB
			Iglesia iglesiaModificada = repo.save(iglesiaActualizar);

			au = new Auditoria(Tablas.IGLESIA.toString(), Accion.MODIFICAR.toString(),
					iglesiaModificada.getUltimoUsuario(), Utilidades.fechaMilisegundos(new Date()),
					iglesiaModificada.toString(), iglesiaModificada.getId());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Iglesia modificada"), HttpStatus.OK);
		} else {
			er = new Errores(Iglesia.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					igle.getUltimoUsuario(), fechaRegistro, igle.getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("Iglesia no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Tipo de Evento
	 * 
	 * @param id - Identificador del Tipo de Evento a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {

		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<Iglesia> i = repo.findById(id);
		try {
			au = new Auditoria(Tablas.IGLESIA.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
			repo.deleteById(id);
			return new ResponseEntity(new Mensaje("Iglesia eliminada"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(Iglesia.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("Iglesia no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

}
