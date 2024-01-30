package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.ContratoDTO;
import com.backend.pruebaIteria.model.Contrato;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ContratoMapper {

    public static ContratoDTO entityToDTO(Contrato contrato) {
        return ContratoDTO.builder()
                .id(contrato.getId())
                .afiliado(AfiliadoMapper.entityToDTO(contrato.getAfiliado()))
                .plan(PlanMapper.entityToDTO(contrato.getPlan()))
                .cantidadUsuarios(contrato.getCantidadUsuarios())
                .fechaInicio(contrato.getFechaInicio())
                .fechaRetiro(contrato.getFechaRetiro())
                .fechaRegistro(contrato.getFechaRegistro())
                .eps(contrato.getEps())
                // No mapear el afiliado aquí para evitar la recursión infinita
                // .afiliado(AfiliadoMapper.entityToDTO(contrato.getAfiliado()))
                .build();
    }

    public static List<ContratoDTO> entityToDTOList(List<Contrato> contratos) {
        return contratos.stream().map(ContratoMapper::entityToDTO).collect(Collectors.toList());
    }

    public static Contrato dtoToEntity(ContratoDTO contratoDTO) {
        return Contrato.builder()
                .id(contratoDTO.getId())
                .afiliado(AfiliadoMapper.dtoToEntity(contratoDTO.getAfiliado()))
                .plan(PlanMapper.dtoToEntity(contratoDTO.getPlan()))
                .cantidadUsuarios(contratoDTO.getCantidadUsuarios())
                .fechaInicio(contratoDTO.getFechaInicio())
                .fechaRetiro(contratoDTO.getFechaRetiro())
                .fechaRegistro(contratoDTO.getFechaRegistro())
                .eps(contratoDTO.getEps())
                .build();
    }

    public static List<Contrato> dtoToEntityList(List<ContratoDTO> contratosDTO) {
        if (contratosDTO == null) {
            return Collections.emptyList(); // O devuelve null si lo prefieres
        }
        return contratosDTO.stream().map(ContratoMapper::dtoToEntity).collect(Collectors.toList());
    }
}


