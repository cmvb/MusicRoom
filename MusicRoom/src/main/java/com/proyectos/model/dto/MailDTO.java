package com.proyectos.model.dto;

import java.io.Serializable;
import java.util.Map;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Información DTO del E-MAIL")
public class MailDTO implements Serializable {
	
	private static final long serialVersionUID = -6776972366550263000L;

	private String from;

	private String to;

	private String subject;

	private Map<String, Object> model;

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, Object> getModel() {
		return model;
	}

	public void setModel(Map<String, Object> model) {
		this.model = model;
	}

}
