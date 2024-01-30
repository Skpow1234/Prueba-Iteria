package com.backend.pruebaIteria.repository;

import com.backend.pruebaIteria.model.Afiliado;
import com.backend.pruebaIteria.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AfiliadoRepository extends JpaRepository<Afiliado, String> {

    // Comprobar la combinación única de tipo de documento y documento
    boolean existsByTipoDocumentoAndDocumento(TipoDocumento tipoDocumento, String documento);

    // Obtener afiliados activos por tipo de documento y documento
    @Query("SELECT a FROM Afiliado a WHERE a.tipoDocumento = :tipoDocumento AND a.documento = :documento AND a.estado = 1")
    Afiliado findActiveByTipoDocumentoAndDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento, @Param("documento") String documento);

    // Obtener afiliado por ID y sus contratos
    @Query("SELECT a FROM Afiliado a LEFT JOIN FETCH a.contratos WHERE a.id = :id")
    Afiliado findWithContratosById(@Param("id") String id);


}
