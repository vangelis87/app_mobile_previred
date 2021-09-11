package com.previred.app_mobile_previred.util;


import java.util.Optional;

/**
 * utilidades para peticiones http
 *
 * @author Rafael Ortiz
 */
public class UtilController {

    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * Clase contiene metodos staticos
     *
     * @author Rafael Ortiz
     */
    private UtilController() {

    }

    /**
     * valida que la cabecera esta bien para recibir la peticion
     *
     * @param acceptHeader de la peticion
     * @return true si la cabecera esta bien
     * @author Rafael Ortiz
     */
    public static boolean acceptEstaOk(Optional<String> acceptHeader) {
        return acceptHeader.isPresent() && acceptHeader.get().contains("application/json");
    }


    /**
     * valida que venga el token con Bearer
     *
     * @param authorizationHeader cabecera
     * @return true si esta bien la cabecera
     */
    public static boolean authorizationEstaOk(Optional<String> authorizationHeader) {
        return authorizationHeader.isPresent() && authorizationHeader.get().startsWith(BEARER_PREFIX);
    }
}