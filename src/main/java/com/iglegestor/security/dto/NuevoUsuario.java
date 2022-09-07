package com.iglegestor.security.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.iglegestor.enums.EstadoRegistro;
import com.iglegestor.security.model.Rol;

public class NuevoUsuario {

	@NotBlank
	private String usuario;
	
	@NotBlank
	private String password;
	
	@NotBlank
	private int id_iglesia;
	
	@Email
	private String correo;
	
	@NotBlank
	private String estado = EstadoRegistro.ACTIVO.toString();
	
	private Set<String> roles = new HashSet<>();

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public int getId_iglesia() {
		return id_iglesia;
	}

	public void setId_iglesia(int id_iglesia) {
		this.id_iglesia = id_iglesia;
	}
    
}
