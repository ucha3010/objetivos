package com.damian.objetivos.converter;

import org.springframework.stereotype.Component;

import com.damian.objetivos.entity.Subcategoria;
import com.damian.objetivos.model.CategoriaModel;
import com.damian.objetivos.model.SubcategoriaModel;

@Component
public class SubcategoriaConverter {
	
	public SubcategoriaModel entity2Model(Subcategoria externObject) {
		SubcategoriaModel localObject = new SubcategoriaModel();
		localObject.setId(externObject.getId());
		localObject.setName(externObject.getName());
		localObject.setDescripcion(externObject.getDescripcion());
		if(externObject.getIdCategoria() != 0) {
			CategoriaModel categoria = new CategoriaModel();
			categoria.setId(externObject.getIdCategoria());
			localObject.setCategoria(categoria);
		}
		return localObject;
		
	}
	
	public Subcategoria model2Entity(SubcategoriaModel externObject) {
		Subcategoria localObject = new Subcategoria();
		localObject.setId(externObject.getId());
		localObject.setName(externObject.getName());
		localObject.setDescripcion(externObject.getDescripcion());
		if(externObject.getCategoria() != null) {
			localObject.setIdCategoria(externObject.getCategoria().getId());
		}
		return localObject;
		
	}

}
