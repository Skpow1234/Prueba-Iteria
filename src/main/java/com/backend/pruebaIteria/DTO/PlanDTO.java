package com.backend.pruebaIteria.DTO;
import lombok.*;

import java.util.Date;

@Getter
@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlanDTO {
    private String id;
    private String nombre;
    private Date fechaInicio;
    private Date fechaFin;
    private Integer estado;
}
