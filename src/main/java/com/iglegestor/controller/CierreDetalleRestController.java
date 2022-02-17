package com.iglegestor.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.Auditoria;
import com.iglegestor.model.CierreDetalle;
import com.iglegestor.model.Errores;
import com.iglegestor.model.Iglesia;
import com.iglegestor.repository.AuditoriaDao;
import com.iglegestor.repository.CierreDetalleDao;
import com.iglegestor.repository.ErroresDao;
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
	
	private Utilidades utils;
	
	@GetMapping(value = "/listar")
	public List<CierreDetalle> listar(){
		return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
	}

	@GetMapping("/detalle/{id}")
    public ResponseEntity<CierreDetalle> getUserByID(@PathVariable int id) throws NotFoundException {
        //Es un Optional<T>
        Optional<CierreDetalle> u = repo.findById(id);
        //Si está presente lo devolvemos
        if(u.isPresent()){
            return ResponseEntity.ok(u.get());
        }
        //Si no, lanzamos un error
        else{
            throw new NotFoundException("No se encontró la el registro de detalle de ciere con id: "+ id);
        }
   }
	
	@RequestMapping(value = "api/cierredetalle/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody CierreDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/cierredetalle/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody CierreDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/cierredetalle/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}
	
	@RequestMapping(value = "api/cierredetalle/listar/cierre/{id_cierre}", method = RequestMethod.GET)
	public List<CierreDetalle> listarPorIdCierre(@PathVariable("id_iglesia") int id_cierre){
		return repo.obtenerRegistrosPorIdCierre(id_cierre);
	}
	
}
