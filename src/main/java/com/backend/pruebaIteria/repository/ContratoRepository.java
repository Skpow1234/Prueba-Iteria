package com.backend.pruebaIteria.repository;

import com.backend.pruebaIteria.model.Afiliado;
import com.backend.pruebaIteria.model.Contrato;
import com.backend.pruebaIteria.model.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, String> {

    // Comprobar si existe un contrato activo con el mismo plan asociado para un afiliado
    boolean existsByAfiliadoAndPlanAndFechaRetiroIsNull(Afiliado afiliado, Plan plan);

    // Obtener contratos activos por afiliado y fecha de retiro nula
    List<Contrato> findByAfiliadoAndFechaRetiroIsNull(Afiliado afiliado);

    // Obtener contratos activos por plan y fecha de retiro nula
    List<Contrato> findByPlanAndFechaRetiroIsNull(Plan plan);

    // Obtener contratos por afiliado y fecha de retiro no nula (para actualizar la fecha de retiro al cambiar el estado del afiliado)
    List<Contrato> findByAfiliadoAndFechaRetiroIsNotNull(Afiliado afiliado);

}




