package com.previred.app_mobile_previred.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Documento")
public class DocumentoDTO {
	@Id
	public String codigo;
	public String jsonDatos;
	public Date fechaExpiracion;
	public int tipoDocumento;
	
	@Override
	public String toString() {
		return "Documento [codigo=" + codigo + ", jsonDatos=" + jsonDatos + ", fechaExpiracion=" + fechaExpiracion
				+ ", tipoDocumento=" + tipoDocumento + ", toString()=" + super.toString() + "]";
	}
	
	
}
