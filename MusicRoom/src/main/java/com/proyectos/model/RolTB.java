package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Rol")
@Entity
@Table(name = "MRA_ROL_TB")
public class RolTB extends BaseEntidadTB implements Serializable {
	private static final long serialVersionUID = 1923563449420236769L;

	@Id
	@Column(name = "rol_id_rol", nullable = false, length = 10)
	private long idRol;

	@NotNull
	@Column(name = "rol_codigo", nullable = false, length = 10)
	private String codigo;

	@NotNull
	@Column(name = "rol_descripcion", nullable = false, length = 100)
	private String descripcion;

	@NotNull
	@Column(name = "rol_path", nullable = false, length = 100)
	private String path;

	@NotNull
	@Column(name = "rol_sub_path", nullable = false, length = 100)
	private String subPath;

	public long getIdRol() {
		return idRol;
	}

	public void setIdRol(long idRol) {
		this.idRol = idRol;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSubPath() {
		return subPath;
	}

	public void setSubPath(String subPath) {
		this.subPath = subPath;
	}

}
