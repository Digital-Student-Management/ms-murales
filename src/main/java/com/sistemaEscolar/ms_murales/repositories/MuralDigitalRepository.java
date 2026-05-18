package com.sistemaEscolar.ms_murales.repositories;

import com.sistemaEscolar.ms_murales.models.entities.MuralDigital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad MuralDigital.
 */
@Repository
public interface MuralDigitalRepository extends JpaRepository<MuralDigital, Long> {

    /** Retorna todas las publicaciones de un nivel de alcance específico. */
    List<MuralDigital> findByNivelAlcance(String nivelAlcance);

    /** Retorna publicaciones ordenadas por fecha descendente. */
    List<MuralDigital> findAllByOrderByFechaPublicacionDesc();
}
