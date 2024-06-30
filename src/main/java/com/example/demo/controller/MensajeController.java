package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.CategoriaEntity;
import com.example.demo.entity.ChatEntity;
import com.example.demo.entity.CuentaEntity;
import com.example.demo.entity.MensajeEntity;
import com.example.demo.entity.PrioridadEntity;
import com.example.demo.repository.CategoriaRepository;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.MensajeRepository;
import com.example.demo.repository.PrioridadRepository;

@Controller
public class MensajeController {
	
	@Autowired
	private PrioridadRepository prioridadRepository;
	
	@Autowired
	private ChatRepository chatRepository;
	
	@Autowired
	private MensajeRepository mensajeRepository;
	
	@GetMapping("/chat")
	public String showChat(Model model, HttpSession session) {
		List<PrioridadEntity> lstPrioridad = prioridadRepository.findAll();
		model.addAttribute("lstPrioridad",lstPrioridad);
		
		List<ChatEntity> lstChat = chatRepository.findAll();
		model.addAttribute("lstChat",lstChat);
		
		List<MensajeEntity> lstMensajes = mensajeRepository.findAll();
		model.addAttribute("lstMensajes",lstMensajes);
			
		
		CuentaEntity usuario = (CuentaEntity)session.getAttribute("usuario");
		
		if(usuario != null) {
			model.addAttribute("sessionUsuario",usuario);
			model.addAttribute("sessionCodUsu",usuario.getCodUsuario());
			
			
			System.out.println(usuario.getTipo().getCodTipo());
		}		
		
		System.out.println(lstMensajes.get(0).getCuenta().getNombre());
		System.out.println(lstMensajes.get(0).getContenido());
		
		System.out.println(lstMensajes.get(1).getCuenta().getNombre());
		System.out.println(lstMensajes.get(1).getContenido());
		return "views/chat";
	}
	
	@PostMapping
	public String registrarMensaje (Model model) {
		
		model.addAttribute("mensaje", new MensajeEntity());
		
		return "";
	}

}
