package com.damian.objetivos.controller;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.damian.objetivos.model.EntradaModel;
import com.damian.objetivos.model.SubcategoriaModel;
import com.damian.objetivos.service.CategoriaService;
import com.damian.objetivos.service.EntradaService;
import com.damian.objetivos.service.SubcategoriaService;
import com.damian.objetivos.util.LoggerMapper;

@Controller
@RequestMapping("/entrada")
public class EntradaController {
	
	@Autowired
	private EntradaService entradaService;
	
	@Autowired
	private CategoriaService categoriaService;
	
	@Autowired
	private SubcategoriaService subcategoriaService;
	
	@GetMapping("/listaEntradas")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView listaEntradas() {
		ModelAndView modelAndView = new ModelAndView("resumen");
		modelAndView.addObject("entradas", entradaService.listAll());
		modelAndView.addObject("categorias", categoriaService.listAll());
		LoggerMapper.log(Level.INFO, "listaEntradas", modelAndView, getClass());
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modelAndView.addObject("username", user.getUsername());
		return modelAndView;
	}
	
	@GetMapping("/formularioEntrada")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView formularioEntrada(@ModelAttribute("idSubcategoria") int idSubcategoria) {
		ModelAndView modelAndView = new ModelAndView("formularioEntrada");
		modelAndView.addObject("subcategoriaId", idSubcategoria);
		SubcategoriaModel subcategoria = new SubcategoriaModel();
		EntradaModel entrada = new EntradaModel();
		entrada.setSubcategoria(subcategoria);
		modelAndView.addObject("entrada", entrada);
		LoggerMapper.log(Level.INFO, "formularioEntrada", modelAndView, getClass());
		return modelAndView;
	}
	
	@PostMapping("/agregarEntrada")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String agregarEntrada(@ModelAttribute("entrada") EntradaModel entrada) {
		entradaService.fillCategoriaId(entrada);
		EntradaModel entradaExit = entradaService.addOrUpdate(entrada);
		LoggerMapper.log(Level.INFO, "agregarEntrada", entradaExit, getClass());
		return "redirect:/entrada/listaEntradas";
	}
	
	@GetMapping("/entradasSubcategoria")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView entradasPorSubcategoria(@ModelAttribute("idSubcategoria") int idSubcategoria) {
		ModelAndView modelAndView = new ModelAndView("subcategoria");
		modelAndView.addObject("entradas", entradaService.listBySubcategoria(idSubcategoria));
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("subcategoriaElegida", subcategoriaService.findById(idSubcategoria));
		LoggerMapper.log(Level.INFO, "entradasSubcategoria", modelAndView, getClass());
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		modelAndView.addObject("username", user.getUsername());
		return modelAndView;
	}

}
