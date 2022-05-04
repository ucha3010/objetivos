package com.damian.objetivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damian.objetivos.converter.CategoriaConverter;
import com.damian.objetivos.entity.Categoria;
import com.damian.objetivos.model.CategoriaModel;
import com.damian.objetivos.repository.CategoriaJpaRepository;
import com.damian.objetivos.service.CategoriaService;

@Service()
public class CategoriaServiceImpl implements CategoriaService{
	
	@Autowired
	private CategoriaJpaRepository categoriaJpaRepository;
	
	@Autowired
	private CategoriaConverter categoriaConverter;
	

	@Override
	public List<CategoriaModel> listAll() {
		return fillCategoriaModel(categoriaJpaRepository.findAll());
	}

	@Override
	public CategoriaModel addOrUpdate(CategoriaModel categoria) {
		return categoriaConverter.entity2Model(categoriaJpaRepository.save(categoriaConverter.model2Entity(categoria)));
	}

	@Override
	public int remove(int id) {
		categoriaJpaRepository.deleteById(id);
		return 0;
	}

	@Override
	public CategoriaModel findById(int id) {
		return categoriaConverter.entity2Model(categoriaJpaRepository.findById(id));
	}
	
	private List<CategoriaModel> fillCategoriaModel (List<Categoria> categorias) {
		List<CategoriaModel> categoriaModelList = new ArrayList<>();
		for (Categoria categoria: categorias) {
			categoriaModelList.add(categoriaConverter.entity2Model(categoria));
		}
		return categoriaModelList;		
	}

}
