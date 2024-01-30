package com.backend.pruebaIteria.services;

import com.backend.pruebaIteria.DTO.PlanDTO;

import java.util.List;

public interface PlanService {
    PlanDTO getPlanById(String id);
    List<PlanDTO> getAllActivePlans();
}
