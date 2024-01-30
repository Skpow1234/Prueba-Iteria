package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.TipoDocumentoDTO;
import com.backend.pruebaIteria.model.TipoDocumento;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TipoDocumentoMapper {

    private final ModelMapper modelMapper;

    public TipoDocumentoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public TipoDocumentoDTO entityToDTO(TipoDocumento tipoDocumento) {
        return modelMapper.map(tipoDocumento, TipoDocumentoDTO.class);
    }

    public TipoDocumento dtoToEntity(TipoDocumentoDTO tipoDocumentoDTO) {
        return modelMapper.map(tipoDocumentoDTO, TipoDocumento.class);
    }
}

