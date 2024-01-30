package com.backend.pruebaIteria.controller;

import com.backend.pruebaIteria.DTO.ContratoDTO;
import com.backend.pruebaIteria.services.ContratoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contratos")
@Slf4j
public class ContratoController {

    private final ContratoService contratoService;

    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> getContratoById(@PathVariable String id) {
        ContratoDTO contratoDTO = contratoService.getContratoById(id);
        return ResponseEntity.ok(contratoDTO);
    }

    @PostMapping("/guardarContrato")
    public ResponseEntity guardarContrato(@RequestBody ContratoDTO contratoDTO) {
        try {
            ContratoDTO createdContrato = contratoService.createContrato(contratoDTO);
            return new ResponseEntity<>(createdContrato, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizarContrato/{id}")
    public ResponseEntity<ContratoDTO> updateContrato(@PathVariable String id, @RequestBody ContratoDTO contratoDTO) {
        contratoDTO.setId(id);
        ContratoDTO updatedContrato = contratoService.updateContrato(contratoDTO);
        return ResponseEntity.ok(updatedContrato);
    }

    @DeleteMapping("/eliminarContrato/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable String id) {
        contratoService.deleteContrato(id);
        return ResponseEntity.noContent().build();
    }
}


