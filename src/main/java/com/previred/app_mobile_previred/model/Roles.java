package com.previred.app_mobile_previred.model;

import java.util.List;

import lombok.Data;

enum RolEnum {
	TCP, TI, TE;
}

@Data
public class Roles {
	private RolEnum rol;
	private List<Perfil> perfiles;
	private String descripcion;
}
