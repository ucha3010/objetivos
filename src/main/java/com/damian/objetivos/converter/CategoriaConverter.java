package com.damian.objetivos.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.damian.objetivos.entity.Categoria;
import com.damian.objetivos.entity.Subcategoria;
import com.damian.objetivos.model.CategoriaModel;
import com.damian.objetivos.model.SubcategoriaModel;
import com.damian.objetivos.repository.SubcategoriaJpaRepository;

@Component
public class CategoriaConverter {
	
	@Autowired
	private SubcategoriaJpaRepository subcategoriaJpaRepository;
	
	@Autowired
	private SubcategoriaConverter subcategoriaConverter;
	
	public CategoriaModel entity2Model(Categoria externObject) {
		CategoriaModel localObject = new CategoriaModel();
		localObject.setId(externObject.getId());
		localObject.setName(externObject.getName());
		List<Subcategoria> subcategoriaList = subcategoriaJpaRepository.findByIdCategoria(externObject.getId());
		List<SubcategoriaModel> subcategoriaModelList = new ArrayList<>(); 
		for(Subcategoria subcategoria: subcategoriaList) {
			subcategoriaModelList.add(subcategoriaConverter.entity2Model(subcategoria));
		}
		localObject.setSubcategoriaList(subcategoriaModelList);
		return localObject;
		
	}
	
	public Categoria model2Entity(CategoriaModel externObject) {
		Categoria localObject = new Categoria();
		localObject.setId(externObject.getId());
		localObject.setName(externObject.getName());
		return localObject;
		
	}

}
