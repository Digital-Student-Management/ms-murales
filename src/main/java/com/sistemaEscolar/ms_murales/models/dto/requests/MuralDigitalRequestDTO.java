package com.sistemaEscolar.ms_murales.models.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO de entrada para crear o actualizar un MuralDigital.
 */
@Data
public class MuralDigitalRequestDTO {

    @NotNull(message = "El id del funcionario es obligatorio")
    private Long idFuncionario;

    @NotBlank(message = "El contenido no puede estar vacío")
    private String contenido;

    /** Nivel de alcance opcional (INSTITUCION, CURSO, ASIGNATURA). */
    private String nivelAlcance;

    @NotNull(message = "La fecha de publicación es obligatoria")
    private LocalDateTime fechaPublicacion;
}
