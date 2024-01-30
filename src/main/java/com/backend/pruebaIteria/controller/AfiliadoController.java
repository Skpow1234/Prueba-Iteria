package com.backend.pruebaIteria.controller;

import com.backend.pruebaIteria.DTO.AfiliadoDTO;
import com.backend.pruebaIteria.services.AfiliadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/afiliados")
@Slf4j
public class AfiliadoController {

    private final AfiliadoService afiliadoService;

    public AfiliadoController(AfiliadoService afiliadoService) {
        this.afiliadoService = afiliadoService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AfiliadoDTO> getAfiliadoById(@PathVariable String id) {
        AfiliadoDTO afiliadoDTO = afiliadoService.getAfiliadoById(id);
        return ResponseEntity.ok(afiliadoDTO);
    }

    @PostMapping("/guardarAfiliado")
    public ResponseEntity guardarAfiliado(@RequestBody AfiliadoDTO afiliadoDTO) {
        try {
            AfiliadoDTO createdAfiliado = afiliadoService.createAfiliado(afiliadoDTO);
            return new ResponseEntity<>(createdAfiliado, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/actualizarAfiliado/{id}")
    public ResponseEntity<AfiliadoDTO> updateAfiliado(@PathVariable String id, @RequestBody AfiliadoDTO afiliadoDTO) {
        afiliadoDTO.setId(id);
        AfiliadoDTO updatedAfiliado = afiliadoService.updateAfiliado(afiliadoDTO);
        return ResponseEntity.ok(updatedAfiliado);
    }

    @DeleteMapping("/eliminarAfiliado/{id}")
    public ResponseEntity<Void> deleteAfiliado(@PathVariable String id) {
        afiliadoService.deleteAfiliado(id);
        return ResponseEntity.noContent().build();
    }
}


