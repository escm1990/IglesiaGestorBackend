package com.iglegestor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.iglegestor.model.Iglesia;
import com.iglegestor.repository.IglesiaDao;
import com.iglegestor.utils.Mensaje;

import javassist.NotFoundException;

@RestController
@RequestMapping("/api/iglesia")
@CrossOrigin(origins = "*")
public class IglesiaRestController {

	@Autowired
	private IglesiaDao repo;
	
	@GetMapping(value = "/listar")
	public List<Iglesia> listar(){
		return repo.findAll();
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
            throw new NotFoundException("Not found User by id: " + id);
        }
   }

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Iglesia per) {
		repo.save(per);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/modificar/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> modificar(@PathVariable("id")int id, @RequestBody Iglesia igle) {
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
            //Guadramos en la DB
            Iglesia iglesiaModificada = repo.save(iglesiaActualizar);            
            return new ResponseEntity(new Mensaje("Iglesia modificada"), HttpStatus.OK);
        }
        else {
            return new ResponseEntity(new Mensaje("Iglesia no se pudo actualizar"), HttpStatus.BAD_REQUEST);
        }
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
}
