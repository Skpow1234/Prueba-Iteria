package com.backend.pruebaIteria.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TipoDocumentoDTO {
    private String id;
    private String nombre;
    private Integer estado;
}
