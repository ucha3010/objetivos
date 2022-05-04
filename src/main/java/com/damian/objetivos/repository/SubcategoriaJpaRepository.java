package com.damian.objetivos.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.damian.objetivos.entity.Subcategoria;

@Repository("subcategoriaJpaRepository")
public interface SubcategoriaJpaRepository extends JpaRepository<Subcategoria, Serializable>{
	
	public abstract Subcategoria findById(int id);
	
	public abstract List<Subcategoria> findByIdCategoria(int idCategoria);

}
