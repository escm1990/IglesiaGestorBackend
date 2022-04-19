package com.iglegestor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iglegestor.model.TipoRegistroMovimiento;

public interface TipoRegistroMovimientoDao extends JpaRepository<TipoRegistroMovimiento, Integer>{


}
