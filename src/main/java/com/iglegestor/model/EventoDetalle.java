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
public class EventoDetalle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String comentario;
	
    @JsonIgnore
	@JoinColumn(name = "miembro_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Miembro.class, fetch = FetchType.EAGER)
    private Miembro miembro;
	
    @Column(name = "miembro_id")
    private int miembro_id;
    
	@JsonIgnore
	@JoinColumn(name = "evento_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Evento.class, fetch = FetchType.EAGER)
    private Evento evento;
	
	@Column(name = "evento_id")
    private int evento_id;

	public EventoDetalle() {
		super();
	}

	public EventoDetalle(String comentario, int miembro_id, int evento_id) {
		super();
		this.comentario = comentario;
		this.miembro_id = miembro_id;
		this.evento_id = evento_id;
	}

	public int getMiembro_id() {
		return miembro_id;
	}

	public void setMiembro_id(int miembro_id) {
		this.miembro_id = miembro_id;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public int getEvento_id() {
		return evento_id;
	}

	public void setEvento_id(int evento_id) {
		this.evento_id = evento_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public Miembro getMiembro() {
		return miembro;
	}

	public void setMiembro(Miembro miembro) {
		this.miembro = miembro;
	}

	public Evento getevento() {
		return evento;
	}

	public void setevento(Evento evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "EventoDetalle [id=" + id + ", comentario=" + comentario + ", miembro_id=" + miembro_id+ ", evento_id="
				+ evento_id + "]";
	}
	
}
