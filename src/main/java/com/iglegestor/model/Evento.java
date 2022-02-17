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
public class Evento implements Serializable{

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
	@JoinColumn(name = "tipo_evento_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = TipoEvento.class, fetch = FetchType.EAGER)
    private TipoEvento tipoEvento;
	
	@Column(name = "tipo_evento_id")
    private int tipo_evento_id;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "evento_id")
     private List<EventoDetalle> detalleEvento;

	private String ultimoUsuario;
	
	public Evento() {
		super();
	}

	public Evento(String descripcion, Long fecha, int iglesia_id, int tipo_evento_id, String ultimoUsuario) {
		super();
		this.descripcion = descripcion;
		this.fecha = fecha;
		this.iglesia_id = iglesia_id;
		this.tipo_evento_id = tipo_evento_id;
		this.ultimoUsuario = ultimoUsuario;
	}

	public String getUltimoUsuario() {
		return ultimoUsuario;
	}

	public void setUltimoUsuario(String ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}

	public Long getFecha() {
		return fecha;
	}

	public void setFecha(Long fecha) {
		this.fecha = fecha;
	}

	public int getIglesia_id() {
		return iglesia_id;
	}

	public void setIglesia_id(int iglesia_id) {
		this.iglesia_id = iglesia_id;
	}

	public int getTipo_evento_id() {
		return tipo_evento_id;
	}

	public void setTipo_evento_id(int tipo_evento_id) {
		this.tipo_evento_id = tipo_evento_id;
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
		return "Evento [id=" + id + ", descripcion=" + descripcion + ", fecha=" + fecha + ", iglesia_id="
				+ iglesia_id + ", tipo_evento_id=" + tipo_evento_id + ", ultimoUsuario="+ultimoUsuario+"]";
	}

	
}
