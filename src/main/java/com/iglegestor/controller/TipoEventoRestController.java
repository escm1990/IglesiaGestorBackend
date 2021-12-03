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

	@Autowired
	private TipoEventoDao repo;
	
	@Autowired
	private AuditoriaDao audit;
	
	Auditoria au;
	
	@Autowired
	private ErroresDao err;
	
	Errores er;
	
	private Utilidades utils;
	
	@GetMapping(value = "/listar")
	public List<TipoEvento> listar(){
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@GetMapping("/detalle/{id}")
    public ResponseEntity<TipoEvento> getTipoEventoByID(@PathVariable int id) throws NotFoundException {
        //Es un Optional<T>
        Optional<TipoEvento> u = repo.findById(id);
        //Si está presente lo devolvemos
        if(u.isPresent()){
            return ResponseEntity.ok(u.get());
        }
        //Si no, lanzamos un error
        else{
            throw new NotFoundException("Not found TipoEvento by id: " + id);
        }
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody TipoEvento te) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
		try {
			repo.save(te);
			au = new Auditoria(Tablas.TIPO_EVENTO.toString(), Accion.INSERTAR.toString(), te.getUltimoUsuario(), fechaRegistro, te.toString(), te.getId());
			audit.save(au);
		} 
		catch(Exception ex) {
			er = new Errores(TipoEvento.class.toString(), "insertar", ex.getStackTrace().toString(), te.getUltimoUsuario(), fechaRegistro , te.getId());
		}
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id")int id, @RequestBody TipoEvento te) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
		//En primer lugar, buscamos el Usuario
        Optional<TipoEvento> encontrada = repo.findById(id);
        //Si está presente lo devolvemos
        if(encontrada.isPresent()){
            //Usuario encontrado para realizar update sobre él.
            TipoEvento tipoEventoActualizar = encontrada.get();         
            //Copiamos los nuevos datos al usuario
            tipoEventoActualizar.setDescripcion(te.getDescripcion());
            tipoEventoActualizar.setEstado(te.getEstado());
            tipoEventoActualizar.setIglesia_id(te.getIglesia_id());
            tipoEventoActualizar.setUltimoUsuario(te.getUltimoUsuario());
            //Guadramos en la DB
            TipoEvento teModificada= repo.save(tipoEventoActualizar);            
            
            au = new Auditoria(Tablas.TIPO_EVENTO.toString(), Accion.MODIFICAR.toString(), te.getUltimoUsuario(), utils.fechaMilisegundos(new Date()) , teModificada.toString(), teModificada.getId());
    		audit.save(au);
    		
            return new ResponseEntity(new Mensaje("Iglesia modificada"), HttpStatus.OK);
        }
        else {
        	er = new Errores(TipoEvento.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(), te.getUltimoUsuario(), fechaRegistro , te.getId());
            return new ResponseEntity(new Mensaje("TipoEvento no se pudo actualizar"), HttpStatus.BAD_REQUEST);
        }
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
        Optional<TipoEvento> i = repo.findById(id);
		try {
        au = new Auditoria(Tablas.TIPO_EVENTO.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(), utils.fechaMilisegundos(new Date()) ,  i.get().toString(),  i.get().getId());
		audit.save(au);
		repo.deleteById(id);
		} catch( Exception ex) {
			er = new Errores(Iglesia.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(), i.get().getUltimoUsuario(), fechaRegistro , i.get().getId());
		}
	}
	
	@RequestMapping(value = "/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<TipoEvento> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
}
