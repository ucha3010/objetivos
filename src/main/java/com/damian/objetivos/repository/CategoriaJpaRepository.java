package com.damian.objetivos.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damian.objetivos.entity.Categoria;

@Repository("categoriaJpaRepository")
public interface CategoriaJpaRepository extends JpaRepository<Categoria, Serializable>{
	
	public abstract Categoria findById(int id);

}
