package com.damian.objetivos.controller;

import com.damian.objetivos.entity.UserRole;
import com.damian.objetivos.model.ClaveUsuarioModel;
import com.damian.objetivos.model.UserRoleModel;
import com.damian.objetivos.repository.UserRoleRepository;
import com.damian.objetivos.service.UserRoleService;
import com.damian.objetivos.util.ViewConstant;
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

import com.damian.objetivos.service.CategoriaService;
import com.damian.objetivos.service.impl.UserService;
import com.damian.objetivos.util.LoggerMapper;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private CategoriaService categoriaService;
	
	@GetMapping("/listaUsuarios")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView listaUsuarios(ModelAndView modelAndView) {
		modelAndView.setViewName("usuarios");
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("usuarios", userService.findAll());
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
		return formularioUsuario(addOrUpdateUsuario(usuario,"actualizarUsuario"));
	}

	@GetMapping("/formularioCambioClave")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
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
		com.damian.objetivos.entity.User usuario = userService.findByUsername(claveUsuarioModel.getUsername());
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

	@GetMapping("/eliminarUsuario")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView eliminarUsuario(@ModelAttribute("username") String username) {
		ModelAndView modelAndView = new ModelAndView();
		if (userService.delete(username)) {
			modelAndView.addObject("eliminacionCorrecta","actualizacionCorrecta");
		} else {
			modelAndView.addObject("eliminacionError","eliminacionError");
		}
		LoggerMapper.log(Level.INFO, "eliminarUsuario", modelAndView, getClass());
		return listaUsuarios(modelAndView);
	}

	@GetMapping("/formularioUsuarioAltaModif")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView formularioUsuarioAltaModif(ModelAndView modelAndView, @ModelAttribute("username") String username) {
		modelAndView.setViewName("formularioUsuarioAltaModif");
		cargarUsuarioCompleto(modelAndView);
		com.damian.objetivos.entity.User usuarioModif = new com.damian.objetivos.entity.User();
		if(!StringUtils.isEmpty(username)) {
			usuarioModif = userService.findByUsername(username);
			usuarioModif.setUserRole(userRoleService.findRolesByUser(usuarioModif));
		}
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("usuarioModif", usuarioModif);
		LoggerMapper.log(Level.INFO, "formularioUsuarioAltaModif", modelAndView, getClass());
		return modelAndView;
	}

	@PostMapping("/altaModifUsuario")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView altaModifUsuario(@ModelAttribute("user") com.damian.objetivos.entity.User usuario) {
		boolean usuarioNuevo = false;
		boolean usernameDuplicado = false;
		UserRole userRole = new UserRole();
		ModelAndView modelAndView = new ModelAndView();
		if(usuario.getPassword().isEmpty()) {
			if(userService.findByUsername(usuario.getUsername()) == null) {
				usuario.setPassword(userService.generatePassword("usuario123"));
				usuarioNuevo = true;
				userRole.setUser(usuario);
				userRole.setRole("ROLE_USER");
			} else {
				usernameDuplicado = true;
				modelAndView.addObject("usernameDuplicado", "usernameDuplicado");
				modelAndView.addObject("usuarioModif", usuario);
				modelAndView.setViewName("formularioUsuarioAltaModif");
				modelAndView.addObject("categorias", categoriaService.listAll());
				cargarUsuarioCompleto(modelAndView);
			}
		}
		if(!usernameDuplicado) {
			modelAndView = addOrUpdateUsuario(usuario, "altaModifUsuario");
			if (usuarioNuevo) {
				userRoleService.save(userRole);
			}
			modelAndView = listaUsuarios(modelAndView);
		}
		return modelAndView;
	}


	@GetMapping("/formularioUsuarioRoles")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView formularioUsuarioRoles(ModelAndView modelAndView, @ModelAttribute("username") String username) {
		modelAndView.setViewName("formularioUsuarioRoles");
		cargarUsuarioCompleto(modelAndView);
		modelAndView.addObject("categorias", categoriaService.listAll());
		modelAndView.addObject("rolesExistentes", ViewConstant.ROLES.split(","));
		modelAndView.addObject("userRoleModel", userRoleService.findByUser(userService.findByUsername(username)));
		LoggerMapper.log(Level.INFO, "formularioUsuarioRoles", modelAndView, getClass());
		return modelAndView;
	}

	@PostMapping("/modifUsuarioRoles")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ModelAndView modifUsuarioRoles(@ModelAttribute("userRoleModel")UserRoleModel userRoleModel) {
		ModelAndView modelAndView = new ModelAndView();
		userRoleService.actualizarRoles(userRoleModel);
		modelAndView.addObject("rolesModificados", "rolesModificados");
		LoggerMapper.log(Level.INFO, "modifUsuarioRoles", modelAndView, getClass());
		return listaUsuarios(modelAndView);
	}

	private ModelAndView addOrUpdateUsuario(com.damian.objetivos.entity.User usuario, String metodo) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			usuario = userService.addOrUpdate(usuario);
			modelAndView.addObject("actualizacionCorrecta", "actualizacionCorrecta");
		} catch (Exception e) {
			modelAndView.addObject("actualizacionError", "actualizacionError");
			LoggerMapper.log(Level.ERROR, metodo, e.getMessage(), getClass());
		}
		LoggerMapper.log(Level.INFO, metodo, usuario, getClass());
		return modelAndView;
	}
	
	private void cargarUsuario(ModelAndView modelAndView) {		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.damian.objetivos.entity.User usuario = userService.findByUsername(user.getUsername());
		modelAndView.addObject("username", usuario.getName());
		
	}

	private com.damian.objetivos.entity.User cargarUsuarioCompleto(ModelAndView modelAndView) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		com.damian.objetivos.entity.User usuario = userService.findByUsername(user.getUsername());
		modelAndView.addObject("usuario", usuario);
		return usuario;
	}

}
