package com.iglegestor.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Movimiento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String descripcion;
	@Temporal(TemporalType.TIMESTAMP)
    private Calendar fecha;

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Iglesia iglesia;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movimiento")
    private List<MovimientoDetalle> detalleMovimiento;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Cierre cierre;

	public Movimiento() {
		super();
	}

	public Movimiento(String descripcion, Calendar fecha, Iglesia iglesia, List<MovimientoDetalle> detalleMovimiento,
			Cierre cierre) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.iglesia = iglesia;
		this.detalleMovimiento = detalleMovimiento;
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

	public Calendar getFecha() {
		return fecha;
	}

	public void setFecha(Calendar fecha) {
		this.fecha = fecha;
	}

	public Iglesia getIglesia() {
		return iglesia;
	}

	public void setIglesia(Iglesia iglesia) {
		this.iglesia = iglesia;
	}

	public List<MovimientoDetalle> getDetalleMovimiento() {
		return detalleMovimiento;
	}

	public void setDetalleMovimiento(List<MovimientoDetalle> detalleMovimiento) {
		this.detalleMovimiento = detalleMovimiento;
	}

	public Cierre getCierre() {
		return cierre;
	}

	public void setCierre(Cierre cierre) {
		this.cierre = cierre;
	}

	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", iglesia=" + iglesia
				+ ", cierre=" + cierre.getId() + "]";
	}
	
	
}
