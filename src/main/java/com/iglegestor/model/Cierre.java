package com.iglegestor.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Cierre implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descripcion;
	private double total;
	private Long fecha;
	private Long fechaEjecucion;
	
	@JsonIgnore
	@JoinColumn(name = "iglesia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Iglesia.class, fetch = FetchType.EAGER)
    private Iglesia iglesia;
	
	@Column(name = "iglesia_id")
    private int iglesia_id;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "cierre_id")
    private List<CierreDetalle> detalleCierre;

	public Cierre() {
		super();
	}

	public Cierre(String descripcion, double total, Long fecha, Long fechaEjecucion, int iglesia_id) {
		super();
		this.descripcion = descripcion;
		this.total = total;
		this.fecha = fecha;
		this.fechaEjecucion = fechaEjecucion;
		this.iglesia_id =  iglesia_id;
	}
	
	public int getIglesia_id() {
		return iglesia_id;
	}

	public void setIglesia_id(int iglesia_id) {
		this.iglesia_id = iglesia_id;
	}

	public Iglesia getIglesia() {
		return iglesia;
	}

	public void setIglesia(Iglesia iglesia) {
		this.iglesia = iglesia;
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

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	public Long getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Long fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public List<CierreDetalle> getDetalleCierre() {
		return detalleCierre;
	}

	public void setDetalleCierre(List<CierreDetalle> detalleCierre) {
		this.detalleCierre = detalleCierre;
	}

	@Override
	public String toString() {
		return "Cierre [id=" + id + ", descripcion=" + descripcion + ", total=" + total + ", fecha=" + fecha
				+ ", fechaEjecucion=" + fechaEjecucion + ", iglesia_id=" + iglesia_id+ "]";
	}

	
}
