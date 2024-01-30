package com.backend.pruebaIteria.repository;

import com.backend.pruebaIteria.model.TipoDocumento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumento, String> {

    // Obtener tipo de documento por nombre y estado activo
    TipoDocumento findByNombreAndEstado(String nombre, Integer estado);

}

