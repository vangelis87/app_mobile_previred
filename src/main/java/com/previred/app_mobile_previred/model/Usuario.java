package com.previred.app_mobile_previred.model;

import java.util.List;

import lombok.Data;


@Data
public class Usuario extends LoginRespuesta {
	private int rut;
	private String dv;
	private String email;
	private String nombres;
	private String apellidoPaterno;
	private String apellidoMaterno;
	private List<Roles> roles;
}

