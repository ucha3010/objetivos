package com.damian.objetivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damian.objetivos.converter.EntradaConverter;
import com.damian.objetivos.entity.Entrada;
import com.damian.objetivos.entity.Subcategoria;
import com.damian.objetivos.model.CategoriaModel;
import com.damian.objetivos.model.EntradaModel;
import com.damian.objetivos.repository.EntradaJpaRepository;
import com.damian.objetivos.repository.SubcategoriaJpaRepository;
import com.damian.objetivos.service.EntradaService;

@Service()
public class EntradaServiceImpl implements EntradaService{
	
	@Autowired
	private EntradaJpaRepository entradaJpaRepository;
	
	@Autowired
	private EntradaConverter entradaConverter;
	
	@Autowired
	private SubcategoriaJpaRepository subcategoriaJpaRepository;
	

	@Override
	public List<EntradaModel> listAll() {
		return fillEntradaModel(entradaJpaRepository.findAll());
	}

	@Override
	public List<EntradaModel> listAllOrdered() {
		return fillEntradaModel(entradaJpaRepository.findAllByOrderByCategoriaAscSubcategoriaAsc());
	}

	@Override
	public EntradaModel findById(int id) {
		return entradaConverter.entity2Model(entradaJpaRepository.getById(id));
	}

	@Override
	public EntradaModel addOrUpdate(EntradaModel entrada) {
		return entradaConverter.entity2Model(entradaJpaRepository.save(entradaConverter.model2Entity(entrada)));
	}

	@Override
	public int remove(int id) {
		entradaJpaRepository.deleteById(id);
		return 0;
	}

	@Override
	public List<EntradaModel> listBySubcategoria(int idSubcategoria) {
		return fillEntradaModel(entradaJpaRepository.findBySubcategoriaOrderByFecha(idSubcategoria));
	}
	
	private List<EntradaModel> fillEntradaModel (List<Entrada> entradas) {
		List<EntradaModel> entradaModelList = new ArrayList<>();
		for (Entrada entrada: entradas) {
			entradaModelList.add(entradaConverter.entity2Model(entrada));
		}
		return entradaModelList;		
	}

	@Override
	public EntradaModel fillCategoriaId(EntradaModel entrada) {
		Subcategoria subcategoria = subcategoriaJpaRepository.findById(entrada.getSubcategoria().getId());
		CategoriaModel categoria = new CategoriaModel();
		categoria.setId(subcategoria.getIdCategoria());
		entrada.setCategoria(categoria);
		return entrada;
	}

}
