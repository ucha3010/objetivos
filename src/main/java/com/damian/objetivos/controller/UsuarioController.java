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

import com.damian.objetivos.repository.UserRepository;
import com.damian.objetivos.service.CategoriaService;
import com.damian.objetivos.service.impl.UserService;
import com.damian.objetivos.util.LoggerMapper;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/listaUsuarios")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listaUsuarios() {//TODO sin hacer
		ModelAndView modelAndView = new ModelAndView("usuarios");
		cargarUsuario(modelAndView);
		LoggerMapper.log(Level.INFO, "listaUsuarios", modelAndView, getClass());
		return modelAndView;
	}
	
	@GetMapping("/formularioUsuario")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView formularioUsuario() {
		ModelAndView modelAndView = new ModelAndView("formularioUsuario");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.damian.objetivos.entity.User usuario = userRepository.findByUsername(user.getUsername());
		modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("categorias", categoriaService.listAll());
		LoggerMapper.log(Level.INFO, "formularioUsuario", modelAndView, getClass());
		return modelAndView;
	}
	
	@PostMapping("/actualizarUsuario")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public String actualizarUsuario(@ModelAttribute("user") com.damian.objetivos.entity.User usuario) {
		usuario = userService.addOrUpdate(usuario);
		LoggerMapper.log(Level.INFO, "actualizarUsuario", usuario, getClass());
		return "redirect:/entrada/listaEntradas";
	}
	
	private void cargarUsuario(ModelAndView modelAndView) {		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.damian.objetivos.entity.User usuario = userRepository.findByUsername(user.getUsername());
		modelAndView.addObject("username", usuario.getName());
		
	}

}
