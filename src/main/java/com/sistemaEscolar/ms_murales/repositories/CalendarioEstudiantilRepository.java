package com.sistemaEscolar.ms_murales.repositories;

import com.sistemaEscolar.ms_murales.models.entities.CalendarioEstudiantil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repositorio JPA para la entidad CalendarioEstudiantil.
 */
@Repository
public interface CalendarioEstudiantilRepository extends JpaRepository<CalendarioEstudiantil, Long> {

    /** Retorna todos los eventos que inician en una fecha específica. */
    List<CalendarioEstudiantil> findByFechaInicio(LocalDate fechaInicio);

    /** Retorna todos los eventos vigentes a partir de una fecha (inicio >= fecha). */
    List<CalendarioEstudiantil> findByFechaInicioGreaterThanEqual(LocalDate fecha);

    /** Retorna todos los eventos de un rango de fechas. */
    List<CalendarioEstudiantil> findByFechaInicioBetween(LocalDate desde, LocalDate hasta);

    /** Retorna todos los eventos ordenados cronológicamente. */
    List<CalendarioEstudiantil> findAllByOrderByFechaInicioAsc();
}
