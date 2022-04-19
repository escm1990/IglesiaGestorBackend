package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Errores implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String clase;
	private String metodo;
	private String descripcion;
	private String usuario;
	private Long fecha;
	
	/* @JsonIgnore
	@JoinColumn(name = "iglesia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Iglesia.class, fetch = FetchType.EAGER)
    private Iglesia iglesia;
	
	@Column(name = "iglesia_id") */
    private int iglesia_id;
	
	public Errores() {
		super();
	}

	public Errores(String clase, String metodo, String descripcion, String usuario, Long fecha, int iglesia_id) {
		super();
		this.clase = clase;
		this.metodo = metodo;
		this.descripcion = descripcion;
		this.usuario = usuario;
		this.fecha = fecha;
		this.iglesia_id = iglesia_id;
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

	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}
	
}
