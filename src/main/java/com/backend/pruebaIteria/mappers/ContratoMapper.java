package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.ContratoDTO;
import com.backend.pruebaIteria.model.Contrato;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContratoMapper {
    private final ModelMapper modelMapper;

    @Autowired
    private AfiliadoMapper afiliadoMapper;

    @Autowired
    private PlanMapper planMapper;

    public ContratoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ContratoDTO entityToDTO(Contrato contrato) {
        return modelMapper.map(contrato, ContratoDTO.class);
    }

    public Contrato dtoToEntity(ContratoDTO contratoDTO) {
        Contrato contrato = modelMapper.map(contratoDTO, Contrato.class);
        contrato.setAfiliado(afiliadoMapper.dtoToEntity(contratoDTO.getAfiliado()));
        contrato.setPlan(planMapper.dtoToEntity(contratoDTO.getPlan()));
        return contrato;
    }
}

