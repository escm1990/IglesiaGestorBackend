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
import com.iglegestor.model.Movimiento;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.MovimientoDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/movimiento")
@CrossOrigin(origins = "*")
public class MovimientoRestController {

	@Autowired
	private MovimientoDao repo;
	
	@Autowired
	private AuditoriaDao audit;

	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;

	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<Movimiento>
	 */
	@GetMapping(value = "/listar")
	public List<Movimiento> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Movimiento
	 * @param id -> Código del Movimiento
	 * @return ResponseEntity<Movimiento>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Movimiento> getMovimientoByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<Movimiento> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(Movimiento.class.toString(), "detalle",
					new NotFoundException("No se encontró Movimiento con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("No se encontró Movimiento con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Movimiento
	 * @param te - Movimiento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody Movimiento te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(te);

			au = new Auditoria(Tablas.MOVIMIENTO.toString(), Accion.INSERTAR.toString(), te.getUltimoUsuario(),
					fechaRegistro, te.toString(), te.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Movimiento insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(Movimiento.class.toString(), "insertar", ex.getStackTrace().toString(),
					te.getUltimoUsuario(), fechaRegistro, te.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Movimiento no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Movimiento
	 * @param id - Identificador del Movimiento a actualizar
	 * @param te - Objeto Movimiento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody Movimiento te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<Movimiento> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			Movimiento MovimientoActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			MovimientoActualizar.setCierre_id(te.getCierre_id());
			MovimientoActualizar.setDescripcion(te.getDescripcion());
			MovimientoActualizar.setFecha(te.getFecha());
			MovimientoActualizar.setIglesia_id(te.getIglesia_id());
			MovimientoActualizar.setUltimoUsuario(te.getUltimoUsuario());
			// Guadramos en la DB
			Movimiento teModificada = repo.save(MovimientoActualizar);

			au = new Auditoria(Tablas.MOVIMIENTO.toString(), Accion.MODIFICAR.toString(), te.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), teModificada.toString(), teModificada.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Movimiento modificado"), HttpStatus.OK);
		} else {
			er = new Errores(Movimiento.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					te.getUltimoUsuario(), fechaRegistro, te.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Movimiento no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Movimiento
	 * @param id - Identificador del Movimiento a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<Movimiento> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.MOVIMIENTO.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("Movimiento eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(Movimiento.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("Movimiento no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/listar/cierre/{id_cierre}", method = RequestMethod.GET)
	public List<Movimiento> listarPorCierre(@PathVariable("id_cierre") int id_cierre){
		return repo.obtenerRegistrosPorCierre(id_cierre);
	}
	
	@RequestMapping(value = "/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Movimiento> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
}
