package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.AfiliadoDTO;
import com.backend.pruebaIteria.model.Afiliado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AfiliadoMapper {
    private final ModelMapper modelMapper;

    @Autowired
    private TipoDocumentoMapper tipoDocumentoMapper;

    @Autowired
    public AfiliadoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public AfiliadoDTO entityToDTO(Afiliado afiliado) {
        return modelMapper.map(afiliado, AfiliadoDTO.class);
    }

    public Afiliado dtoToEntity(AfiliadoDTO afiliadoDTO) {
        Afiliado afiliado = modelMapper.map(afiliadoDTO, Afiliado.class);
        afiliado.setTipoDocumento(tipoDocumentoMapper.dtoToEntity(afiliadoDTO.getTipoDocumento()));
        return afiliado;
    }
}



