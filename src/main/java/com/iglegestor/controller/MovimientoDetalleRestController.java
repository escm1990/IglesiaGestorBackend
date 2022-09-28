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
import com.iglegestor.model.EventoDetalleArray;
import com.iglegestor.model.MovimientoDetalle;
import com.iglegestor.model.MovimientoDetalleArray;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.MovimientoDetalleDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/movimientodetalle")
@CrossOrigin(origins = "*")
public class MovimientoDetalleRestController {

	@Autowired
	private MovimientoDetalleDao repo;
	
	@Autowired
	private AuditoriaDao audit;

	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;

	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<MovimientoDetalle>
	 */
	@GetMapping(value = "/listar")
	public List<MovimientoDetalle> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Movimiento Detalle
	 * @param id -> Código del Movimiento Detalle
	 * @return ResponseEntity<MovimientoDetalle>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<MovimientoDetalle> getMovimientoDetalleByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<MovimientoDetalle> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(MovimientoDetalle.class.toString(), "detalle",
					new NotFoundException("No se encontró MovimientoDetalle con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("No se encontró MovimientoDetalle con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Movimiento Detalle
	 * @param te - Movimiento Detalle a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody MovimientoDetalle te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(te);

			au = new Auditoria(Tablas.MOVIMIENTO_DETALLE.toString(), Accion.INSERTAR.toString(), te.getUltimoUsuario(),
					fechaRegistro, te.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("MovimientoDetalle insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(MovimientoDetalle.class.toString(), "insertar", ex.getStackTrace().toString(),
					te.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("MovimientoDetalle no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Movimiento Detalle
	 * @param id - Identificador del Movimiento Detalle a actualizar
	 * @param te - Objeto Movimiento Detalle a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody MovimientoDetalle te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<MovimientoDetalle> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			MovimientoDetalle MovimientoDetalleActualizar = encontrada.get();
			// Copiamos los nuevos datos al objeto
			MovimientoDetalleActualizar.setComentario(te.getComentario());
			MovimientoDetalleActualizar.setEstado(te.getEstado());
			MovimientoDetalleActualizar.setMiembro_id(te.getMiembro_id());
			MovimientoDetalleActualizar.setMovimiento_id(te.getMovimiento_id());
			MovimientoDetalleActualizar.setMonto(te.getMonto());
			MovimientoDetalleActualizar.setTipo_registro_id(te.getTipo_registro_id());
			MovimientoDetalleActualizar.setUltimoUsuario(te.getUltimoUsuario());
			// Guadramos en la DB
			MovimientoDetalle teModificada = repo.save(MovimientoDetalleActualizar);

			au = new Auditoria(Tablas.MOVIMIENTO_DETALLE.toString(), Accion.MODIFICAR.toString(), te.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), teModificada.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("MovimientoDetalle modificado"), HttpStatus.OK);
		} else {
			er = new Errores(MovimientoDetalle.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					te.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("MovimientoDetalle no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Movimiento Detalle
	 * @param id - Identificador del Movimiento Detalle a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<MovimientoDetalle> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.MOVIMIENTO_DETALLE.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("MovimientoDetalle eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(MovimientoDetalle.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("MovimientoDetalle no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/listar/tipo/{id_tipo}", method = RequestMethod.GET)
	public List<MovimientoDetalle> listarPorTipoRegistro(@PathVariable("id_tipo") int id_tipo){
		return repo.obtenerRegistrosPorTipoRegistroMovimiento(id_tipo);
	}
	
	@RequestMapping(value = "/listar/movimiento/{id_movimiento}", method = RequestMethod.GET)
	public List<MovimientoDetalle> listarPorMovimiento(@PathVariable("id_movimiento") int id_movimiento){
		return repo.obtenerRegistrosPorMovimiento(id_movimiento);
	}
	
	@RequestMapping(value = "/listar/estado/{id_estado}", method = RequestMethod.GET)
	public List<MovimientoDetalle> listarPorEstado(@PathVariable("id_estado") String id_estado){
		return repo.obtenerRegistrosPorEstadoRegistro(id_estado);
	}
	
	/**
	 * Método que guarda un Evento Detalle
	 * @param te - Evento Detalle a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar/excel", method = RequestMethod.POST)
	public ResponseEntity<?> insertarCargaExcel(@RequestBody MovimientoDetalleArray mda) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			
			List registros = mda.getDetalles();
			for(int i=0;i<registros.size();i++) {
				System.out.println(registros.get(i));
				repo.save((MovimientoDetalle) registros.get(i));
			}
			
			au = new Auditoria(Tablas.EVENTO_DETALLE.toString(), Accion.INSERTAR.toString(), "-",
					fechaRegistro, mda.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("MovimientoDetalle desde Excel insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(EventoDetalle.class.toString(), "insertar", ex.getStackTrace().toString(),
					"-", fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("MovimientoDetalle desde Excel no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}
}
