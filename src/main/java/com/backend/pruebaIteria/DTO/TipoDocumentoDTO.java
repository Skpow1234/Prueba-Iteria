package com.backend.pruebaIteria.DTO;
import lombok.*;

@Getter
@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TipoDocumentoDTO {
    private String id;
    private String nombre;
    private Integer estado;
}
