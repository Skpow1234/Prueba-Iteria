package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.TipoDocumentoDTO;
import com.backend.pruebaIteria.model.TipoDocumento;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TipoDocumentoMapper {

    public static TipoDocumentoDTO entityToDTO(TipoDocumento tipoDocumento) {
        return TipoDocumentoDTO.builder()
                .id(tipoDocumento.getId())
                .nombre(tipoDocumento.getNombre())
                .estado(tipoDocumento.getEstado())
                .build();
    }

    public static List<TipoDocumentoDTO> entityToDTOList(List<TipoDocumento> tiposDocumento) {
        return tiposDocumento.stream().map(TipoDocumentoMapper::entityToDTO).collect(Collectors.toList());
    }

    public static TipoDocumento dtoToEntity(TipoDocumentoDTO tipoDocumentoDTO) {
        return TipoDocumento.builder()
                .id(tipoDocumentoDTO.getId())
                .nombre(tipoDocumentoDTO.getNombre())
                .estado(tipoDocumentoDTO.getEstado())
                .build();
    }

    public static List<TipoDocumento> dtoToEntityList(List<TipoDocumentoDTO> tiposDocumentoDTO) {
        return tiposDocumentoDTO.stream().map(TipoDocumentoMapper::dtoToEntity).collect(Collectors.toList());
    }
}


