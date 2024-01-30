package com.backend.pruebaIteria.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AfiliadoDTO {
    private String id;
    private String nombre;
    private String apellidos;
    private TipoDocumentoDTO tipoDocumento;
    private String documento;
    private String telefono;
    private String direccion;
    private String email;
    private Integer estado;
    private List<ContratoDTO> contratos;
}
