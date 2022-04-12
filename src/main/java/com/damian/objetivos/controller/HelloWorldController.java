package com.damian.objetivos.controller;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.damian.objetivos.model.Person;
import com.damian.objetivos.service.ExampleService;
import com.damian.objetivos.util.LoggerMapper;

@Controller
@RequestMapping("/")
public class HelloWorldController {
	
	public static final String CARPETA_INTERNA = "carpetaInterna/";
	public static final String HELLO_WORLD = "helloWorld";
	public static final String HELLO_WORLD_2 = "helloWorld2";
	public static final String EXAMPLE2_VIEW = "example2";
	public static final String FORM_VIEW = "formulario";
	public static final String FORM_RESULT = "resultado";
	
	@Autowired
	private ExampleService exampleService;
	
	@GetMapping("/HelloWorld")
	public String helloWorldController(Model model) {
		model.addAttribute("person", new Person("Pepe", 23));
		model.addAttribute("people", exampleService.getListPeople());
		return CARPETA_INTERNA + HELLO_WORLD;
	}
	
	@GetMapping("/HelloWorld2")
	public String helloWorld2Controller() {
		return CARPETA_INTERNA + HELLO_WORLD_2;
	}
	
	@GetMapping("/HelloWorldMaV")
	public ModelAndView helloWorldMaVController() {
		ModelAndView modelAndView = new ModelAndView(CARPETA_INTERNA + HELLO_WORLD);
		modelAndView.addObject("person", new Person("Juan", 30));
		modelAndView.addObject("people", exampleService.getListPeople());
		return modelAndView;
	}
	
	////////////////////////////////////////////////////////////
	// Método get con parámetro de entrada
	@GetMapping("/request1")
	public ModelAndView request1(@RequestParam(name="nm", required=false, defaultValue="quien seas") String name) {
		ModelAndView modelAndView = new ModelAndView(CARPETA_INTERNA + EXAMPLE2_VIEW);
		modelAndView.addObject("nm_in_model", name);
		return modelAndView;
	}
	
	////////////////////////////////////////////////////////
	//Formulario
	
	@GetMapping("/showForm")
	public String showForm(Model model) {
		model.addAttribute("person", new Person());
		return CARPETA_INTERNA + FORM_VIEW;
	}
	
	@PostMapping("/addPerson")
	public ModelAndView addPerson(@ModelAttribute("person") Person person) {
		ModelAndView modelAndView = new ModelAndView(CARPETA_INTERNA + FORM_RESULT);
		modelAndView.addObject("person", person);
		LoggerMapper.log(Level.INFO, "addPerson", person, getClass());
		return modelAndView;
	}

}
