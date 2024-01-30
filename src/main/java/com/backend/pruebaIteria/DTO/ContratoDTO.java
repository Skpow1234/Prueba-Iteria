package com.backend.pruebaIteria.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
