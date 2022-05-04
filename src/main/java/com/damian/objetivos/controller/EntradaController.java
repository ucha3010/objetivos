package com.damian.objetivos.controller;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.damian.objetivos.model.EntradaModel;
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
	
	final String SUBCATEGORIA = "subcategoria";
	
	@GetMapping("/listaEntradas")
	public ModelAndView listaEntradas() {
		ModelAndView modelAndView = new ModelAndView("resumen");
		modelAndView.addObject("entradas", entradaService.listAll());
		modelAndView.addObject("categorias", categoriaService.listAll());
		LoggerMapper.log(Level.INFO, "listaEntradas", modelAndView, getClass());
		return modelAndView;
	}
	
	@PostMapping("/agregarEntrada")
	public String agregarEntrada(@ModelAttribute("entrada") EntradaModel entrada) {
		EntradaModel entradaExit = entradaService.addOrUpdate(entrada);
		LoggerMapper.log(Level.INFO, "agregarEntrada", entradaExit, getClass());
		return "redirect:/entrada/listaEntradas";
	}
	
	@GetMapping("/entradasSubcategoria")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView entradasPorSubcategoria(@ModelAttribute("idSubcategoria") int idSubcategoria) {
		ModelAndView modelAndView = new ModelAndView(SUBCATEGORIA);
		modelAndView.addObject("entradas", entradaService.listBySubcategoria(idSubcategoria));
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("subcategoriaElegida", subcategoriaService.findById(idSubcategoria));
		LoggerMapper.log(Level.INFO, "entradasSubcategoria", modelAndView, getClass());
		
		//User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//modelAndView.addObject("username", user.getUsername());
		return modelAndView;
	}

}
