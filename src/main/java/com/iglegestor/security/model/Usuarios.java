package com.iglegestor.security.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iglegestor.model.Iglesia;

@Entity
public class Usuarios implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(unique = true)
	private String usuario;
	
	@NotNull
	private String password;
	
	@NotNull
	private String correo;
	
	@NotNull
	private String estado;
	
	@NotNull
    private Long fecha;
	
	@NotNull 
    @ManyToMany(fetch = FetchType.EAGER)    
    @JoinTable(name = "usuario_rol", joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "rol_id"))
    private Set<Rol> roles = new HashSet<>();
  /*
    @JsonIgnore
	@JoinColumn(name = "iglesia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Iglesia.class, fetch = FetchType.EAGER)
    private Iglesia iglesia;
*/	
	@Column(name = "iglesia_id")
    private int iglesia_id;

	public Usuarios() {
		super();
	}

	public Usuarios(@NotNull String usuario, @NotNull String password, @NotNull String correo, @NotNull String estado, @NotNull Long fecha, int iglesia_id) {
		super();
		this.usuario = usuario;
		this.password = password;
		this.correo = correo;
		this.estado = estado;
		this.fecha = fecha;
		this.iglesia_id = iglesia_id;
	}

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}
	
	public int getIglesia_id() {
		return iglesia_id;
	}

	public void setIglesia_id(int iglesia_id) {
		this.iglesia_id = iglesia_id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

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

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}
/*
	public Roles getRol() {
		return rol;
	}

	public void setRol(Roles rol) {
		this.rol = rol;
	}
 
 public Iglesia getIglesia() {
	return iglesia;
}

public void setIglesia(Iglesia iglesia) {
	this.iglesia = iglesia;
}


public int getRol_id() {
	return rol_id;
}

public void setRol_id(int rol_id) {
	this.rol_id = rol_id;
}
*/
	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", usuario=" + usuario + ", password=" + password + ", correo=" + correo
				+ ", estado=" + estado + ", fecha=" + fecha + ", iglesia_id=" + iglesia_id + ", rol=" + roles.stream().toString()
				+"]";
	}
	
}
