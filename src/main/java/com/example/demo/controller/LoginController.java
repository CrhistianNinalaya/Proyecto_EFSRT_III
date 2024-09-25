package com.example.demo.controller;



import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.CuentaEntity;
import com.example.demo.service.CuentaService;


@Controller
public class LoginController {

	@Autowired
	private CuentaService cuentaService;
	
	@GetMapping("/")
	public String index(Model model, HttpSession session) {
//		if(session.getAttribute("cuenta")== null) {
//			model.addAttribute("usuario", new CuentaEntity());
//		}
		model.addAttribute("usuario",new CuentaEntity());
		
		return "views/login";
	}
	
	@PostMapping("/login")
	public String login(CuentaEntity cuentaEntity, Model model, HttpSession session) {
		
		boolean usuarioValido = cuentaService.validarUsuario(cuentaEntity, session);
		if(usuarioValido) {
			CuentaEntity cuentaLogeada = cuentaService.buscarUsuarioPorCorreo(cuentaEntity.getCorreo());
			session.setAttribute("usuario", cuentaLogeada);
			System.out.println("hicieron match");
			return "redirect:/chat";
		}
		model.addAttribute("loginInvalido","No existe ese usuario");
		System.out.println("No hicieron match");
		model.addAttribute("usuario",new CuentaEntity());
		return "views/login";
	}
	
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
