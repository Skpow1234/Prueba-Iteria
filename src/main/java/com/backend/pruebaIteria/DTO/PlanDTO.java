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
public class PlanDTO {
    private String id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer estado;
}
