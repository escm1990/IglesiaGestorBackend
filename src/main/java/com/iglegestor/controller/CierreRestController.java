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
import com.iglegestor.model.Cierre;
import com.iglegestor.model.Errores;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.CierreDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/cierre")

@CrossOrigin(origins = "*")
public class CierreRestController {

	/**
	 * Sección de importaciones
	 */
	@Autowired
	private CierreDao repo;

	@Autowired
	private AuditoriaDao audit;

	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;
	
	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<Cierre>
	 */
	@GetMapping(value = "/listar")
	public List<Cierre> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Cierre
	 * @param id -> Código del Cierre
	 * @return ResponseEntity<Cierre>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Cierre> getCierreByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<Cierre> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(Cierre.class.toString(), "detalle",
					new NotFoundException("No se encontró Cierre con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("No se encontró Cierre con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Cierre
	 * @param te - Cierre a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody Cierre te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(te);

			au = new Auditoria(Tablas.CIERRE.toString(), Accion.INSERTAR.toString(), te.getUltimoUsuario(),
					fechaRegistro, te.toString(), te.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Cierre insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(Cierre.class.toString(), "insertar", ex.getStackTrace().toString(),
					te.getUltimoUsuario(), fechaRegistro, te.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Cierre no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Cierre
	 * @param id - Identificador del Cierre a actualizar
	 * @param te - Objeto Cierre a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody Cierre ci) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<Cierre> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			Cierre CierreActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			CierreActualizar.setDescripcion(ci.getDescripcion());
			CierreActualizar.setFechaEjecucion(fechaRegistro);
			CierreActualizar.setTotal(ci.getTotal());
			CierreActualizar.setIglesia_id(ci.getIglesia_id());
			CierreActualizar.setUltimoUsuario(ci.getUltimoUsuario());
			// Guadramos en la DB
			Cierre teModificada = repo.save(CierreActualizar);

			au = new Auditoria(Tablas.CIERRE.toString(), Accion.MODIFICAR.toString(), ci.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), teModificada.toString(), teModificada.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Cierre modificado"), HttpStatus.OK);
		} else {
			er = new Errores(Cierre.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					ci.getUltimoUsuario(), fechaRegistro, ci.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Cierre no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Cierre
	 * @param id - Identificador del Cierre a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<Cierre> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.CIERRE.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("Cierre eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(Cierre.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("Cierre no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Cierre> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "/listar/fecha/{fecha}", method = RequestMethod.GET)
	public List<Cierre> listarPorFecha(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFecha(fechaInicio,fechaFinal);
	}
}
