package com.previred.app_mobile_previred.model;

import java.util.List;

import lombok.Data;

@Data	
public class Perfil {
	private int codPerfil;
	private String descripcion;
	private String valVarchar1;
	private List<Funcionalidad> funcionalidades;
}
