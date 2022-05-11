package com.damian.objetivos.service;

import java.util.List;

import com.damian.objetivos.model.EntradaModel;

public interface EntradaService {
	
	public abstract List<EntradaModel> listAll();
	
	public abstract EntradaModel addOrUpdate(EntradaModel entrada);
	
	public abstract int remove(int id);
	
	public abstract List<EntradaModel> listBySubcategoria(int idSubcategoria);

	public abstract EntradaModel fillCategoriaId(EntradaModel entrada);

}
