package com.sistemaEscolar.ms_murales.models.mappers;

import com.sistemaEscolar.ms_murales.models.dto.requests.MuralDigitalRequestDTO;
import com.sistemaEscolar.ms_murales.models.dto.response.MuralDigitalResponseDTO;
import com.sistemaEscolar.ms_murales.models.entities.MuralDigital;

/**
 * Mapper manual para la entidad MuralDigital.
 */
public class MuralDigitalMapper {

    private MuralDigitalMapper() {}

    /** Convierte un RequestDTO a entidad. */
    public static MuralDigital toEntity(MuralDigitalRequestDTO dto) {
        MuralDigital mural = new MuralDigital();
        mural.setIdFuncionario(dto.getIdFuncionario());
        mural.setContenido(dto.getContenido());
        mural.setNivelAlcance(dto.getNivelAlcance());
        mural.setFechaPublicacion(dto.getFechaPublicacion());
        return mural;
    }

    /** Convierte una entidad a ResponseDTO. */
    public static MuralDigitalResponseDTO toResponseDTO(MuralDigital mural) {
        MuralDigitalResponseDTO dto = new MuralDigitalResponseDTO();
        dto.setIdPublicacion(mural.getIdPublicacion());
        dto.setIdFuncionario(mural.getIdFuncionario());
        dto.setContenido(mural.getContenido());
        dto.setNivelAlcance(mural.getNivelAlcance());
        dto.setFechaPublicacion(mural.getFechaPublicacion());
        return dto;
    }

    /** Actualiza una entidad existente con los datos del DTO (para PUT). */
    public static void updateEntityFromDTO(MuralDigitalRequestDTO dto, MuralDigital mural) {
        mural.setIdFuncionario(dto.getIdFuncionario());
        mural.setContenido(dto.getContenido());
        mural.setNivelAlcance(dto.getNivelAlcance());
        mural.setFechaPublicacion(dto.getFechaPublicacion());
    }
}
