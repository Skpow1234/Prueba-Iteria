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
@Table(name = "CONTRATO")
public class Contrato implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CTO_ID", nullable = false)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AFI_ID", nullable = false)
    private Afiliado afiliado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PLN_ID", nullable = false)
    private Plan plan;

    @Column(name = "CTO_CANTIDAD_USUARIOS", nullable = false)
    private Integer cantidadUsuarios;

    @Column(name = "CTO_FECHA_INICIO", nullable = false)
    private Date fechaInicio;

    @Column(name = "CTO_FECHA_RETIRO")
    private Date fechaRetiro;

    @Column(name = "CTO_FECHA_REGISTRO", nullable = false)
    private Date fechaRegistro;

    @Column(name = "CTO_EPS", nullable = false)
    private String eps;
}
