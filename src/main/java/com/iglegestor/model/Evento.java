package com.iglegestor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Evento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descripcion;
	private Long fecha; 
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Iglesia iglesia;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TipoEvento tipoEvento;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
     private List<EventoDetalle> detalleEvento;

	public Evento() {
		super();
	}

	public Evento(String descripcion, Long fecha, Iglesia iglesia, TipoEvento tipoEvento) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.iglesia = iglesia;
		this.tipoEvento = tipoEvento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Long getFechaEvento() {
		return fecha;
	}

	public void setFechaEvento(Long fecha) {
		this.fecha = fecha;
	}

	public Iglesia getIglesia() {
		return iglesia;
	}

	public void setIglesia(Iglesia iglesia) {
		this.iglesia = iglesia;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

	public void setTipoEvento(TipoEvento tipoEvento) {
		this.tipoEvento = tipoEvento;
	}

	public List<EventoDetalle> getDetalleEvento() {
		return detalleEvento;
	}

	public void setDetalleEvento(List<EventoDetalle> detalleEvento) {
		this.detalleEvento = detalleEvento;
	}

	@Override
	public String toString() {
		return "Evento [id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", iglesia="
				+ iglesia.getId() + ", tipoEvento=" + tipoEvento.getId() +"]";
	}

	
}
