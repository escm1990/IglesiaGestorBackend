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
	
	private Utilidades utils;

	@GetMapping(value = "/listar")
	public List<Iglesia> listar(){
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}
	
	@GetMapping("/detalle/{id}")
    public ResponseEntity<Iglesia> getUserByID(@PathVariable int id) throws NotFoundException {
        //Es un Optional<T>
        Optional<Iglesia> u = repo.findById(id);
        //Si está presente lo devolvemos
        if(u.isPresent()){
            return ResponseEntity.ok(u.get());
        }
        //Si no, lanzamos un error
        else{
            throw new NotFoundException("Not found Iglesia by id: " + id);
        }
   }

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Iglesia igle) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
		try {
			repo.save(igle);
			au = new Auditoria(Tablas.IGLESIA.toString(), Accion.INSERTAR.toString(), igle.getUltimoUsuario(), fechaRegistro, igle.toString(), igle.getId());
			audit.save(au);
		} 
		catch(Exception ex) {
			er = new Errores(Iglesia.class.toString(), "insertar", ex.getStackTrace().toString(), igle.getUltimoUsuario(), fechaRegistro , igle.getId());
		}
		
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id")int id, @RequestBody Iglesia igle) throws ParseException {
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
		//En primer lugar, buscamos el Usuario
        Optional<Iglesia> encontrada = repo.findById(id);
        //Si está presente lo devolvemos
        if(encontrada.isPresent()){
            //Usuario encontrado para realizar update sobre él.
            Iglesia iglesiaActualizar = encontrada.get();         
            //Copiamos los nuevos datos al usuario
            iglesiaActualizar.setNombre(igle.getNombre());  
            iglesiaActualizar.setDireccion(igle.getDireccion());
            iglesiaActualizar.setCorreo(igle.getCorreo()); 
            iglesiaActualizar.setFechaFundacion(igle.getFechaFundacion()); 
            iglesiaActualizar.setEstado(igle.getEstado()); 
            iglesiaActualizar.setLogo(igle.getLogo()); 
            iglesiaActualizar.setPais(igle.getPais()); 
            iglesiaActualizar.setTelefono(igle.getTelefono());
            iglesiaActualizar.setUltimoUsuario(igle.getUltimoUsuario());
            //Guadramos en la DB
            Iglesia iglesiaModificada = repo.save(iglesiaActualizar);            
            
            au = new Auditoria(Tablas.IGLESIA.toString(), Accion.MODIFICAR.toString(), iglesiaModificada.getUltimoUsuario(), utils.fechaMilisegundos(new Date()) , iglesiaModificada.toString(), iglesiaModificada.getId());
    		audit.save(au);
    		
            return new ResponseEntity(new Mensaje("Iglesia modificada"), HttpStatus.OK);
        }
        else {
        	er = new Errores(Iglesia.class.toString(), "modificar", HttpStatus.BAD_REQUEST.toString(), igle.getUltimoUsuario(), fechaRegistro , igle.getId());
            return new ResponseEntity(new Mensaje("Iglesia no se pudo actualizar"), HttpStatus.BAD_REQUEST);
        }
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) throws ParseException {
		
		Long fechaRegistro = utils.fechaMilisegundos(new Date());
        Optional<Iglesia> i = repo.findById(id);
		try {
        au = new Auditoria(Tablas.IGLESIA.toString(), Accion.ELIMINAR.toString(), i.get().getUltimoUsuario(), utils.fechaMilisegundos(new Date()) ,  i.get().toString(),  i.get().getId());
		audit.save(au);
		repo.deleteById(id);
		} catch( Exception ex) {
			er = new Errores(Iglesia.class.toString(), "eliminar", HttpStatus.BAD_REQUEST.toString(), i.get().getUltimoUsuario(), fechaRegistro , i.get().getId());
		}
	}
	
}
