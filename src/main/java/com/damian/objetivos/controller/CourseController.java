package com.damian.objetivos.controller;

import java.util.List;

import org.apache.logging.log4j.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.damian.objetivos.model.CourseModel;
import com.damian.objetivos.service.CourseService;
import com.damian.objetivos.util.LoggerMapper;

@Controller
@RequestMapping("/course")
public class CourseController {

	public static final String CARPETA_INTERNA = "carpetaInterna/";
	public static final String COURSES_VIEW = "courses";
	
	@Autowired
	private CourseService courseService;
	
	@GetMapping("/listCourses")
	public ModelAndView listAllCourse() {
		ModelAndView modelAndView = new ModelAndView(CARPETA_INTERNA + COURSES_VIEW);
		List<CourseModel> courses = courseService.listAllCourses();
		modelAndView.addObject("courses", courses);
		modelAndView.addObject("course", new CourseModel());
		LoggerMapper.log(Level.INFO, "listAllCourse", courses, getClass());
		return modelAndView;
	}
	
	@PostMapping("/addCourse")
	public String addCourse(@ModelAttribute("course") CourseModel course) {
		CourseModel courseExit = courseService.addCourse(course);
		LoggerMapper.log(Level.INFO, "addCourse", courseExit, getClass());
		return "redirect:/course/listCourses";
	}

}
