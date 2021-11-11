package com.iglegestor.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;

import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails{

	@NonNull
	private String usuario;
	
	@NonNull
	@Column(unique = true)
	private String password;
	
	@NonNull
	private String correo;
	
	@NonNull
	private String estado;

	private Collection<? extends GrantedAuthority> authorities;
	
	public UsuarioPrincipal(String usuario, String password, String correo, String estado,
			Collection<? extends GrantedAuthority> authorities) {
		super();
		this.usuario = usuario;
		this.password = password;
		this.correo = correo;
		this.estado = estado;
		this.authorities = authorities;
	}

	public static UsuarioPrincipal build(Usuarios usuario) {
		//Convirtiendo la clase Roles en GrantedAuthority
		List<GrantedAuthority> authorities = usuario.getRoles().stream().map(
				rol -> new SimpleGrantedAuthority(rol.getNombre().name())).collect(Collectors.toList());
		return new UsuarioPrincipal(usuario.getUsuario(), usuario.getPassword(), usuario.getCorreo(), usuario.getEstado(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getCorreo() {
		return correo;
	}

	public String getEstado() {
		return estado;
	}
	
}
