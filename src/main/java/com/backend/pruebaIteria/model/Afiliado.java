package com.backend.pruebaIteria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AFILIADO")
public class Afiliado {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AFI_ID", nullable = false)
    private String id;

    @Column(name = "AFI_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "AFI_APELLIDOS", nullable = false)
    private String apellidos;

    @ManyToOne
    @JoinColumn(name = "TDC_ID", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(name = "AFI_DOCUMENTO", nullable = false, unique = true)
    private String documento;

    @Column(name = "AFI_TELEFONO", nullable = false)
    private String telefono;

    @Column(name = "AFI_DIRECCION", nullable = false)
    private String direccion;

    @Column(name = "AFI_EMAIL", nullable = false)
    private String email;

    @Column(name = "AFI_ESTADO", nullable = false)
    private Integer estado;

    @OneToMany(mappedBy = "afiliado", cascade = CascadeType.ALL)
    private List<Contrato> contratos;
}
