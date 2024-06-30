package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showChat(Model model, HttpSession session,
			// Hace opcional este parametro
			@RequestParam(value = "codChat", required = false, defaultValue = "0") Integer codChat) {
		List<PrioridadEntity> lstPrioridad = prioridadRepository.findAll();
		model.addAttribute("lstPrioridad", lstPrioridad);
		model.addAttribute("chatEntity", new ChatEntity());

		Integer codPrioridad = (Integer) session.getAttribute("codPrioridad");
		System.out.println("Desde get showChat " + codPrioridad);

		if (codPrioridad == null) {
			List<ChatEntity> lstChat = chatRepository.findAll();
			model.addAttribute("lstChat", lstChat);
			System.out.println(lstChat.get(0).getPrioridad().getNombre());
		}

//		List<MensajeEntity> lstMensajes= mensajeRepository.findAll();
//		
//		System.out.println(lstMensajes.get(0).getChat().getCodChat());

		if (codChat != 0) {
			List<MensajeEntity> mensajesPorFiltro = mensajeRepository.findAllByChat_CodChat(codChat);
			model.addAttribute("lstMensajes", mensajesPorFiltro);			
			model.addAttribute("codChat", codChat);
		}
		
		if (codPrioridad != null) {
			
			List<ChatEntity> lstChat = chatRepository.findAllByPrioridad_CodPrioridad(codPrioridad);
			System.out.println("Desde el momento en que codPrioridad no es NULL "+lstChat.get(0).getPrioridad().getCodPrioridad());
			model.addAttribute("lstChat", lstChat);
		}

		CuentaEntity usuario = (CuentaEntity) session.getAttribute("usuario");

		if (usuario != null) {
			model.addAttribute("sessionUsuario", usuario);
			model.addAttribute("sessionCodUsu", usuario.getCodUsuario());

			System.out.println(usuario.getTipo().getCodTipo());
		}

		return "views/chat";
	}

	@PostMapping("/filtrar_chat")
	public String filtrarChat(@ModelAttribute("chatEntity") ChatEntity chatEntity, HttpSession session) {
		int prioridadSeleccionada = chatEntity.getPrioridad().getCodPrioridad();
		session.setAttribute("codPrioridad", prioridadSeleccionada);

		return "redirect:/chat";
	}

}
