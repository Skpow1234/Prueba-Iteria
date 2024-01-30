package com.backend.pruebaIteria.controller;

import com.backend.pruebaIteria.DTO.ContratoDTO;
import com.backend.pruebaIteria.services.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @GetMapping("/{id}")
    public ResponseEntity<ContratoDTO> getContratoById(@PathVariable String id) {
        ContratoDTO contratoDTO = contratoService.getContratoById(id);
        return ResponseEntity.ok(contratoDTO);
    }

    @PostMapping
    public ResponseEntity<ContratoDTO> createContrato(@RequestBody ContratoDTO contratoDTO) {
        ContratoDTO createdContrato = contratoService.createContrato(contratoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdContrato);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContratoDTO> updateContrato(@PathVariable String id, @RequestBody ContratoDTO contratoDTO) {
        contratoDTO.setId(id);
        ContratoDTO updatedContrato = contratoService.updateContrato(contratoDTO);
        return ResponseEntity.ok(updatedContrato);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrato(@PathVariable String id) {
        contratoService.deleteContrato(id);
        return ResponseEntity.noContent().build();
    }
}

