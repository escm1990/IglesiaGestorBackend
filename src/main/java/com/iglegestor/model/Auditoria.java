package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Auditoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Iglesia iglesia;
	
	private String tabla;
	private int llaveRegistro;
	private String accion;
	private String usuario;
	private Long fecha;
	private String registro;
	
	public Auditoria() {
		super();
	}
	
	public Auditoria(String tabla, int llaveRegistro, String accion, String usuario, Long fecha, String registro, Iglesia iglesia) {
		super();
		this.tabla = tabla;
		this.llaveRegistro = llaveRegistro;
		this.accion = accion;
		this.usuario = usuario;
		this.fecha = fecha;
		this.registro = registro;
		this.iglesia = iglesia;
	}

	public Iglesia getIglesia() {
		return iglesia;
	}

	public void setIglesia(Iglesia iglesia) {
		this.iglesia = iglesia;
	}

	public int getLlaveRegistro() {
		return llaveRegistro;
	}

	public void setLlaveRegistro(int llaveRegistro) {
		this.llaveRegistro = llaveRegistro;
	}

	public int getLlaveTabla() {
		return llaveRegistro;
	}

	public void setLlaveTabla(int llaveTabla) {
		this.llaveRegistro = llaveTabla;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}
	
	public String getRegistro() {
		return registro;
	}

	public void setRegistro(String registro) {
		this.registro = registro;
	}
	
}
