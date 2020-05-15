package com.proyectos.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información de la Factura (Venta o Compra)")
@Entity
@Table(name = "MRA_FACTURA_TB")
public class FacturaTB extends BaseEntidadTB implements Serializable {

	private static final long serialVersionUID = 1776234501672940448L;

	@Id
	@Column(name = "fac_id_factura", nullable = false, length = 10)
	private long idFactura;

	@NotNull
	@OneToOne
	@JoinColumn(name = "fac_tercero", nullable = false, insertable = false)
	private TerceroTB terceroTb;

	@NotNull
	@OneToOne
	@JoinColumn(name = "fac_ensayo", nullable = false, insertable = false)
	private EnsayoTB ensayoTb;

	@NotNull
	@Column(name = "fac_valor_unitario", nullable = false, length = 10)
	private double valorUnitario;

	@NotNull
	@Column(name = "fac_valor_total", nullable = false, length = 10)
	private double valorTotal;

	@NotNull
	@Column(name = "fac_valor_pagado", nullable = false, length = 10)
	private double valorPagado;

	@NotNull
	@Column(name = "fac_valor_restante", nullable = false, length = 10)
	private double valorRestante;

	@NotNull
	@Column(name = "fac_tipo_factura", nullable = false, length = 10)
	private short tipoFactura;

	@NotNull
	@Column(name = "fac_pendiente", nullable = false, length = 10)
	private short pendiente;

	@NotNull
	@OneToMany(mappedBy = "facturaTb", fetch = FetchType.LAZY)
	private List<PagoTB> listaPagoTb;

	public long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	public TerceroTB getTerceroTb() {
		return terceroTb;
	}

	public void setTerceroTb(TerceroTB terceroTb) {
		this.terceroTb = terceroTb;
	}

	public EnsayoTB getEnsayoTb() {
		return ensayoTb;
	}

	public void setEnsayoTb(EnsayoTB ensayoTb) {
		this.ensayoTb = ensayoTb;
	}

	public double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public double getValorPagado() {
		return valorPagado;
	}

	public void setValorPagado(double valorPagado) {
		this.valorPagado = valorPagado;
	}

	public double getValorRestante() {
		return valorRestante;
	}

	public void setValorRestante(double valorRestante) {
		this.valorRestante = valorRestante;
	}

	public short getTipoFactura() {
		return tipoFactura;
	}

	public void setTipoFactura(short tipoFactura) {
		this.tipoFactura = tipoFactura;
	}

	public short getPendiente() {
		return pendiente;
	}

	public void setPendiente(short pendiente) {
		this.pendiente = pendiente;
	}

	public List<PagoTB> getListaPagoTb() {
		return listaPagoTb;
	}

	public void setListaPagoTb(List<PagoTB> listaPagoTb) {
		this.listaPagoTb = listaPagoTb;
	}

}
