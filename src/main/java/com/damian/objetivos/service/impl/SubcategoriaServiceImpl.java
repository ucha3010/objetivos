package com.damian.objetivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damian.objetivos.converter.SubcategoriaConverter;
import com.damian.objetivos.entity.Subcategoria;
import com.damian.objetivos.model.SubcategoriaModel;
import com.damian.objetivos.repository.SubcategoriaJpaRepository;
import com.damian.objetivos.service.CategoriaService;
import com.damian.objetivos.service.SubcategoriaService;

@Service()
public class SubcategoriaServiceImpl implements SubcategoriaService{
	
	@Autowired
	private SubcategoriaJpaRepository subcategoriaJpaRepository;
	
	@Autowired
	private SubcategoriaConverter subcategoriaConverter;
	
	@Autowired
	private CategoriaService categoriaService;
	

	@Override
	public List<SubcategoriaModel> listAll() {
		return fillSubcategoriaModel(subcategoriaJpaRepository.findAllByOrderByIdCategoriaAscIdAsc());
	}

	@Override
	public SubcategoriaModel addOrUpdate(SubcategoriaModel subcategoria) {
		return subcategoriaConverter.entity2Model(subcategoriaJpaRepository.save(subcategoriaConverter.model2Entity(subcategoria)));
	}

	@Override
	public int remove(int id) {
		subcategoriaJpaRepository.deleteById(id);
		return 0;
	}

	@Override
	public SubcategoriaModel findById(int id) {
		SubcategoriaModel subcategoria = subcategoriaConverter.entity2Model(subcategoriaJpaRepository.findById(id));
		if(subcategoria.getCategoria() != null) {
			subcategoria.setCategoria(categoriaService.findById(subcategoria.getCategoria().getId()));
		}
		return subcategoria;
	}
	
	private List<SubcategoriaModel> fillSubcategoriaModel (List<Subcategoria> subcategorias) {
		List<SubcategoriaModel> subcategoriaModelList = new ArrayList<>();
		for (Subcategoria subcategoria: subcategorias) {
			subcategoriaModelList.add(subcategoriaConverter.entity2Model(subcategoria));
		}
		return subcategoriaModelList;		
	}

}
