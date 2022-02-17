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

	@Autowired
	private TipoPersonaDao repo;
	
	@Autowired
	private AuditoriaDao audit;
	
	Auditoria au;
	
	@Autowired
	private ErroresDao err;
	
	Errores er;
	
	private Utilidades utils;
	
	@GetMapping(value = "/listar")
	public List<TipoPersona> listar(){
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@GetMapping("/detalle/{id}")
    public ResponseEntity<TipoPersona> getTipoPersonaByID(@PathVariable int id) throws NotFoundException {
        //Es un Optional<T>
        Optional<TipoPersona> u = repo.findById(id);
        //Si está presente lo devolvemos
        if(u.isPresent()){
            return ResponseEntity.ok(u.get());
        }
        //Si no, lanzamos un error
        else{
            throw new NotFoundException("Not found TipoPersona by id: " + id);
        }
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody TipoPersona tp) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
		try {
			repo.save(tp);
			au = new Auditoria(Tablas.TIPO_PERSONA.toString(), Accion.INSERTAR.toString(), tp.getUltimoUsuario(), fechaRegistro, tp.toString(), tp.getId());
			audit.save(au);
		} 
		catch(Exception ex) {
			er = new Errores(TipoPersona.class.toString(), "insertar", ex.getStackTrace().toString(), tp.getUltimoUsuario(), fechaRegistro , tp.getId());
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id")int id, @RequestBody TipoPersona tp) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
		//En primer lugar, buscamos el Usuario
        Optional<TipoPersona> encontrada = repo.findById(id);
        //Si está presente lo devolvemos
        if(encontrada.isPresent()){
            //Usuario encontrado para realizar update sobre él.
            TipoPersona tipoPersonaActualizar = encontrada.get();         
            //Copiamos los nuevos datos al usuario
            tipoPersonaActualizar.setDescripcion(tp.getDescripcion());
            tipoPersonaActualizar.setEstado(tp.getEstado());
            tipoPersonaActualizar.setIglesia_id(tp.getIglesia_id());
            tipoPersonaActualizar.setUltimoUsuario(tp.getUltimoUsuario());
            //Guadramos en la DB
            TipoPersona teModificada= repo.save(tipoPersonaActualizar);            
            
            au = new Auditoria(Tablas.TIPO_PERSONA.toString(), Accion.MODIFICAR.toString(), tp.getUltimoUsuario(), utils.fechaMilisegundos(new Date()) , teModificada.toString(), teModificada.getId());
    		audit.save(au);
    		
            return new ResponseEntity(new Mensaje("Iglesia modificada"), HttpStatus.OK);
        }
        else {
        	er = new Errores(TipoPersona.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(), tp.getUltimoUsuario(), fechaRegistro , te.getId());
            return new ResponseEntity(new Mensaje("TipoPersona no se pudo actualizar"), HttpStatus.BAD_REQUEST);
        }
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
        Optional<TipoPersona> i = repo.findById(id);
		try {
        au = new Auditoria(Tablas.TIPO_PERSONA.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(), utils.fechaMilisegundos(new Date()) ,  i.get().toString(),  i.get().getId());
		audit.save(au);
		repo.deleteById(id);
		} catch( Exception ex) {
			er = new Errores(Iglesia.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(), i.get().getUltimoUsuario(), fechaRegistro , i.get().getId());
		}
	}
	
	@RequestMapping(value = "/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<TipoPersona> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
}
