package com.backend.pruebaIteria.services;

import com.backend.pruebaIteria.DTO.TipoDocumentoDTO;

public interface TipoDocumentoService {
    TipoDocumentoDTO getTipoDocumentoById(String id);
    TipoDocumentoDTO getTipoDocumentoByNombre(String nombre);
}
