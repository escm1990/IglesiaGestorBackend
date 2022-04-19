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
import com.iglegestor.model.CierreDetalle;
import com.iglegestor.model.Errores;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.CierreDetalleDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/cierredetalle")
@CrossOrigin(origins = "*")
public class CierreDetalleRestController {

	@Autowired
	private CierreDetalleDao repo;
	
	@Autowired
	private AuditoriaDao audit;
	
	Auditoria au;
	
	@Autowired
	private ErroresDao err;
	
	Errores er;

	
	/**
	 * Retorna un listado de Cierre Detalle
	 * @return List<CierreDetalle>
	 */
	@GetMapping(value = "/listar")
	public List<CierreDetalle> listar(){
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Cierre Detalle
	 * @param id -> Código del Cierre Detalle
	 * @return ResponseEntity<CierreDetalle>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
    public ResponseEntity<CierreDetalle> getUserByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		//Es un Optional<T>
        Optional<CierreDetalle> u = repo.findById(id);
        //Si está presente lo devolvemos
        if(u.isPresent()){
            return ResponseEntity.ok(u.get());
        }
        //Si no, lanzamos un error
        else{
        	er = new Errores(CierreDetalle.class.toString(), "detalle",
					new NotFoundException("No se encontró CierreDetalle con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
            throw new NotFoundException("No se encontró el registro de detalle de ciere con id: "+ id);
        }
   }
	
	/**
	 * Método que guarda un registro de Cierre Detalle
	 * @param cd - Cierre Detalle a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody CierreDetalle cd) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(cd);

			au = new Auditoria(Tablas.CIERRE_DETALLE.toString(), Accion.INSERTAR.toString(), cd.getUltimoUsuario(),
					fechaRegistro, cd.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("CierreDetalle insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(CierreDetalle.class.toString(), "insertar", ex.getStackTrace().toString(),
					cd.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("CierreDetalle no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Cierre Detalle
	 * @param id - Identificador del Cierre Detalle a actualizar
	 * @param te - Objeto Cierre Detalle a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody CierreDetalle te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Cierre Detalle
		Optional<CierreDetalle> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			CierreDetalle CierreDetalleActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			CierreDetalleActualizar.setDescripcion(te.getDescripcion());
			CierreDetalleActualizar.setMonto(te.getMonto());
			CierreDetalleActualizar.setUltimoUsuario(te.getUltimoUsuario());
			// Guadramos en la DB
			CierreDetalle teModificada = repo.save(CierreDetalleActualizar);

			au = new Auditoria(Tablas.CIERRE_DETALLE.toString(), Accion.MODIFICAR.toString(), te.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), teModificada.toString(), 0);
			audit.save(au);

			return new ResponseEntity(new Mensaje("CierreDetalle modificado"), HttpStatus.OK);
		} else {
			er = new Errores(CierreDetalle.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					te.getUltimoUsuario(), fechaRegistro, 0);
			err.save(er);
			return new ResponseEntity(new Mensaje("CierreDetalle no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Cierre Detalle
	 * @param id - Identificador del Cierre Detalle a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<CierreDetalle> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.CIERRE_DETALLE.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("CierreDetalle eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(CierreDetalle.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("CierreDetalle no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/listar/cierre/{id_cierre}", method = RequestMethod.GET)
	public List<CierreDetalle> listarPorIdCierre(@PathVariable("id_iglesia") int id_cierre){
		return repo.obtenerRegistrosPorIdCierre(id_cierre);
	}
	
}
