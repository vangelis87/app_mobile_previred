package com.previred.app_mobile_previred.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class Mensaje {
	@JsonAlias(value = "to")
	private String to;
	@JsonAlias(value = "notification")
	private Notificacion notification;
	@JsonAlias(value = "data")
	private String data;

}
