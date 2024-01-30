package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.AfiliadoDTO;
import com.backend.pruebaIteria.model.Afiliado;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AfiliadoMapper {

    public static AfiliadoDTO entityToDTO(Afiliado afiliado) {
        return AfiliadoDTO.builder()
                .id(afiliado.getId())
                .nombre(afiliado.getNombre())
                .apellidos(afiliado.getApellidos())
                .tipoDocumento(TipoDocumentoMapper.entityToDTO(afiliado.getTipoDocumento()))
                .documento(afiliado.getDocumento())
                .telefono(afiliado.getTelefono())
                .direccion(afiliado.getDireccion())
                .email(afiliado.getEmail())
                .estado(afiliado.getEstado())
                // No mapear los contratos aquí para evitar la recursión infinita
                // .contratos(ContratoMapper.entityToDTOList(afiliado.getContratos()))
                .build();
    }

    public static List<AfiliadoDTO> entityToDTOList(List<Afiliado> afiliados) {
        return afiliados.stream().map(AfiliadoMapper::entityToDTO).collect(Collectors.toList());
    }

    public static Afiliado dtoToEntity(AfiliadoDTO afiliadoDTO) {
        return Afiliado.builder()
                .id(afiliadoDTO.getId())
                .nombre(afiliadoDTO.getNombre())
                .apellidos(afiliadoDTO.getApellidos())
                .tipoDocumento(TipoDocumentoMapper.dtoToEntity(afiliadoDTO.getTipoDocumento()))
                .documento(afiliadoDTO.getDocumento())
                .telefono(afiliadoDTO.getTelefono())
                .direccion(afiliadoDTO.getDireccion())
                .email(afiliadoDTO.getEmail())
                .estado(afiliadoDTO.getEstado())
                .contratos(ContratoMapper.dtoToEntityList(afiliadoDTO.getContratos()))
                .build();
    }

    public static List<Afiliado> dtoToEntityList(List<AfiliadoDTO> afiliadosDTO) {
        return afiliadosDTO.stream().map(AfiliadoMapper::dtoToEntity).collect(Collectors.toList());
    }
}





