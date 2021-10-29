package com.iglegestor.rest;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.Miembro;
import com.iglegestor.repository.MiembroDao;
import com.iglegestor.utils.Constantes;
import com.iglegestor.utils.Utilidades;

@RestController
@CrossOrigin(origins = "*")
public class MiembroRestController {

	@Autowired
	private MiembroDao repo;
	
	@GetMapping(value = "api/miembro/listar")
	public List<Miembro> listar(){
		return repo.findAll();
	}

	@RequestMapping(value = "api/miembro/guardar", method = RequestMethod.POST)
	public void insertar(@RequestBody Miembro per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/miembro/modificar", method = RequestMethod.PUT)
	public void modificar(@RequestBody Miembro per) {
		repo.save(per);
	}
	
	@RequestMapping(value = "api/miembro/eliminar/{id}", method = RequestMethod.DELETE)
	public void eliminar(@PathVariable("id") Integer id) {
		repo.deleteById(id);
	}

	@RequestMapping(value = "api/miembro/listar/iglesia/{id_iglesia}", method = RequestMethod.GET)
	public List<Miembro> listarPorIglesia(@PathVariable("id_iglesia") int id_iglesia){
		return repo.obtenerRegistrosPorIglesia(id_iglesia);
	}
	
	@RequestMapping(value = "api/miembro/listar/sexo/{id_sexo}", method = RequestMethod.GET)
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
		Long fechaInicio = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFechaBautismo(fechaInicio,fechaFinal);
	}
	
	@RequestMapping(value = "api/miembro/listar/fechaconversion/{fecha}", method = RequestMethod.GET)
	public List<Miembro> listarPorFechaConversion(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFechaConversion(fechaInicio,fechaFinal);
	}
	
	@RequestMapping(value = "api/miembro/listar/fechanacimiento/{fecha}", method = RequestMethod.GET)
	public List<Miembro> listarPorFechaNacimiento(@PathVariable("fecha") String fecha) throws ParseException{
		Long fechaInicio = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraInicio);
		Long fechaFinal = Utilidades.fechaMilisegundos(fecha, Constantes.FormatoHoraFinal);
		return repo.obtenerRegistrosPorFechaNacimiento(fechaInicio,fechaFinal);
	}
	
}
