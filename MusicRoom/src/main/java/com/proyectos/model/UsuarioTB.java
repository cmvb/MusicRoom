package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

//@XmlRootElement
@Entity
@Table(name = "MRA_USUARIO_TB")
public class UsuarioTB implements Serializable {

	private static final long serialVersionUID = -5034711187700250581L;

	@Id
	@NotNull
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usu_id_usuario", nullable = false, length = 10)
	private long idUsuario;

	@NotNull
	@Column(name = "usu_usuario", nullable = false, length = 20)
	private String usuario;

	@NotNull
	@Column(name = "usu_nombre", nullable = false, length = 50)
	private String nombre;

	@NotNull
	@Column(name = "usu_apellido", nullable = false, length = 50)
	private String apellido;

	@NotNull
	@Column(name = "usu_numero_documento", nullable = false, length = 50)
	private String numeroDocumento;

	public long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

}
