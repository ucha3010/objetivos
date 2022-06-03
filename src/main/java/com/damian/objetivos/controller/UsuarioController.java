package com.damian.objetivos.controller;

import com.damian.objetivos.model.ClaveUsuarioModel;
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
	public ModelAndView formularioUsuario(ModelAndView modelAndView) {
		modelAndView.setViewName("formularioUsuario");
		cargarUsuarioCompleto(modelAndView);
		modelAndView.addObject("categorias", categoriaService.listAll());
		LoggerMapper.log(Level.INFO, "formularioUsuario", modelAndView, getClass());
		return modelAndView;
	}
	
	@PostMapping("/actualizarUsuario")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView actualizarUsuario(@ModelAttribute("user") com.damian.objetivos.entity.User usuario) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			usuario = userService.addOrUpdate(usuario);
			modelAndView.addObject("actualizacionCorrecta", "actualizacionCorrecta");
		} catch (Exception e) {
			modelAndView.addObject("actualizacionError", "actualizacionError");
			LoggerMapper.log(Level.ERROR, "actualizarUsuario", e.getMessage(), getClass());
		}
		LoggerMapper.log(Level.INFO, "actualizarUsuario", usuario, getClass());
		return formularioUsuario(modelAndView);
	}

	@GetMapping("/formularioCambioClave")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ModelAndView formularioCambioClave() {
		ModelAndView modelAndView = new ModelAndView("formularioCambioClave");
		com.damian.objetivos.entity.User usuario = cargarUsuarioCompleto(modelAndView);
		modelAndView.addObject("categorias", categoriaService.listAll());
		ClaveUsuarioModel claveUsuarioModel = new ClaveUsuarioModel();
		claveUsuarioModel.setUsername(usuario.getUsername());
		modelAndView.addObject("claveUsuarioModel", claveUsuarioModel);
		LoggerMapper.log(Level.INFO, "formularioCambioClave", modelAndView, getClass());
		return modelAndView;
	}

	@PostMapping("/actualizarClaveUsuario")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
	public ModelAndView actualizarClaveUsuario(@ModelAttribute("claveUsuarioModel") ClaveUsuarioModel claveUsuarioModel ) {
		ModelAndView modelAndView = new ModelAndView("formularioCambioClave");
		com.damian.objetivos.entity.User usuario = userRepository.findByUsername(claveUsuarioModel.getUsername());
		if (userService.comparePassword(claveUsuarioModel.getAntiguaClave(), usuario.getPassword())) {
			usuario.setPassword(userService.generatePassword(claveUsuarioModel.getNuevaClave()));
			usuario = userService.addOrUpdate(usuario);
			modelAndView.addObject("claveModificada", "claveModificada");
			LoggerMapper.log(Level.INFO, "actualizarUsuario", "Contraseña actualizada", getClass());
		} else {
			modelAndView.addObject("antiguaDistinta", "antiguaDistinta");
			LoggerMapper.log(Level.INFO, "actualizarUsuario", "Contraseña antigua distinta", getClass());
		}
		modelAndView.addObject("usuario", usuario);
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("claveUsuarioModel", claveUsuarioModel);
		LoggerMapper.log(Level.INFO, "actualizarUsuario", usuario, getClass());
		return modelAndView;
	}
	
	private void cargarUsuario(ModelAndView modelAndView) {		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.damian.objetivos.entity.User usuario = userRepository.findByUsername(user.getUsername());
		modelAndView.addObject("username", usuario.getName());
		
	}

	private com.damian.objetivos.entity.User cargarUsuarioCompleto(ModelAndView modelAndView) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.damian.objetivos.entity.User usuario = userRepository.findByUsername(user.getUsername());
		modelAndView.addObject("usuario", usuario);
		return usuario;
	}

}
