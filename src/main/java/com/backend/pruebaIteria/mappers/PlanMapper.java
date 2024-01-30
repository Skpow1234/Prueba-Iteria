package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.PlanDTO;
import com.backend.pruebaIteria.model.Plan;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlanMapper {

    public static PlanDTO entityToDTO(Plan plan) {
        return PlanDTO.builder()
                .id(plan.getId())
                .nombre(plan.getNombre())
                .fechaInicio(plan.getFechaInicio())
                .fechaFin(plan.getFechaFin())
                .estado(plan.getEstado())
                .build();
    }

    public static List<PlanDTO> entityToDTOList(List<Plan> planes) {
        return planes.stream().map(PlanMapper::entityToDTO).collect(Collectors.toList());
    }

    public static Plan dtoToEntity(PlanDTO planDTO) {
        return Plan.builder()
                .id(planDTO.getId())
                .nombre(planDTO.getNombre())
                .fechaInicio(planDTO.getFechaInicio())
                .fechaFin(planDTO.getFechaFin())
                .estado(planDTO.getEstado())
                .build();
    }

    public static List<Plan> dtoToEntityList(List<PlanDTO> planesDTO) {
        return planesDTO.stream().map(PlanMapper::dtoToEntity).collect(Collectors.toList());
    }
}

