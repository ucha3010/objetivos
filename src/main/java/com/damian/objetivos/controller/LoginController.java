package com.damian.objetivos.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.damian.objetivos.util.ViewConstant;

@Controller
@RequestMapping("/")
public class LoginController {
	
	@GetMapping("/login")
	@PreAuthorize("permitAll()")
	public String showLoginForm(Model model,
			@RequestParam(name = "error", required = false) String error,
			@RequestParam(name = "logout", required = false) String logout) {
		model.addAttribute("error", error);
		model.addAttribute("logout", logout);
		return ViewConstant.LOGIN;
	}
	
	@GetMapping("/loginsuccess")
	public String loginCheck() {
		return "redirect:/entrada/listaEntradas";
	}

}
