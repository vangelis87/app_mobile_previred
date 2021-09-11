package com.previred.app_mobile_previred.util;


import java.security.Key;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

/**
 * Maneja JWT
 * @author Rafael Ortiz
 */
@Component
public class JwtTokenProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${jwt.key}")
    private String secretKey;

    public Claims obtenerClaims(String token) {
        return Jwts.parser()
                .setSigningKey(obtenerSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Obtiene rut usuario a partir del JWT
     * @param token
     * @return
     */
    public Integer obtenerRutUsuario(String token) {
        LOGGER.info("Obteniendo rut usuario desde token");
        return Integer.parseInt(obtenerClaimToken(token, Claims::getSubject));
    }

    public <T> T obtenerClaimToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = obtenerClaims(token);
        return claimsResolver.apply(claims);
    }

    private Key obtenerSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}