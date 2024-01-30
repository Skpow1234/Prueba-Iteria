package com.backend.pruebaIteria.services.Implementation;

import com.backend.pruebaIteria.DTO.AfiliadoDTO;
import com.backend.pruebaIteria.DTO.ContratoDTO;
import com.backend.pruebaIteria.mappers.AfiliadoMapper;
import com.backend.pruebaIteria.model.Afiliado;
import com.backend.pruebaIteria.model.Contrato;
import com.backend.pruebaIteria.model.Plan;
import com.backend.pruebaIteria.model.TipoDocumento;
import com.backend.pruebaIteria.repository.AfiliadoRepository;
import com.backend.pruebaIteria.repository.PlanRepository;
import com.backend.pruebaIteria.repository.TipoDocumentoRepository;
import com.backend.pruebaIteria.services.AfiliadoService;
import com.backend.pruebaIteria.services.ContratoService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class AfiliadoServiceImpl implements AfiliadoService {

    @Autowired
    private final AfiliadoRepository afiliadoRepository;

    @Autowired
    private final AfiliadoMapper afiliadoMapper;

    @Autowired
    private final ContratoService contratoService;

    @Autowired
    private final TipoDocumentoRepository tipoDocumentoRepository;

    @Autowired
    private final PlanRepository planRepository;

    // Logger for the class
    private static final Logger logger = LoggerFactory.getLogger(AfiliadoServiceImpl.class);

    public AfiliadoServiceImpl(AfiliadoRepository afiliadoRepository, AfiliadoMapper afiliadoMapper, ContratoService contratoService, TipoDocumentoRepository tipoDocumentoRepository, PlanRepository planRepository) {
        this.afiliadoRepository = afiliadoRepository;
        this.afiliadoMapper = afiliadoMapper;
        this.contratoService = contratoService;
        this.tipoDocumentoRepository = tipoDocumentoRepository;
        this.planRepository = planRepository;
    }

    @Override
    @Transactional
    public AfiliadoDTO createAfiliado(AfiliadoDTO afiliadoDTO) {
        try {
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
        } catch (Exception ex) {
            logger.error("Error creating Afiliado: {}", ex.getMessage());
            throw ex; // Rethrow the exception for handling at a higher level
        }
    }

    @Override
    public void deleteAfiliado(String id) {
        try {
            // Implementar lógica para cambiar el estado del afiliado y grabar la fecha de retiro en sus contratos asociados
            Afiliado afiliado = afiliadoRepository.findById(id).orElse(null);
            if (afiliado != null) {
                afiliado.setEstado(0);
                afiliadoRepository.save(afiliado);

                // Cambiar el estado de los contratos asociados y grabar la fecha de retiro
                afiliado.getContratos().forEach(contrato -> contratoService.deleteContrato(contrato.getId()));
            }
        } catch (Exception ex) {
            logger.error("Error deleting Afiliado: {}", ex.getMessage());
            throw ex; // Rethrow the exception for handling at a higher level
        }
    }

    @Override
    public AfiliadoDTO getAfiliadoById(String id) {
        Afiliado afiliado = afiliadoRepository.findWithContratosById(id);
        if (afiliado == null) {
            throw new EntityNotFoundException("Afiliado not found with ID: " + id);
        }
        return afiliadoMapper.entityToDTO(afiliado);
    }

    @Override
    @Transactional
    public AfiliadoDTO updateAfiliado(AfiliadoDTO afiliadoDTO) {
        try {
            // Validar restricciones antes de actualizar en el repositorio
            validateAfiliadoUpdate(afiliadoDTO);

            // Obtener afiliado existente
            Afiliado existingAfiliado = afiliadoRepository.findById(afiliadoDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Afiliado not found with ID: " + afiliadoDTO.getId()));

            // Actualizar campos del afiliado existente
            existingAfiliado.setNombre(afiliadoDTO.getNombre());
            existingAfiliado.setApellidos(afiliadoDTO.getApellidos());
            existingAfiliado.setTipoDocumento(tipoDocumentoRepository.findById(afiliadoDTO.getTipoDocumento().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Tipo de documento not found")));
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
                        .orElseThrow(() -> new IllegalArgumentException("Contrato not found with ID: " + contratoDTO.getId()));

                // Validar restricciones antes de actualizar el contrato
                validateContratoUpdate(contratoDTO, existingContrato);

                // Actualizar campos del contrato existente
                existingContrato.setPlan(planRepository.findById(contratoDTO.getPlan().getId())
                        .orElseThrow(() -> new IllegalArgumentException("Plan not found")));
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
        } catch (Exception ex) {
            logger.error("Error updating Afiliado: {}", ex.getMessage());
            throw ex; // Rethrow the exception for handling at a higher level
        }
    }

    private TipoDocumento getTipoDocumentoById(String tipoDocumentoId) {
        return tipoDocumentoRepository.findById(tipoDocumentoId)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de documento not found"));
    }

    private void validateAfiliadoCreate(AfiliadoDTO afiliadoDTO) {
        try {
            TipoDocumento tipoDocumento = getTipoDocumentoById(afiliadoDTO.getTipoDocumento().getId());

            if (afiliadoRepository.existsByTipoDocumentoAndDocumento(tipoDocumento, afiliadoDTO.getDocumento())) {
                throw new IllegalStateException("La combinación de tipo documento y documento debe ser única para la tabla de afiliados.");
            }

            if (tipoDocumento.getEstado() != 1) {
                throw new IllegalStateException("Solo se puede utilizar un tipo de documento con estado activo para el registro de afiliados.");
            }
        } catch (Exception ex) {
            logger.error("Error validating Afiliado create: {}", ex.getMessage());
            throw ex; // Rethrow the exception for handling at a higher level
        }
    }


    private void validateAfiliadoUpdate(AfiliadoDTO afiliadoDTO) {
        try {
            // Validaciones específicas para la actualización de afiliados
            if (afiliadoDTO.getId() == null) {
                throw new IllegalArgumentException("Afiliado ID cannot be null for update.");
            }

            // Validar si el afiliado existe
            Afiliado existingAfiliado = afiliadoRepository.findById(afiliadoDTO.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Afiliado not found with ID: " + afiliadoDTO.getId()));

            // Validar restricciones adicionales según tus requerimientos
            // Por ejemplo, puedes agregar validaciones para campos específicos:
            // if (afiliadoDTO.getSomeField() == null) {
            //    throw new IllegalStateException("SomeField cannot be null for afiliado update.");
            // }

            // Puedes agregar más validaciones según tus requerimientos

        } catch (Exception ex) {
            logger.error("Error validating Afiliado update: {}", ex.getMessage());
            throw ex; // Rethrow the exception for handling at a higher level
        }
    }


    private void validateContratoUpdate(ContratoDTO contratoDTO, Contrato existingContrato) {
        try {
            // Validaciones específicas para la actualización de contratos
            if (existingContrato.getPlan().getId().equals(contratoDTO.getPlan().getId())) {
                throw new IllegalStateException("No se puede contratar el mismo plan que ya está asociado y activo.");
            }

            if (existingContrato.getPlan().getFechaFin().equals(contratoDTO.getFechaRegistro())) {
                throw new IllegalStateException("No se puede registrar un contrato si su plan tiene como fecha fin el mismo día de registro del contrato.");
            }
        } catch (Exception ex) {
            logger.error("Error validating Contrato update: {}", ex.getMessage());
            throw ex; // Rethrow the exception for handling at a higher level
        }
    }

    private void updateContratoState(Contrato contrato) {
        try {
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
        } catch (Exception ex) {
            logger.error("Error updating Contrato state: {}", ex.getMessage());
            throw ex; // Rethrow the exception for handling at a higher level
        }
    }
}
