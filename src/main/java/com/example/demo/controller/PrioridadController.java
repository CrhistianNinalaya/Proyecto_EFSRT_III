package com.example.demo.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.PrioridadEntity;
import com.example.demo.repository.PrioridadRepository;

@Controller
public class PrioridadController {
	
	@Autowired
	private PrioridadRepository prioridadRepository;
	
	@GetMapping("/chat")
	public String showPrioridad(Model model) {
		List<PrioridadEntity> lstPrioridad = prioridadRepository.findAll();
		model.addAttribute("lstPrioridad",lstPrioridad);
		return "chat";
	}

}
