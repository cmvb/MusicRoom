package com.proyectos.model.dto;

import java.io.Serializable;
import java.util.List;

import com.proyectos.model.BandaTB;
import com.proyectos.model.IntegranteTB;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Información DTO de la Banda y sus Integrantes")
public class BandaIntegranteDTO implements Serializable {

	private static final long serialVersionUID = 7847944348791154513L;

	private BandaTB bandaTb;

	private List<IntegranteTB> listaIntegrantesTb;

	public BandaTB getBandaTb() {
		return bandaTb;
	}

	public void setBandaTb(BandaTB bandaTb) {
		this.bandaTb = bandaTb;
	}

	public List<IntegranteTB> getListaIntegrantesTb() {
		return listaIntegrantesTb;
	}

	public void setListaIntegrantesTb(List<IntegranteTB> listaIntegrantesTb) {
		this.listaIntegrantesTb = listaIntegrantesTb;
	}
}
