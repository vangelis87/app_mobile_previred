package com.previred.app_mobile_previred.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class LoginRespuesta {
	@JsonAlias(value = "access_type")
	private String tokenType;
	@JsonAlias(value = "access_token")
	private String accessToken;
	@JsonAlias(value = "expires_in")
	private int expiresIn;
	@JsonAlias(value = "refresh_token")
	private String refreshToken;
}
