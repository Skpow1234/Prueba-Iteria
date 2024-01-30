package com.backend.pruebaIteria.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Data
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AFILIADO")
public class Afiliado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "AFI_ID", nullable = false)
    private String id;

    @Column(name = "AFI_NOMBRE", nullable = false)
    private String nombre;

    @Column(name = "AFI_APELLIDOS", nullable = false)
    private String apellidos;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @OneToMany(mappedBy = "afiliado", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Contrato> contratos;
}
