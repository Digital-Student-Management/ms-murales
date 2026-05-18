package com.sistemaEscolar.ms_murales.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

/**
 * Entidad que representa un evento del Calendario Estudiantil.
 * Los docentes y administrativos publican fechas de pruebas, actividades,
 * eventos institucionales, feriados, etc.
 *
 * Campos del modelo lógico:
 *  - id_evento      → PK autoincremental
 *  - id_funcionario → referencia al funcionario creador (ms-usuarios)
 *  - titulo_evento  → nombre del evento
 *  - descrip_evento → descripción detallada del evento
 *  - fecha_inicio   → cuándo comienza el evento
 *  - fecha_fin      → cuándo termina (opcional: puede ser un evento de un solo día)
 */
@Entity
@Table(name = "calendario_estudiantil")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarioEstudiantil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEvento;

    /** ID del funcionario responsable del evento (ms-usuarios). */
    @Column(name = "id_funcionario", nullable = false)
    private Long idFuncionario;

    @Column(nullable = false, length = 150)
    private String tituloEvento;

    @Column(nullable = false, length = 500)
    private String descripEvento;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    /**
     * Fecha de término del evento. Es opcional (o = optional en modelo lógico):
     * si es null, el evento ocurre solo en fechaInicio.
     */
    @Column(nullable = true)
    private LocalDate fechaFin;
}
