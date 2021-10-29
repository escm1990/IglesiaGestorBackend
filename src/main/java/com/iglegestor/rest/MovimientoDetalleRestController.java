package com.iglegestor.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.MovimientoDetalle;
import com.iglegestor.repository.MovimientoDetalleDao;

@RestController
@CrossOrigin(origins = "*")
public class MovimientoDetalleRestController {

	@Autowired
	private MovimientoDetalleDao repo;
	
	@GetMapping(value = "api/movimientodetalle/listar")
	public List<MovimientoDetalle> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/movimientodetalle/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody MovimientoDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/movimientodetalle/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody MovimientoDetalle per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/movimientodetalle/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}

	@RequestMapping(value = "api/movimientodetalle/listar/tipo/{id_tipo}", method = RequestMethod.GET)
	public List<MovimientoDetalle> listarPorTipoRegistro(@PathVariable("id_tipo") int id_tipo){
		return repo.obtenerRegistrosPorTipoRegistroMovimiento(id_tipo);
	}
	
	@RequestMapping(value = "api/movimientodetalle/listar/movimiento/{id_movimiento}", method = RequestMethod.GET)
	public List<MovimientoDetalle> listarPorMovimiento(@PathVariable("id_movimiento") int id_movimiento){
		return repo.obtenerRegistrosPorMovimiento(id_movimiento);
	}
	
	@RequestMapping(value = "api/movimientodetalle/listar/estado/{id_estado}", method = RequestMethod.GET)
	public List<MovimientoDetalle> listarPorEstado(@PathVariable("id_estado") String id_estado){
		return repo.obtenerRegistrosPorEstadoRegistro(id_estado);
	}
	
}
