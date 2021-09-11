package com.previred.app_mobile_previred.servicio.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.previred.app_mobile_previred.model.DocumentoDTO;
import com.previred.app_mobile_previred.repository.DocumentoRepository;
import com.previred.app_mobile_previred.servicio.ValidacionDocumentoService;

@Service
public class ValidacionDocumentoServiceImpl implements ValidacionDocumentoService {
	
	@Autowired
	private DocumentoRepository documentoRepository;
	
	@Override
	public boolean guardar(DocumentoDTO documento) {
		try {
			documentoRepository.save(documento);
			return true;
		}catch(Exception ex) {
			return false;
		}
	}

	@Override
	public Optional<DocumentoDTO> obtenerPorCodigo(String codigo) {
		return documentoRepository.findByCodigo(codigo);
	}

}
