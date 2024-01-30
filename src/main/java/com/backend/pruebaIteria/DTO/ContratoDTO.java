package com.backend.pruebaIteria.DTO;
import lombok.*;

import java.util.Date;

@Getter
@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContratoDTO {
    private String id;
    private AfiliadoDTO afiliado;
    private PlanDTO plan;
    private Integer cantidadUsuarios;
    private Date fechaInicio;
    private Date fechaRetiro;
    private Date fechaRegistro;
    private String eps;
}
