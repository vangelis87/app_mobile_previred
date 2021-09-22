package com.previred.app_mobile_previred.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Notificacion {
	@JsonAlias(value = "title")
	private String title ;
	@JsonAlias(value = "body")
	private String body;
}
