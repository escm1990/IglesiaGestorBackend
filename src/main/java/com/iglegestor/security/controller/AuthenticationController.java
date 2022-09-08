package com.iglegestor.security.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iglegestor.model.Cierre;
import com.iglegestor.model.Errores;
import com.iglegestor.repository.ErroresDao;
import com.iglegestor.security.dto.JwtDto;
import com.iglegestor.security.dto.LoginUsuario;
import com.iglegestor.security.dto.NuevoUsuario;
import com.iglegestor.security.jwt.JwtProvider;
import com.iglegestor.security.model.Rol;
import com.iglegestor.security.model.Usuarios;
import com.iglegestor.security.repository.UsuariosDao;
import com.iglegestor.security.service.RolService;
import com.iglegestor.security.service.UsuariosService;
import com.iglegestor.utils.Mensaje;
import com.iglegestor.utils.Utilidades;

import javassist.NotFoundException;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthenticationController {

	@Autowired
	PasswordEncoder pe;
	
	@Autowired
	AuthenticationManager am;
	
	@Autowired
	UsuariosService us;
	
	@Autowired
	RolService rs;
	
	@Autowired
	JwtProvider jwtPr;
	
	@Autowired
	UsuariosDao repoUser;
	
	@Autowired
	private ErroresDao err;

	Errores er;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nu, BindingResult br) throws ParseException{
		if(br.hasErrors()) {
			return new ResponseEntity(new Mensaje("Campos mal puestos o Email inválido"), HttpStatus.BAD_REQUEST);
		}
		
		if(us.existsByUsuario(nu.getUsuario())) {
			return new ResponseEntity(new Mensaje("Nombre ya existe"),HttpStatus.BAD_REQUEST);
		}
		
		if(us.existsByCorreo(nu.getCorreo())) {
			return new ResponseEntity(new Mensaje("Correo ya existe"),HttpStatus.BAD_REQUEST);
		}
		
		Usuarios usuario = new Usuarios(nu.getUsuario(), pe.encode(nu.getPassword()), nu.getCorreo(), nu.getEstado(), Utilidades.fechaMilisegundos(new Date()), nu.getId_iglesia());
		Set<Rol> roles = new HashSet<>();
		roles.add(rs.getByNombre(com.iglegestor.enums.Roles.ROLE_USER).get());
		
		if(nu.getRoles().contains("admin")) {
			roles.add(rs.getByNombre(com.iglegestor.enums.Roles.ROLE_ADMIN).get());
		}
		
		if(nu.getRoles().contains("consulta")) {
			roles.add(rs.getByNombre(com.iglegestor.enums.Roles.ROLE_CONSULTA).get());
		}
		
		usuario.setRoles(roles);
		us.save(usuario);
		
		return new ResponseEntity(new Mensaje("Usuario guardado"), HttpStatus.CREATED);	
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario lu, BindingResult br){
		if(br.hasErrors()) {
			return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
		}
		
		Authentication auth = am.authenticate(new UsernamePasswordAuthenticationToken(lu.getUsuario(), lu.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtPr.generateToken(auth);
		UserDetails ud = (UserDetails) auth.getPrincipal();
		JwtDto jwtDto = new JwtDto(jwt, ud.getUsername(), ud.getAuthorities());
		
		return new ResponseEntity(jwtDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/user/{user}")
	public ResponseEntity<Usuarios> getUserByUser(@PathVariable String user) throws NotFoundException, ParseException {
		Long fechaRegistro = Utilidades.fechaMilisegundos(new Date());
		// Es un Optional<T>
		Optional<Usuarios> u = repoUser.findByUsuario(user);
		// Si está presente lo devolvemos
		if (u.isPresent()) {
			return ResponseEntity.ok(u.get());
		}
		// Si no, lanzamos un error
		else {
			er = new Errores(Usuarios.class.toString(), "detalle",
					new NotFoundException("No se encontró usuario con el usuario: " + user).toString(), "", fechaRegistro, 0);
			err.save(er);
			throw new NotFoundException("No se encontró la  usuario con el usuario: " + user);
		}
	}
	
}
