package com.example.demo.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChatController {	
	
	@GetMapping("/guardarMensaje")
	public String mostrarFormulario(Model model) {	    	    	   	   
	    return "nombre_de_tu_vista"; 
	}

}