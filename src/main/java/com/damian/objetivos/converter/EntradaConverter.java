package com.damian.objetivos.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.damian.objetivos.entity.Entrada;
import com.damian.objetivos.model.EntradaModel;
import com.damian.objetivos.repository.CategoriaJpaRepository;
import com.damian.objetivos.repository.SubcategoriaJpaRepository;

@Component
public class EntradaConverter {
	
	@Autowired
	private CategoriaJpaRepository categoriaJpaRepository;
	
	@Autowired
	private SubcategoriaJpaRepository subcategoriaJpaRepository;

	@Autowired
	private CategoriaConverter categoriaConverter;
	
	@Autowired
	private SubcategoriaConverter subcategoriaConverter;
	
	public EntradaModel entity2Model(Entrada externObject) {
		EntradaModel localObject = new EntradaModel();
		localObject.setId(externObject.getId());
		localObject.setFecha(externObject.getFecha());
		localObject.setDetalle(externObject.getDetalle());
		if(externObject.getCategoria() != 0) {
			localObject.setCategoria(categoriaConverter.entity2Model(categoriaJpaRepository.getById(externObject.getCategoria())));
		}
		if(externObject.getSubcategoria() != 0) {
			localObject.setSubcategoria(subcategoriaConverter.entity2Model(subcategoriaJpaRepository.getById(externObject.getSubcategoria())));
		}
		return localObject;
		
	}
	
	public Entrada model2Entity(EntradaModel externObject) {
		Entrada localObject = new Entrada();
		localObject.setId(externObject.getId());
		localObject.setFecha(externObject.getFecha());
		localObject.setDetalle(externObject.getDetalle());
		if(externObject.getCategoria() != null) {
			localObject.setCategoria(externObject.getCategoria().getId());
		}
		if(externObject.getSubcategoria() != null) {
			localObject.setSubcategoria(externObject.getSubcategoria().getId());
		}
		return localObject;
		
	}

}
