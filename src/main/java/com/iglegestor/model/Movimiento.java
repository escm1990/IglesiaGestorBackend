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
public class Movimiento implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String descripcion;

    private Long fecha;

	@JsonIgnore
	@JoinColumn(name = "iglesia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Iglesia.class, fetch = FetchType.EAGER)
    private Iglesia iglesia;
	
	@Column(name = "iglesia_id")
    private int iglesia_id;

	@JsonIgnore
	@JoinColumn(name = "cierre_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Cierre.class, fetch = FetchType.EAGER)
    private Cierre cierre;
	
	@Column(name = "cierre_id")
    private int cierre_id;

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "movimiento_id")
    private List<MovimientoDetalle> detalleMovimiento;
	
	private String ultimoUsuario;
	
	public Movimiento() {
		super();
	}

	public Movimiento(String descripcion, Long fecha, int iglesia_id, int cierre_id, String ultimoUsuario) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.iglesia_id = iglesia_id;
		this.cierre_id = cierre_id;
		this.ultimoUsuario = ultimoUsuario;
	}

	public int getIglesia_id() {
		return iglesia_id;
	}

	public void setIglesia_id(int iglesia_id) {
		this.iglesia_id = iglesia_id;
	}

	public int getCierre_id() {
		return cierre_id;
	}

	public void setCierre_id(int cierre_id) {
		this.cierre_id = cierre_id;
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

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
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
	
	public String getUltimoUsuario() {
		return ultimoUsuario;
	}

	public void setUltimoUsuario(String ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}

	@Override
	public String toString() {
		return "Movimiento [id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", iglesia_id=" + iglesia_id
				+ ", cierre_id=" + cierre_id + ", ultimoUsuario=" + ultimoUsuario + "]";
	}
	
	
}
