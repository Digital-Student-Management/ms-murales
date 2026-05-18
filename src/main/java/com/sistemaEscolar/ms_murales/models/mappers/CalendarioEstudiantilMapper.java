package com.sistemaEscolar.ms_murales.models.mappers;

import com.sistemaEscolar.ms_murales.models.dto.requests.CalendarioEstudiantilRequestDTO;
import com.sistemaEscolar.ms_murales.models.dto.response.CalendarioEstudiantilResponseDTO;
import com.sistemaEscolar.ms_murales.models.entities.CalendarioEstudiantil;

/**
 * Mapper manual para la entidad CalendarioEstudiantil.
 */
public class CalendarioEstudiantilMapper {

    private CalendarioEstudiantilMapper() {}

    /** Convierte un RequestDTO a entidad. */
    public static CalendarioEstudiantil toEntity(CalendarioEstudiantilRequestDTO dto) {
        CalendarioEstudiantil evento = new CalendarioEstudiantil();
        evento.setIdFuncionario(dto.getIdFuncionario());
        evento.setTituloEvento(dto.getTituloEvento());
        evento.setDescripEvento(dto.getDescripEvento());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
        return evento;
    }

    /** Convierte una entidad a ResponseDTO. */
    public static CalendarioEstudiantilResponseDTO toResponseDTO(CalendarioEstudiantil evento) {
        CalendarioEstudiantilResponseDTO dto = new CalendarioEstudiantilResponseDTO();
        dto.setIdEvento(evento.getIdEvento());
        dto.setIdFuncionario(evento.getIdFuncionario());
        dto.setTituloEvento(evento.getTituloEvento());
        dto.setDescripEvento(evento.getDescripEvento());
        dto.setFechaInicio(evento.getFechaInicio());
        dto.setFechaFin(evento.getFechaFin());
        return dto;
    }

    /** Actualiza una entidad existente con los datos del DTO (para PUT). */
    public static void updateEntityFromDTO(CalendarioEstudiantilRequestDTO dto, CalendarioEstudiantil evento) {
        evento.setIdFuncionario(dto.getIdFuncionario());
        evento.setTituloEvento(dto.getTituloEvento());
        evento.setDescripEvento(dto.getDescripEvento());
        evento.setFechaInicio(dto.getFechaInicio());
        evento.setFechaFin(dto.getFechaFin());
    }
}
