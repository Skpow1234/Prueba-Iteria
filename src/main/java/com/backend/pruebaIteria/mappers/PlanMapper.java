package com.backend.pruebaIteria.mappers;

import com.backend.pruebaIteria.DTO.PlanDTO;
import com.backend.pruebaIteria.model.Plan;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlanMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public PlanMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public PlanDTO entityToDTO(Plan plan) {
        return modelMapper.map(plan, PlanDTO.class);
    }

    public Plan dtoToEntity(PlanDTO planDTO) {
        return modelMapper.map(planDTO, Plan.class);
    }
}
