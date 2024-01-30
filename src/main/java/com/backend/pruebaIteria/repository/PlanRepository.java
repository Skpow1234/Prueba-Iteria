package com.backend.pruebaIteria.repository;

import com.backend.pruebaIteria.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanRepository extends JpaRepository<Plan, String> {

    // Comprobar si existe un plan activo con el mismo nombre
    boolean existsByNombreAndEstado(String nombre, Integer estado);

}
