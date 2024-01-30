package com.backend.pruebaIteria.services.Implementation;

import com.backend.pruebaIteria.DTO.ContratoDTO;
import com.backend.pruebaIteria.mappers.AfiliadoMapper;
import com.backend.pruebaIteria.mappers.ContratoMapper;
import com.backend.pruebaIteria.mappers.PlanMapper;
import com.backend.pruebaIteria.model.Afiliado;
import com.backend.pruebaIteria.model.Contrato;
import com.backend.pruebaIteria.model.Plan;
import com.backend.pruebaIteria.repository.ContratoRepository;
import com.backend.pruebaIteria.services.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContratoServiceImpl implements ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoMapper contratoMapper;

    @Autowired
    private AfiliadoMapper afiliadoMapper;

    @Autowired
    private PlanMapper planMapper;

    @Override
    public ContratoDTO getContratoById(String id) {
        Contrato contrato = contratoRepository.findById(id).orElse(null);
        return contratoMapper.entityToDTO(contrato);
    }

    @Override
    public ContratoDTO createContrato(ContratoDTO contratoDTO) {
        // Validar restricciones antes de guardar en el repositorio
        if (contratoDTO.getPlan().getEstado() != 1) {
            throw new IllegalStateException("No se puede registrar un contrato con un plan inactivo.");
        }

        Afiliado afiliado = afiliadoMapper.dtoToEntity(contratoDTO.getAfiliado());
        Plan plan = planMapper.dtoToEntity(contratoDTO.getPlan());

        // Verificar si el afiliado ya tiene un contrato activo con este plan
        if (contratoRepository.existsByAfiliadoAndPlanAndFechaRetiroIsNull(afiliado, plan)) {
            throw new IllegalStateException("El afiliado ya tiene un contrato activo con este plan.");
        }

        // Verificar si el plan tiene como fecha fin el mismo día de registro del contrato
        if (plan.getFechaFin() != null && plan.getFechaFin().equals(contratoDTO.getFechaRegistro())) {
            throw new IllegalStateException("No se puede registrar un contrato si su plan tiene como fecha fin el mismo día de registro del contrato.");
        }

        Contrato contrato = contratoMapper.dtoToEntity(contratoDTO);
        contrato = contratoRepository.save(contrato);
        return contratoMapper.entityToDTO(contrato);
    }

    @Override
    public ContratoDTO updateContrato(ContratoDTO contratoDTO) {
        // Validar restricciones antes de actualizar en el repositorio
        Contrato contrato = contratoMapper.dtoToEntity(contratoDTO);
        contrato = contratoRepository.save(contrato);
        return contratoMapper.entityToDTO(contrato);
    }

    @Override
    public void deleteContrato(String id) {
        // Implementar lógica para cambiar el estado del contrato y grabar la fecha de retiro
        Contrato contrato = contratoRepository.findById(id).orElse(null);
        if (contrato != null) {
            contrato.setFechaRetiro(new Date());
            contratoRepository.save(contrato);
        }
    }
}


