package com.iglegestor.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class MovimientoDetalle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String comentario;
	private String estado;
	private double monto;
	
	@JsonIgnore
	@JoinColumn(name = "movimiento_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Movimiento.class, fetch = FetchType.EAGER)
    private Movimiento movimiento;
	
	@Column(name = "movimiento_id")
    private int movimiento_id;
	
	@JsonIgnore
	@JoinColumn(name = "miembro_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Miembro.class, fetch = FetchType.EAGER)
    private Miembro miembro;
	
    @Column(name = "miembro_id")
    private int miembro_id;
	
    @JsonIgnore
	@JoinColumn(name = "tipo_registro_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = TipoRegistroMovimiento.class, fetch = FetchType.EAGER)
    private TipoRegistroMovimiento tipoRegistroMovimiento;
	
    @Column(name = "tipo_registro_id")
    private int tipo_registro_id;;
	
    private String ultimoUsuario;

	public MovimientoDetalle() {
		super();
	}

	public MovimientoDetalle(String estado, String comentario, double monto, int movimiento_id, 
							int miembro_id, int tipo_registro_id, String ultimoUsuario) {
		super();
		this.estado = estado;
		this.comentario = comentario;
		this.monto = monto;
		this.movimiento_id = movimiento_id;
		this.miembro_id = miembro_id;
		this.tipo_registro_id = tipo_registro_id;
		this.ultimoUsuario = ultimoUsuario;
	}

	public String getUltimoUsuario() {
		return ultimoUsuario;
	}

	public void setUltimoUsuario(String ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}

	public int getMovimiento_id() {
		return movimiento_id;
	}

	public void setMovimiento_id(int movimiento_id) {
		this.movimiento_id = movimiento_id;
	}

	public int getMiembro_id() {
		return miembro_id;
	}

	public void setMiembro_id(int miembro_id) {
		this.miembro_id = miembro_id;
	}

	public TipoRegistroMovimiento getTipoRegistroMovimiento() {
		return tipoRegistroMovimiento;
	}

	public void setTipoRegistroMovimiento(TipoRegistroMovimiento tipoRegistroMovimiento) {
		this.tipoRegistroMovimiento = tipoRegistroMovimiento;
	}

	public int getTipo_registro_id() {
		return tipo_registro_id;
	}

	public void setTipo_registro_id(int tipo_registro_id) {
		this.tipo_registro_id = tipo_registro_id;
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

	@Override
	public String toString() {
		return "MovimientoDetalle [id=" + id + ", estado=" + estado + ", comentario=" + comentario + ", monto=" + monto
				+ ", movimiento_id=" + movimiento_id + ", miembro_id=" + miembro_id + ", tipo_registro_id=" + tipo_registro_id
				+ ", ultimoUsuario=" + ultimoUsuario + "]";
	}

}
