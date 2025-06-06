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
public class Miembro implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nombre;
	private String apellido;
	private Long fechaNacimiento;
	private String sexo;
	private String estadoCivil;
	private String direccion;
	private String telefonoFijo;
	private String telefonoMovil;	
	private String correo;
	private Long fechaConversion;
	private Long fechaBautismo;
	private String estado;
	private String foto;
	private String ultimoUsuario;
	
	@JsonIgnore
	@JoinColumn(name = "iglesia_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = Iglesia.class, fetch = FetchType.EAGER)
    private Iglesia iglesia;
	
	@Column(name = "iglesia_id")
    private int iglesia_id;
	
	@JsonIgnore
	@JoinColumn(name = "tipo_persona_id", insertable = false, updatable = false)
    @ManyToOne(targetEntity = TipoPersona.class, fetch = FetchType.EAGER)
    private TipoPersona tipoPersona;
	
	@Column(name = "tipo_persona_id")
    private int tipo_persona_id;

	public Miembro() {
		super();
	}

	public Miembro(String nombre, String apellido, Long fechaNacimiento, String sexo, String estadoCivil,
			String direccion, String telefonoFijo, String telefonoMovil, String correo, Long fechaConversion,
			Long fechaBautismo, String estado, String foto, int iglesia_id, int tipo_persona_id, String ultimoUsuario) {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.fechaNacimiento = fechaNacimiento;
		this.sexo = sexo;
		this.estadoCivil = estadoCivil;
		this.direccion = direccion;
		this.telefonoFijo = telefonoFijo;
		this.telefonoMovil = telefonoMovil;
		this.correo = correo;
		this.fechaConversion = fechaConversion;
		this.fechaBautismo = fechaBautismo;
		this.estado = estado;
		this.foto = foto;
		this.iglesia_id = iglesia_id;
		this.tipo_persona_id = tipo_persona_id;
		this.ultimoUsuario = ultimoUsuario;
	}

	public String getUltimoUsuario() {
		return ultimoUsuario;
	}

	public void setUltimoUsuario(String ultimoUsuario) {
		this.ultimoUsuario = ultimoUsuario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Long getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Long fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefonoFijo() {
		return telefonoFijo;
	}

	public void setTelefonoFijo(String telefonoFijo) {
		this.telefonoFijo = telefonoFijo;
	}

	public String getTelefonoMovil() {
		return telefonoMovil;
	}

	public void setTelefonoMovil(String telefonoMovil) {
		this.telefonoMovil = telefonoMovil;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Long getFechaBautismo() {
		return fechaBautismo;
	}

	public void setFechaBautismo(Long fechaBautismo) {
		this.fechaBautismo = fechaBautismo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Iglesia getIglesia() {
		return iglesia;
	}

	public void setIglesia(Iglesia iglesia) {
		this.iglesia = iglesia;
	}

	public TipoPersona getTipoPersona() {
		return tipoPersona;
	}

	public void setTipoPersona(TipoPersona tipoPersona) {
		this.tipoPersona = tipoPersona;
	}

	public Long getFechaConversion() {
		return fechaConversion;
	}

	public void setFechaConversion(Long fechaConversion) {
		this.fechaConversion = fechaConversion;
	}

	public int getIglesia_id() {
		return iglesia_id;
	}

	public void setIglesia_id(int iglesia_id) {
		this.iglesia_id = iglesia_id;
	}

	public int getTipo_persona_id() {
		return tipo_persona_id;
	}

	public void setTipo_persona_id(int tipo_persona_id) {
		this.tipo_persona_id = tipo_persona_id;
	}

	@Override
	public String toString() {
		return "Miembro [id=" + id + ", nombre=" + nombre + ", apellido=" + apellido + ", fechaNacimiento="
				+ fechaNacimiento + ", sexo=" + sexo + ", estadoCivil=" + estadoCivil + ", direccion=" + direccion
				+ ", telefonoFijo=" + telefonoFijo + ", telefonoMovil=" + telefonoMovil + ", correo=" + correo
				+ ", fechaConversion=" + fechaConversion + ", fechaBautismo=" + fechaBautismo + ", estado=" + estado
				+ ", foto=" + foto + ", iglesia_id=" + iglesia_id + ", tipo_persona_id=" + tipo_persona_id 
				+  ", ultimoUsuario=" + ultimoUsuario + "]";
	}

	
	
}
