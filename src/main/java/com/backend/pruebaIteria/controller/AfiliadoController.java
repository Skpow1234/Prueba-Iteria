package com.backend.pruebaIteria.controller;

import com.backend.pruebaIteria.DTO.AfiliadoDTO;
import com.backend.pruebaIteria.services.AfiliadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/afiliados")
public class AfiliadoController {

    @Autowired
    private AfiliadoService afiliadoService;

    @GetMapping("/{id}")
    public ResponseEntity<AfiliadoDTO> getAfiliadoById(@PathVariable String id) {
        AfiliadoDTO afiliadoDTO = afiliadoService.getAfiliadoById(id);
        return ResponseEntity.ok(afiliadoDTO);
    }

    @PostMapping
    public ResponseEntity<AfiliadoDTO> createAfiliado(@RequestBody AfiliadoDTO afiliadoDTO) {
        AfiliadoDTO createdAfiliado = afiliadoService.createAfiliado(afiliadoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAfiliado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AfiliadoDTO> updateAfiliado(@PathVariable String id, @RequestBody AfiliadoDTO afiliadoDTO) {
        afiliadoDTO.setId(id);
        AfiliadoDTO updatedAfiliado = afiliadoService.updateAfiliado(afiliadoDTO);
        return ResponseEntity.ok(updatedAfiliado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAfiliado(@PathVariable String id) {
        afiliadoService.deleteAfiliado(id);
        return ResponseEntity.noContent().build();
    }
}

