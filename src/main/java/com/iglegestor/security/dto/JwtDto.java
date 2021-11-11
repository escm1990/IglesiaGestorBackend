package com.iglegestor.security.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class JwtDto {
	
	private String token;
	private String bearer = "Bearer";
	private String usuario;
	private Collection<? extends GrantedAuthority> authorities;
	
	public JwtDto(String token, String usuario, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.usuario = usuario;
		this.authorities = authorities;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getBearer() {
		return bearer;
	}

	public void setBearer(String bearer) {
		this.bearer = bearer;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	
}
