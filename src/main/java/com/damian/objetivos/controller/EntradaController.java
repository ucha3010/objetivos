package com.damian.objetivos.controller;

import com.damian.objetivos.model.ObjetivoHitoModel;
import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.damian.objetivos.model.EntradaModel;
import com.damian.objetivos.model.SubcategoriaModel;
import com.damian.objetivos.repository.UserRepository;
import com.damian.objetivos.service.CategoriaService;
import com.damian.objetivos.service.EntradaService;
import com.damian.objetivos.service.SubcategoriaService;
import com.damian.objetivos.util.LoggerMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/entrada")
public class EntradaController {
	
	@Autowired
	private EntradaService entradaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private SubcategoriaService subcategoriaService;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/listaEntradas")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView listaEntradas() {
		ModelAndView modelAndView = new ModelAndView("resumen");
		List<EntradaModel> entradas = entradaService.listAllOrdered();
		modelAndView.addObject("entradas", entradas);
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("tablaObjetivos", rellenarTablaObjetivos(entradas));
		cargarUsuario(modelAndView);
		LoggerMapper.log(Level.INFO, "listaEntradas", modelAndView, getClass());
		return modelAndView;
	}
	
	@GetMapping("/formularioEntrada")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView formularioEntrada(ModelAndView modelAndView, @ModelAttribute("idSubcategoria") int idSubcategoria,
										  @ModelAttribute("idEntrada") int idEntrada) {
		modelAndView.setViewName("formularioEntrada");
		modelAndView.addObject("subcategoriaId", idSubcategoria);
		SubcategoriaModel subcategoria = new SubcategoriaModel();
		EntradaModel entrada = new EntradaModel();
		if(idEntrada != 0) {
			entrada = entradaService.findById(idEntrada);
		}
		entrada.setSubcategoria(subcategoria);
		modelAndView.addObject("entrada", entrada);
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("subcategoriaElegida", subcategoriaService.findById(idSubcategoria));
		cargarUsuario(modelAndView);
		LoggerMapper.log(Level.INFO, "formularioEntrada", modelAndView, getClass());
		return modelAndView;
	}
	
	@PostMapping("/agregarEntrada")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView agregarEntrada(@ModelAttribute("entrada") EntradaModel entrada) {
		entradaService.fillCategoriaId(entrada);
		EntradaModel entradaExit = entradaService.addOrUpdate(entrada);
		LoggerMapper.log(Level.INFO, "agregarEntrada", entradaExit, getClass());
		return entradasPorSubcategoria(new ModelAndView(), entrada.getSubcategoria().getId());
	}

	@GetMapping("/eliminarEntrada")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView eliminarEntrada(@ModelAttribute("idEntrada") int idEntrada) {
		ModelAndView modelAndView = new ModelAndView();
		EntradaModel entrada = entradaService.findById(idEntrada);
		if (entradaService.remove(idEntrada) == 1) {
			modelAndView.addObject("eliminacionCorrecta","actualizacionCorrecta");
		} else {
			modelAndView.addObject("eliminacionError","eliminacionError");
		}
		LoggerMapper.log(Level.INFO, "eliminarEntrada", modelAndView, getClass());
		return entradasPorSubcategoria(modelAndView, entrada.getSubcategoria().getId());
	}
	
	@GetMapping("/entradasSubcategoria")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView entradasPorSubcategoria(ModelAndView modelAndView, @ModelAttribute("idSubcategoria") int idSubcategoria) {
		modelAndView.setViewName("subcategoria");
		modelAndView.addObject("entradas", entradaService.listBySubcategoria(idSubcategoria));
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("subcategoriaElegida", subcategoriaService.findById(idSubcategoria));
		cargarUsuario(modelAndView);
		LoggerMapper.log(Level.INFO, "entradasSubcategoria", modelAndView, getClass());
		return modelAndView;
	}
	
	private void cargarUsuario(ModelAndView modelAndView) {		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.damian.objetivos.entity.User usuario = userRepository.findByUsername(user.getUsername());
		modelAndView.addObject("username", usuario.getName());
	}

	private List<ObjetivoHitoModel> rellenarTablaObjetivos(List<EntradaModel> entradas) {
		List<SubcategoriaModel> subcategorias = subcategoriaService.listAll();
		List<ObjetivoHitoModel> objetivoHitoModels = new ArrayList<>();
		ObjetivoHitoModel objetivoHitoModel;
		for(SubcategoriaModel subcategoria: subcategorias) {
			objetivoHitoModel = new ObjetivoHitoModel();
			objetivoHitoModel.setIdSubcategoria(subcategoria.getId());
			objetivoHitoModel.setTitulo(subcategoria.getName());
			objetivoHitoModel.setObjetivo(subcategoria.getDescripcion());
			for(EntradaModel entrada: entradas) {
				if(subcategoria.getId() == entrada.getSubcategoria().getId()) {
					objetivoHitoModel.setHito(objetivoHitoModel.getHito()+1);
				}
			}
			objetivoHitoModels.add(objetivoHitoModel);
		}
		return objetivoHitoModels;
	}

}
