package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Auditoria implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    private int iglesia_id;
	
	private String tabla;
	private String accion;
	private String usuario;
	private Long fecha;
	
	@Column(length = 4000)
	private String registro;
	
	public Auditoria() {
		super();
	}
	
	public Auditoria(String tabla, String accion, String usuario, Long fecha, String registro, int iglesia_id) {
		super();
		this.tabla = tabla;
		this.accion = accion;
		this.usuario = usuario;
		this.fecha = fecha;
		this.registro = registro;
		this.iglesia_id = iglesia_id;
	}

	public int getIglesia_id() {
		return iglesia_id;
	}

	public void setIglesia_id(int iglesia_id) {
		this.iglesia_id = iglesia_id;
	}

	/*public Iglesia getIglesia() {
		return iglesia;
	}

	public void setIglesia(Iglesia iglesia) {
		this.iglesia = iglesia;
	}
*/
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
