package com.example.demo.entity;
import java.io.Serializable;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="tb_cuenta")
public class CuentaEntity implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="cod_usuario", nullable = false)
	private Integer codUsuario;
	
	@Column(name="nom_usuario",nullable = false, columnDefinition = "VARCHAR(50)" )
	private String nombre;
	
	@Column(name="ape_usuario", nullable = false, columnDefinition = "VARCHAR(50)")
	private String apeUsuario;
	
	@Column(name="password", nullable = false, columnDefinition = "CHAR(6)")
	private String password;
	
	@Column(name="correo", nullable = false, columnDefinition = "VARCHAR(50)",unique = true)
	private String correo;
	
	@ManyToOne
	@JoinColumn(name="cod_tipo", nullable= false)
	private TipoCuentaEntity tipo;

}
