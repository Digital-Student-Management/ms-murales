package com.sistemaEscolar.ms_murales.models.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

/**
 * DTO de entrada para crear o actualizar un CalendarioEstudiantil.
 */
@Data
public class CalendarioEstudiantilRequestDTO {

    @NotNull(message = "El id del funcionario es obligatorio")
    private Long idFuncionario;

    @NotBlank(message = "El título del evento no puede estar vacío")
    private String tituloEvento;

    @NotBlank(message = "La descripción del evento no puede estar vacía")
    private String descripEvento;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    /** Fecha de término opcional. Si no se informa, el evento es de un solo día. */
    private LocalDate fechaFin;
}
