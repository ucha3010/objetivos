package com.damian.objetivos.service;

import java.util.List;

import com.damian.objetivos.model.SubcategoriaModel;

public interface SubcategoriaService {
	
	public abstract List<SubcategoriaModel> listAll();
	
	public abstract SubcategoriaModel addOrUpdate(SubcategoriaModel subcategoria);
	
	public abstract int remove(int id);
	
	public abstract SubcategoriaModel findById(int id);

}
