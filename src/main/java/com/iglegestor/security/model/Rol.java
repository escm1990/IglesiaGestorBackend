package com.iglegestor.security.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.iglegestor.enums.Roles;
import com.iglegestor.model.Iglesia;

@Entity
public class Rol implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	/*
	@JsonIgnore
	@JoinColumn(name = "iglesia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Iglesia.class, fetch = FetchType.EAGER)
    private Iglesia iglesia;
	
	@Column(name = "iglesia_id")
    private int iglesia_id;
*/		
	@NotNull
	@Enumerated(EnumType.STRING)
	private Roles nombre;

	public Rol() {
		super();
	}
	
	public Rol(@NotNull Roles nombre) {	
		this.nombre = nombre;
	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Roles getNombre() {
		return nombre;
	}

	public void setNombre(Roles nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Roles [id=" + id + ", nombre=" + nombre + "]";
	}
	
}
