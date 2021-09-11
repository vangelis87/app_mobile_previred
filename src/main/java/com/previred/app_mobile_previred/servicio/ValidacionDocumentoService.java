package com.previred.app_mobile_previred.servicio;

import java.util.Optional;

import com.previred.app_mobile_previred.model.DocumentoDTO;


public interface ValidacionDocumentoService {
	public boolean guardar(DocumentoDTO documento);
	public Optional<DocumentoDTO> obtenerPorCodigo(String coddigo);
}
