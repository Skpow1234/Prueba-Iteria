package com.backend.pruebaIteria.services;

import com.backend.pruebaIteria.DTO.AfiliadoDTO;

public interface AfiliadoService {

    AfiliadoDTO getAfiliadoById(String id);
    AfiliadoDTO createAfiliado(AfiliadoDTO afiliadoDTO);
    AfiliadoDTO updateAfiliado(AfiliadoDTO afiliadoDTO);
    void deleteAfiliado(String id);
}

