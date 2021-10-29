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
public class EventoDetalle implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    private String comentario;
	
	@ManyToOne(optional = true, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Miembro miembro;
	
	@ManyToOne
    @JoinColumn(name = "evento_id", nullable = false, updatable = false)
    private Evento evento;

	public EventoDetalle() {
		super();
	}

	public EventoDetalle(String comentario, Miembro miembro, Evento evento) {
		super();
		this.comentario = comentario;
		this.miembro = miembro;
		this.evento = evento;
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
		return "EventoDetalle [id=" + id + ", comentario=" + comentario + ", miembro=" + miembro.getId() + ", evento="
				+ evento.getId() + "]";
	}
	
}
