package com.backend.pruebaIteria.repository;

import com.backend.pruebaIteria.model.Afiliado;
import com.backend.pruebaIteria.model.TipoDocumento;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AfiliadoRepository extends JpaRepository<Afiliado, String> {

    boolean existsByTipoDocumentoAndDocumento(TipoDocumento tipoDocumento, String documento);

    @Query("SELECT a FROM Afiliado a WHERE a.tipoDocumento = :tipoDocumento AND a.documento = :documento AND a.estado = 1")
    Optional<Afiliado> findActiveByTipoDocumentoAndDocumento(@Param("tipoDocumento") TipoDocumento tipoDocumento, @Param("documento") String documento);

    default Afiliado findWithContratosById(String id) {
        return findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Afiliado not found with ID: " + id));
    }
}

