package com.previred.app_mobile_previred.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.previred.app_mobile_previred.model.DocumentoDTO;

@Repository
public interface DocumentoRepository extends JpaRepository<DocumentoDTO, String> {
	public Optional<DocumentoDTO> findByCodigo(String codigo);
}
