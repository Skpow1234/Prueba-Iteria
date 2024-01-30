package com.backend.pruebaIteria.services;

import com.backend.pruebaIteria.DTO.ContratoDTO;

public interface ContratoService {

    ContratoDTO getContratoById(String id);
    ContratoDTO createContrato(ContratoDTO contratoDTO);
    ContratoDTO updateContrato(ContratoDTO contratoDTO);
    void deleteContrato(String id);
}

