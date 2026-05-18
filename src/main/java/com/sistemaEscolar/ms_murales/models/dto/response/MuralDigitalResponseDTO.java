package com.sistemaEscolar.ms_murales.models.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO de salida para la entidad MuralDigital.
 */
@Data
public class MuralDigitalResponseDTO {

    private Long idPublicacion;
    private Long idFuncionario;
    private String contenido;
    private String nivelAlcance;
    private LocalDateTime fechaPublicacion;
}
