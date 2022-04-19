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
import com.iglegestor.model.Miembro;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.repository.MiembroDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/miembro")
@CrossOrigin(origins = "*")
public class MiembroRestController {

	@Autowired
	private MiembroDao repo;
	
	@Autowired
	private AuditoriaDao audit;

	@Autowired
	private ErroresDao err;

	Auditoria au;
	Errores er;

	/**
	 * Retorna un listado de Tipos de Eventos
	 * @return List<Miembro>
	 */
	@GetMapping(value = "/listar")
	public List<Miembro> listar() {
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	/**
	 * Método que retorna el detalle de un Miembro
	 * @param id -> Código del Miembro
	 * @return ResponseEntity<Miembro>
	 * @throws NotFoundException
	 * @throws ParseException
	 */
	@GetMapping("/detalle/{id}")
	public ResponseEntity<Miembro> getMiembroByID(@PathVariable int id) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<Miembro> u = repo.findById(id);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error y lo almacenamos en base de datos
		else {
			er = new Errores(Miembro.class.toString(), "detalle",
					new NotFoundException("No se encontró Miembro con el id: " + id).toString(), "", fechaRegistro,
					0);
			err.save(er);
			throw new NotFoundException("No se encontró Miembro con el id: " + id);
		}
	}

	/**
	 * Método que guarda un Miembro
	 * @param te - Miembro a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public ResponseEntity<?> insertar(@RequestBody Miembro te) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		try {
			repo.save(te);

			au = new Auditoria(Tablas.MIEMBRO.toString(), Accion.INSERTAR.toString(), te.getUltimoUsuario(),
					fechaRegistro, te.toString(), te.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Miembro insertado"), HttpStatus.OK);
		} catch (Exception ex) {
			er = new Errores(Miembro.class.toString(), "insertar", ex.getStackTrace().toString(),
					te.getUltimoUsuario(), fechaRegistro, te.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Miembro no se pudo insertar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para modificar un Miembro
	 * @param id - Identificador del Miembro a actualizar
	 * @param mi - Objeto Miembro a persistir
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id") int id, @RequestBody Miembro mi) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// En primer lugar, buscamos el Usuario
		Optional<Miembro> encontrada = repo.findById(id);
		// Si está presente lo devolvemos
		if (encontrada.isPresent()) {
			// Usuario encontrado para realizar update sobre él.
			Miembro MiembroActualizar = encontrada.get();
			// Copiamos los nuevos datos al usuario
			MiembroActualizar.setApellido(mi.getApellido());
			MiembroActualizar.setCorreo(mi.getCorreo());
			MiembroActualizar.setDireccion(mi.getDireccion());
			MiembroActualizar.setEstadoCivil(mi.getEstadoCivil());
			MiembroActualizar.setFechaBautismo(mi.getFechaBautismo());
			MiembroActualizar.setFechaConversion(mi.getFechaConversion());
			MiembroActualizar.setFoto(mi.getFoto());
			MiembroActualizar.setNombre(mi.getNombre());
			MiembroActualizar.setSexo(mi.getSexo());
			MiembroActualizar.setTipo_persona_id(mi.getTipo_persona_id());
			MiembroActualizar.setTelefonoFijo(mi.getTelefonoFijo());
			MiembroActualizar.setTelefonoMovil(mi.getTelefonoMovil());
			MiembroActualizar.setFechaNacimiento(mi.getFechaNacimiento());
			MiembroActualizar.setEstado(mi.getEstado());
			MiembroActualizar.setIglesia_id(mi.getIglesia_id());
			MiembroActualizar.setUltimoUsuario(mi.getUltimoUsuario());
			// Guadramos en la DB
			Miembro miModificado = repo.save(MiembroActualizar);

			au = new Auditoria(Tablas.MIEMBRO.toString(), Accion.MODIFICAR.toString(), mi.getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), miModificado.toString(), miModificado.getIglesia_id());
			audit.save(au);

			return new ResponseEntity(new Mensaje("Miembro modificado"), HttpStatus.OK);
		} else {
			er = new Errores(Miembro.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(),
					mi.getUltimoUsuario(), fechaRegistro, mi.getIglesia_id());
			err.save(er);
			return new ResponseEntity(new Mensaje("Miembro no se pudo actualizar"), HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Método para eliminar un Miembro
	 * @param id - Identificador del Miembro a eliminar
	 * @return ResponseEntity<?>
	 * @throws ParseException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		Optional<Miembro> i = repo.findById(id);
		try {
			
			au = new Auditoria(Tablas.MIEMBRO.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(),
					Utilidades.fechaMilisegundos(new Date()), i.get().toString(), i.get().getId());
			audit.save(au);
		
			repo.deleteById(id);
			
			return new ResponseEntity(new Mensaje("Miembro eliminado"), HttpStatus.OK);

		} catch (Exception ex) {
			er = new Errores(Miembro.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(),
					i.get().getUltimoUsuario(), fechaRegistro, i.get().getId());
			err.save(er);
			return new ResponseEntity(new Mensaje("Miembro no se pudo eliminar"), HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Miembro> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "/listar/sexo/{id_sexo}", method = RequestMethod.GET)
	public List<Miembro> listarPorSexo(@PathVariable("id_sexo") String id_sexo){
		return repo.obtenerRegistrosPorSexo(id_sexo);
	}
	
	@RequestMapping(value = "api/miembro/listar/estadocivil/{id_estado_civil}", method = RequestMethod.GET)
	public List<Miembro> listarPorEstadoCivil(@PathVariable("id_estado_civil") String id_estado_civil){
		return repo.obtenerRegistrosPorEstadoCivil(id_estado_civil);
	}
	
	@RequestMapping(value = "api/miembro/listar/tipo/{id_tipo}", method = RequestMethod.GET)
	public List<Miembro> listarPorTipoPersona(@PathVariable("id_tipo") int id_tipo){
		return repo.obtenerRegistrosPorTipoPersona(id_tipo);
	}
	
	@RequestMapping(value = "api/miembro/listar/nombre/{criterio}", method = RequestMethod.GET)
	public List<Miembro> listarPorIglesia(@PathVariable("criterio") String criterio){
		return repo.obtenerRegistrosPorNombreCompleto(criterio);
	}
	
	@RequestMapping(value = "api/miembro/listar/fechabautismo/{fecha}", method = RequestMethod.GET)
	public List<Miembro> listarPorFechaBautismo(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFechaBautismo(fechaInicio,fechaFinal);
	}
	
	@RequestMapping(value = "api/miembro/listar/fechaconversion/{fecha}", method = RequestMethod.GET)
	public List<Miembro> listarPorFechaConversion(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFechaConversion(fechaInicio,fechaFinal);
	}
	
	@RequestMapping(value = "api/miembro/listar/fechanacimiento/{fecha}", method = RequestMethod.GET)
	public List<Miembro> listarPorFechaNacimiento(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaStringMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFechaNacimiento(fechaInicio,fechaFinal);
	}
	
}
