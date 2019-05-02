package com.proyectos.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Usuario")
@Entity
@Table(name = "MRA_USUARIO_TB")
public class UsuarioTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = -5034711187700250581L;

	@Id
	@Column(name = "usu_id_usuario", nullable = false, length = 10)
	private long idUsuario;

	@NotNull
	@Column(name = "usu_usuario", nullable = false, length = 10)
	private String usuario;

	@NotNull
	@Column(name = "usu_password", nullable = false, length = 200)
	private String password;

	@NotNull
	@Column(name = "usu_nombre", nullable = false, length = 50)
	private String nombre;

	@NotNull
	@Column(name = "usu_apellido", nullable = false, length = 50)
	private String apellido;

	@NotNull
	@Column(name = "usu_numero_documento", nullable = false, length = 50)
	private String numeroDocumento;

	@NotNull
	@Column(name = "usu_tipo_documento", nullable = false, length = 10)
	private short tipoDocumento;

	@NotNull
	@Column(name = "usu_tipo_usuario", nullable = false, length = 10)
	private short tipoUsuario;

	@NotNull
	@Column(name = "usu_email", nullable = false, length = 50)
	private String email;

	@NotNull
	@Column(name = "usu_fecha_nacimiento", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaNacimiento;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public short getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(short tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public short getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(short tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

}
