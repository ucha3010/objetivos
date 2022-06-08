package com.damian.objetivos.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damian.objetivos.entity.Entrada;

@Repository("entradaJpaRepository")
public interface EntradaJpaRepository extends JpaRepository<Entrada, Serializable>{
	
	public abstract List<Entrada> findBySubcategoriaOrderByFecha(int subcategoria);

	public abstract List<Entrada> findAllByOrderByCategoriaAscSubcategoriaAsc();

}
