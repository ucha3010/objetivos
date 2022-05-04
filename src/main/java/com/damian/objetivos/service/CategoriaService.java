package com.damian.objetivos.service;

import java.util.List;

import com.damian.objetivos.model.CategoriaModel;

public interface CategoriaService {
	
	public abstract List<CategoriaModel> listAll();
	
	public abstract CategoriaModel addOrUpdate(CategoriaModel categoria);
	
	public abstract int remove(int id);
	
	public abstract CategoriaModel findById(int id);

}
