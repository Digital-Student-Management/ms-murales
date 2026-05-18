package com.sistemaEscolar.ms_murales.models.dto.response;

import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de salida para la entidad CalendarioEstudiantil.
 */
@Data
public class CalendarioEstudiantilResponseDTO {

    private Long idEvento;
    private Long idFuncionario;
    private String tituloEvento;
    private String descripEvento;
    private LocalDate fechaInicio;

    /** Puede ser null si el evento es de un solo día. */
    private LocalDate fechaFin;
}
