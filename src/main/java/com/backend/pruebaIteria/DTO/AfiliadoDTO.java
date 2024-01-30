package com.backend.pruebaIteria.DTO;
import lombok.*;

import java.util.List;

@Getter
@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
