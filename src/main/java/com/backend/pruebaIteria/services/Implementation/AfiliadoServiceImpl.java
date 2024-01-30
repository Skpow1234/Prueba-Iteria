package com.backend.pruebaIteria.services.Implementation;

import com.backend.pruebaIteria.DTO.AfiliadoDTO;
import com.backend.pruebaIteria.DTO.ContratoDTO;
import com.backend.pruebaIteria.mappers.AfiliadoMapper;
import com.backend.pruebaIteria.model.Afiliado;
import com.backend.pruebaIteria.model.Contrato;
import com.backend.pruebaIteria.model.Plan;
import com.backend.pruebaIteria.repository.AfiliadoRepository;
import com.backend.pruebaIteria.repository.PlanRepository;
import com.backend.pruebaIteria.repository.TipoDocumentoRepository;
import com.backend.pruebaIteria.services.AfiliadoService;
import com.backend.pruebaIteria.services.ContratoService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AfiliadoServiceImpl implements AfiliadoService {

    @Autowired
    private AfiliadoRepository afiliadoRepository;

    @Autowired
    private AfiliadoMapper afiliadoMapper;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private PlanRepository planRepository;

    @Override
    @Transactional
    public AfiliadoDTO createAfiliado(AfiliadoDTO afiliadoDTO) {
        // Validar restricciones antes de guardar en el repositorio
        validateAfiliadoCreate(afiliadoDTO);

        Afiliado afiliado = afiliadoMapper.dtoToEntity(afiliadoDTO);
        afiliado = afiliadoRepository.save(afiliado);

        // Crear contratos asociados al afiliado
        afiliadoDTO.getContratos().forEach(contratoDTO -> {
            contratoDTO.setAfiliado(afiliadoDTO);
            contratoService.createContrato(contratoDTO);
        });

        return afiliadoMapper.entityToDTO(afiliado);
    }

    @Override
    public void deleteAfiliado(String id) {
        // Implementar lógica para cambiar el estado del afiliado y grabar la fecha de retiro en sus contratos asociados
        Afiliado afiliado = afiliadoRepository.findById(id).orElse(null);
        if (afiliado != null) {
            afiliado.setEstado(0);
            afiliadoRepository.save(afiliado);

            // Cambiar el estado de los contratos asociados y grabar la fecha de retiro
            afiliado.getContratos().forEach(contrato -> {
                contratoService.deleteContrato(contrato.getId());
            });
        }
    }


    @Override
    public AfiliadoDTO getAfiliadoById(String id) {
        Afiliado afiliado = afiliadoRepository.findWithContratosById(id);
        if (afiliado == null) {
            throw new IllegalArgumentException("Afiliado no encontrado con ID: " + id);
        }

        return afiliadoMapper.entityToDTO(afiliado);
    }

    @Override
    @Transactional
    public AfiliadoDTO updateAfiliado(AfiliadoDTO afiliadoDTO) {
        // Validar restricciones antes de actualizar en el repositorio
        validateAfiliadoUpdate(afiliadoDTO);

        // Obtener afiliado existente
        Afiliado existingAfiliado = afiliadoRepository.findById(afiliadoDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("Afiliado no encontrado con ID: " + afiliadoDTO.getId()));

        // Actualizar campos del afiliado existente
        existingAfiliado.setNombre(afiliadoDTO.getNombre());
        existingAfiliado.setApellidos(afiliadoDTO.getApellidos());
        existingAfiliado.setTipoDocumento(tipoDocumentoRepository.findById(afiliadoDTO.getTipoDocumento().getId())
                .orElseThrow(() -> new IllegalArgumentException("Tipo de documento no encontrado")));
        existingAfiliado.setDocumento(afiliadoDTO.getDocumento());
        existingAfiliado.setTelefono(afiliadoDTO.getTelefono());
        existingAfiliado.setDireccion(afiliadoDTO.getDireccion());
        existingAfiliado.setEmail(afiliadoDTO.getEmail());

        // Actualizar contratos asociados
        List<ContratoDTO> contratoDTOList = afiliadoDTO.getContratos();
        for (ContratoDTO contratoDTO : contratoDTOList) {
            Contrato existingContrato = existingAfiliado.getContratos().stream()
                    .filter(contrato -> contrato.getId().equals(contratoDTO.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Contrato no encontrado con ID: " + contratoDTO.getId()));

            // Validar restricciones antes de actualizar el contrato
            validateContratoUpdate(contratoDTO, existingContrato);

            // Actualizar campos del contrato existente
            existingContrato.setPlan(planRepository.findById(contratoDTO.getPlan().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Plan no encontrado")));
            existingContrato.setCantidadUsuarios(contratoDTO.getCantidadUsuarios());
            existingContrato.setFechaInicio(contratoDTO.getFechaInicio());
            existingContrato.setFechaRetiro(contratoDTO.getFechaRetiro());
            existingContrato.setFechaRegistro(contratoDTO.getFechaRegistro());
            existingContrato.setEps(contratoDTO.getEps());

            // Actualizar estado del contrato basado en la fecha de retiro
            updateContratoState(existingContrato);
        }

        // Guardar cambios en el afiliado actualizado
        Afiliado updatedAfiliado = afiliadoRepository.save(existingAfiliado);

        return afiliadoMapper.entityToDTO(updatedAfiliado);
    }

    private void validateAfiliadoCreate(AfiliadoDTO afiliadoDTO) {
        // Validaciones específicas para la creación de afiliados
        if (afiliadoRepository.existsByTipoDocumentoAndDocumento(
                tipoDocumentoRepository.findById(afiliadoDTO.getTipoDocumento().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Tipo de documento no encontrado")),
                afiliadoDTO.getDocumento())) {
            throw new IllegalStateException("La combinación de tipo documento y documento debe ser única para la tabla de afiliados.");
        }

        if (afiliadoDTO.getTipoDocumento().getEstado() != 1) {
            throw new IllegalStateException("Solo se puede utilizar un tipo de documento con estado activo para el registro de afiliados.");
        }
    }

    private void validateAfiliadoUpdate(AfiliadoDTO afiliadoDTO) {
        // Validaciones específicas para la actualización de afiliados
        // Puedes agregar más validaciones según tus requerimientos
    }

    private void validateContratoUpdate(ContratoDTO contratoDTO, Contrato existingContrato) {
        // Validaciones específicas para la actualización de contratos
        if (existingContrato.getPlan().getId().equals(contratoDTO.getPlan().getId())) {
            throw new IllegalStateException("No se puede contratar el mismo plan que ya está asociado y activo.");
        }

        if (existingContrato.getPlan().getFechaFin().equals(contratoDTO.getFechaRegistro())) {
            throw new IllegalStateException("No se puede registrar un contrato si su plan tiene como fecha fin el mismo día de registro del contrato.");
        }
    }

    private void updateContratoState(Contrato contrato) {
        Plan plan = contrato.getPlan();

        // Verificar si el plan está activo
        if (plan.getEstado() == 1) {
            // Lógica para actualizar el estado del contrato basado en la fecha de retiro
            if (contrato.getFechaRetiro() != null && contrato.getFechaRetiro().before(new Date())) {
                plan.setEstado(0); // Estado inactivo
            } else {
                plan.setEstado(1); // Estado activo
            }
        } else {
            plan.setEstado(0); // Estado inactivo si el plan no está activo
        }
    }

}