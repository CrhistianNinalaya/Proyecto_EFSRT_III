package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
		model.addAttribute("mensaje", new MensajeEntity());

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

		    if (!mensajesPorFiltro.isEmpty()) {
		        int ULTIMO = mensajesPorFiltro.size() - 1;
		        MensajeEntity mensajePrimero = mensajesPorFiltro.get(ULTIMO);

		        System.out.println("********************************");
		        System.out.println("Desde el get: " + mensajesPorFiltro.get(0).toString());
		        System.out.println("Desde el get: " + mensajesPorFiltro.get(0).getChat().getCodChat());

		        model.addAttribute("mensaje", mensajePrimero);
		        
		        System.out.println("DESDE EL GET***************************");
		        System.out.println(mensajePrimero.toString());
		    } else {
		        // Si la lista está vacía, crea un mensaje vacío
		        Optional<ChatEntity> chatBuscadoPorId = chatRepository.findById(codChat);
		        MensajeEntity nuevoMensaje = new MensajeEntity();
		        
		        
		        nuevoMensaje.setChat(chatBuscadoPorId.orElse(null)); // Asigna el chat encontrado o null
		        CuentaEntity usuario = (CuentaEntity)session.getAttribute("usuario");
		        nuevoMensaje.setCuenta(usuario);
		        model.addAttribute("mensaje", nuevoMensaje);
		        System.out.println("DESDE EL GET***************************");
		        System.out.println(nuevoMensaje.toString());
		    }

		}

		
		if (codPrioridad != null) {
			
			List<ChatEntity> lstChat = chatRepository.findAllByPrioridad_CodPrioridad(codPrioridad);
			System.out.println("Desde el momento en que codPrioridad no es NULL "+lstChat.get(0).getPrioridad().getCodPrioridad());
			model.addAttribute("lstChat", lstChat);
		}

		
		CuentaEntity usuario = (CuentaEntity) session.getAttribute("usuario");
//		System.out.println("DEsde el get");
//		System.out.println(usuario.toString());
//		

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

	
	@PostMapping("/registrar_mensaje")
    public String registrarMensaje (Model model, HttpSession session, @ModelAttribute("mensaje") MensajeEntity mensaje,
    		@RequestParam("contenido")String contenido){
					

		System.out.println("CODMENSAJE:"+mensaje.getChat().getCodChat());
		
		
		
		int codChat = mensaje.getChat().getCodChat();
		mensaje.setFecMensaje(LocalDateTime.now());
		if(mensaje.getCodMensaje() == null) {
			mensaje.setCodMensaje(1);
		} else {		
			mensaje.setCodMensaje(mensaje.getCodMensaje()+1);
		}
		mensaje.setContenido(contenido);
		
		System.out.println("*******************************************");
		System.out.println("DESDE EL POST");
		System.out.println(mensaje.toString());
		
		System.out.println("*******************************************");
		
        CuentaEntity cuentaEncontrada = (CuentaEntity) session.getAttribute("usuario");
        
        System.out.println(mensaje.toString());
        if(cuentaEncontrada !=null) {
        	System.out.println("DEsde el post");
        	System.out.println(cuentaEncontrada.toString());
        	System.out.println("*******************************************");        
        }
//        MensajeEntity nuevomensaje = new MensajeEntity();                    
//        
        mensaje.setCuenta(cuentaEncontrada);
                                               
        mensajeRepository.save(mensaje);
        
		return "redirect:/chat?codChat=" + codChat;
    }
}
