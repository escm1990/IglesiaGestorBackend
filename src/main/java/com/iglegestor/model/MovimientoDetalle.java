package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class MovimientoDetalle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String comentario;
	private String estado;
	private double monto;
	
	@ManyToOne
    @JoinColumn(name = "movimiento_id", nullable = false, updatable = false)
    private Movimiento movimiento;
	
	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Miembro miembro;
	
	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private TipoRegistroMovimiento tipoRegistroMovimiento;

	public MovimientoDetalle() {
		super();
	}

	public MovimientoDetalle(String estado, String comentario, double monto, Movimiento movimiento, Miembro miembro,
			TipoRegistroMovimiento tipoRegistroMovimiento) {
		super();
		this.estado = estado;
		this.comentario = comentario;
		this.monto = monto;
		this.movimiento = movimiento;
		this.miembro = miembro;
		this.tipoRegistroMovimiento = tipoRegistroMovimiento;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public double getMonto() {
		return monto;
	}

	public void setMonto(double monto) {
		this.monto = monto;
	}

	public Movimiento getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(Movimiento movimiento) {
		this.movimiento = movimiento;
	}

	public Miembro getMiembro() {
		return miembro;
	}

	public void setMiembro(Miembro miembro) {
		this.miembro = miembro;
	}

	public TipoRegistroMovimiento getTipoMovimiento() {
		return tipoRegistroMovimiento;
	}

	public void setTipoMovimiento(TipoRegistroMovimiento tipoRegistroMovimiento) {
		this.tipoRegistroMovimiento = tipoRegistroMovimiento;
	}

	@Override
	public String toString() {
		return "MovimientoDetalle [id=" + id + ", estado=" + estado + ", comentario=" + comentario + ", monto=" + monto
				+ ", movimiento_id=" + movimiento.getId() + ", miembro=" + miembro.getId() + ", tipoRegistroMovimiento=" + tipoRegistroMovimiento.getId()
				+ "]";
	}

}
