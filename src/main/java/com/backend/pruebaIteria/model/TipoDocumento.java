package com.backend.pruebaIteria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TIPO_DOCUMENTO")
public class TipoDocumento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TDC_ID", nullable = false)
    private String id;

    @Column(name = "TDC_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "TDC_ESTADO", nullable = false)
    private Integer estado;
}
