package com.proyectos.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;

//@XmlRootElement
@ApiModel(description = "Información del Pago de la Factura")
@Entity
@Table(name = "MRA_PAGO_TB")
public class PagoTB extends BaseEntidadTB implements Serializable {
	
	private static final long serialVersionUID = -6517963257730568711L;

	@Id
	@Column(name = "pag_id_pago", nullable = false, length = 10)
	private long idPago;

	@NotNull
	@Column(name = "pag_valor_pago", nullable = false, length = 10)
	private double valorPago;

	@NotNull
	@Column(name = "pag_nombre_pago", nullable = false, length = 50)
	private String nombrePago;

	@NotNull
	@Column(name = "pag_documento_pago", nullable = false, length = 20)
	private String documentoPago;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "pag_factura", nullable = false, insertable = false)
	private FacturaTB facturaTb;

	public long getIdPago() {
		return idPago;
	}

	public void setIdPago(long idPago) {
		this.idPago = idPago;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public String getNombrePago() {
		return nombrePago;
	}

	public void setNombrePago(String nombrePago) {
		this.nombrePago = nombrePago;
	}

	public String getDocumentoPago() {
		return documentoPago;
	}

	public void setDocumentoPago(String documentoPago) {
		this.documentoPago = documentoPago;
	}

	public FacturaTB getFacturaTb() {
		return facturaTb;
	}

	public void setFacturaTb(FacturaTB facturaTb) {
		this.facturaTb = facturaTb;
	}

}
