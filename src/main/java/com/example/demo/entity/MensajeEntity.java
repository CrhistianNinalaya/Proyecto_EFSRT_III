package com.example.demo.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tb_mensaje")
public class MensajeEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_mensaje", nullable = false, unique = true)
	private Integer codMensaje;
	
	
	@Column(name="contenido", nullable = false)
	private String contenido;
	
	@Column(name="fec_mensaje", nullable = false, columnDefinition = "DATETIME")
	private LocalDateTime fecMensaje;
	
	@ManyToOne
	@JoinColumn(name="cod_chat", nullable= false)
	private ChatEntity chat;
	
	@ManyToOne
	@JoinColumn(name = "cod_usuario", nullable = false)
	private CuentaEntity cuenta;
	

}
