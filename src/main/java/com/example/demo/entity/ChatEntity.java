package com.example.demo.entity;
import java.io.Serializable;
import java.time.LocalDate;

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
@Table(name = "tb_chat")
public class ChatEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cod_chat", nullable = false, unique = true)
	private Integer codChat;

	@Column(name = "fec_inicio", nullable = false, columnDefinition = "DATE")
	private LocalDate fecInicio;

	@Column(name = "fec_cierre", nullable = false, columnDefinition = "DATE")
	private LocalDate fecCierre;

	@Column(name = "estado", nullable = false)
	private boolean estado;

	@ManyToOne
	@JoinColumn(name = "cod_usuario", nullable = false)
	private CuentaEntity cuenta;

	@ManyToOne
	@JoinColumn(name = "cod_cat", nullable = false)
	private CategoriaEntity categoria;

	@ManyToOne
	@JoinColumn(name = "cod_prioridad", nullable = false)
	private PrioridadEntity prioridad;

}
