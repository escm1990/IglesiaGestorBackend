package com.iglegestor.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iglegestor.model.TipoEvento;

public interface TipoEventoDao extends JpaRepository<TipoEvento, Integer>{

}
