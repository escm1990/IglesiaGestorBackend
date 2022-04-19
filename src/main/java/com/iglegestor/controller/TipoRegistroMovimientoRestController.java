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
import com.iglegestor.model.TipoRegistroMovimiento;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.TipoRegistroMovimientoDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/tiporegistromovimiento")
@CrossOrigin(origins = "*")
public class TipoRegistroMovimientoRestController {

	@Autowired
	private TipoRegistroMovimientoDao repo;

	@Autowired
	private AuditoriaDao audit;

	Auditoria au;

	@Autowired
	private ErroresDao err;

	Errores er;

	/**
	 * Retorna un listado de Tipos de Registro de Movimientos
	 * 
	 * @return List<TipoRegistroMovimiento>
	 */
	@GetMapping(value = "/listar")
	public List<TipoRegistroMovimiento> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un TipoRegistroMovimiento
	 * 
	 * @param id -> Código del TipoRegistroMovimiento
	 * @return ResponseEntity<TipoRegistroMovimiento>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<TipoRegistroMovimiento> getTipoMovimientoByID(@PathVariable int id)
			throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<TipoRegistroMovimiento> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(TipoRegistroMovimiento.class.toString(), "detalle",
					new NotFoundException("No se encontró TipoEvento con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("Not found TipoRegistroMovimiento by id: " + id);
		}
	}

	/**
	 * Método que guarda un TipoRegistroMovimiento
	 * 
	 * @param trm - TipoRegistroMovimiento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody TipoRegistroMovimiento trm) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(trm);
			au = new Auditoria(Tablas.TIPO_REGISTRO_MOVIMIENTO.toString(), Accion.INSERTAR.toString(),
					trm.getUltimoUsuario(), fechaRegistro, trm.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("TipoRegistroMovimiento Insertado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(TipoRegistroMovimiento.class.toString(), "insertar", ex.getStackTrace().toString(),
					trm.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoRegistroMovimiento no se pudo actualizar"),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Tipo de Evento
	 * 
	 * @param id  - Identificador del Tipo de Evento a actualizar
	 * @param trm - Objeto Tipo de Evento a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody TipoRegistroMovimiento trm)
			throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<TipoRegistroMovimiento> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			TipoRegistroMovimiento trmActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			trmActualizar.setDescripcion(trm.getDescripcion());
			trmActualizar.setEstado(trm.getEstado());
			trmActualizar.setUltimoUsuario(trm.getUltimoUsuario());
			// Guadramos en la DB
			TipoRegistroMovimiento trmModificada = repo.save(trmActualizar);

			au = new Auditoria(Tablas.TIPO_REGISTRO_MOVIMIENTO.toString(), Accion.MODIFICAR.toString(),
					trm.getUltimoUsuario(), Utilidades.fechaMilisegundos(new Date()), trmModificada.toString(),
					0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("TipoRegistroMovimiento modificado"), HttpStatus.OK);
		} else {
			er = new Errores(TipoRegistroMovimiento.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					trm.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoRegistroMovimiento no se pudo actualizar"),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un TipoRegistroMovimiento
	 * @param id - Identificador del Tipo de Evento a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<TipoRegistroMovimiento> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.TIPO_REGISTRO_MOVIMIENTO.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("TipoRegistroMovimiento eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(TipoRegistroMovimiento.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("TipoRegistroMovimiento no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

}
