package com.damian.objetivos.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorsController {
	
	public static final String ISE_VIEW = "error/500";
	
	//@ExceptionHandler(Exception.class)
	public String showInternalServerError() {
		return ISE_VIEW;
	}

}
