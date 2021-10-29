package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Iglesia implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private String direccion;
	private String pais;
	private String correo;
	private String telefono;
	private String logo;
	private String estado;
    private Long fechaFundacion;
	
	public Iglesia() {
		super();
	}

	public Iglesia(String nombre, String direccion, String pais, String correo, String telefono, String logo,
			String estado, Long fechaFundacion) {
		super();
		this.nombre = nombre;
		this.direccion = direccion;
		this.pais = pais;
		this.correo = correo;
		this.telefono = telefono;
		this.logo = logo;
		this.estado = estado;
		this.fechaFundacion = fechaFundacion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Long getFechaFundacion() {
		return fechaFundacion;
	}

	public void setFechaFundacion(Long fechaFundacion) {
		this.fechaFundacion = fechaFundacion;
	}

	@Override
	public String toString() {
		return "Iglesia [id=" + id + ", nombre=" + nombre + ", direccion=" + direccion + ", pais=" + pais + ", correo="
				+ correo + ", telefono=" + telefono + ", logo=" + logo + ", estado=" + estado + ", fechaFundacion="
				+ fechaFundacion + "]";
	}
	
}
