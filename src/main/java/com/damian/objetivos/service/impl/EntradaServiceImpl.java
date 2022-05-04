package com.damian.objetivos.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.damian.objetivos.converter.EntradaConverter;
import com.damian.objetivos.entity.Entrada;
import com.damian.objetivos.model.EntradaModel;
import com.damian.objetivos.repository.EntradaJpaRepository;
import com.damian.objetivos.service.EntradaService;

@Service()
public class EntradaServiceImpl implements EntradaService{
	
	@Autowired
	private EntradaJpaRepository entradaJpaRepository;
	
	@Autowired
	private EntradaConverter entradaConverter;
	

	@Override
	public List<EntradaModel> listAll() {
		return fillEntradaModel(entradaJpaRepository.findAll());
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

}
