package com.backend.pruebaIteria.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Getter
@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "PLAN")
public class Plan implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PLN_ID", nullable = false)
    private String id;

    @Column(name = "PLN_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "PLN_FECHA_INICIO", nullable = false)
    private Date fechaInicio;

    @Column(name = "PLN_FECHA_FIN", nullable = false)
    private Date fechaFin;

    @Column(name = "PLN_ESTADO", nullable = false)
    private Integer estado;
}
