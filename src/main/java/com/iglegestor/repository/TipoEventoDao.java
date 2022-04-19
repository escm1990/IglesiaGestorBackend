package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.TipoEvento;

public interface TipoEventoDao extends JpaRepository<TipoEvento, Integer>{


}
