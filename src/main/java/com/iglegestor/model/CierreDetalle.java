package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CierreDetalle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	private String descripcion;
	private double monto;
	
	@ManyToOne
    @JoinColumn(name = "cierre_id", nullable = false, updatable = false)
    private Cierre cierre;

	public CierreDetalle() {
		super();
	}

	public CierreDetalle(String descripcion, double monto, Cierre cierre) {
		super();
		this.descripcion = descripcion;
		this.monto = monto;
		this.cierre = cierre;
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

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Cierre getCierre() {
		return cierre;
	}

	public void setCierre(Cierre cierre) {
		this.cierre = cierre;
	}

	@Override
	public String toString() {
		return "CierreDetalle [id=" + id + ", descripcion=" + descripcion + ", monto=" + monto + ", cierre="
				+ cierre.getId() + "]";
	}
	
	
	
}
