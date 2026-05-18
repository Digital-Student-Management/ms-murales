package com.sistemaEscolar.ms_murales.services;

import com.sistemaEscolar.ms_murales.models.dto.requests.MuralDigitalRequestDTO;
import com.sistemaEscolar.ms_murales.models.dto.response.MuralDigitalResponseDTO;
import com.sistemaEscolar.ms_murales.models.entities.MuralDigital;
import com.sistemaEscolar.ms_murales.models.mappers.MuralDigitalMapper;
import com.sistemaEscolar.ms_murales.repositories.MuralDigitalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * Servicio de lógica de negocio para la entidad MuralDigital.
 */
@Service
@RequiredArgsConstructor
public class MuralDigitalService {

    private final MuralDigitalRepository muralDigitalRepository;
    private final UsuarioClient usuarioClient;

    // ─── Lectura ─────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<MuralDigitalResponseDTO> findAll() {
        return muralDigitalRepository.findAllByOrderByFechaPublicacionDesc().stream()
                .map(MuralDigitalMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public MuralDigitalResponseDTO findById(Long id) {
        return MuralDigitalMapper.toResponseDTO(findMuralOrThrow(id));
    }

    @Transactional(readOnly = true)
    public List<MuralDigitalResponseDTO> findByNivelAlcance(String nivelAlcance) {
        return muralDigitalRepository.findByNivelAlcance(nivelAlcance).stream()
                .map(MuralDigitalMapper::toResponseDTO)
                .toList();
    }

    // ─── Creación ────────────────────────────────────────────────────────────

    @Transactional
    public MuralDigitalResponseDTO create(MuralDigitalRequestDTO dto) {
        usuarioClient.assertFuncionarioExists(dto.getIdFuncionario());
        MuralDigital nuevo = MuralDigitalMapper.toEntity(dto);
        return MuralDigitalMapper.toResponseDTO(muralDigitalRepository.save(nuevo));
    }

    // ─── Actualización ───────────────────────────────────────────────────────

    @Transactional
    public MuralDigitalResponseDTO update(Long id, MuralDigitalRequestDTO dto) {
        MuralDigital existente = findMuralOrThrow(id);
        usuarioClient.assertFuncionarioExists(dto.getIdFuncionario());
        MuralDigitalMapper.updateEntityFromDTO(dto, existente);
        return MuralDigitalMapper.toResponseDTO(muralDigitalRepository.save(existente));
    }

    // ─── Eliminación ─────────────────────────────────────────────────────────

    @Transactional
    public void delete(Long id) {
        findMuralOrThrow(id);
        muralDigitalRepository.deleteById(id);
    }

    // ─── Soporte privado ─────────────────────────────────────────────────────

    private MuralDigital findMuralOrThrow(Long id) {
        return muralDigitalRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Publicación de mural con ID " + id + " no encontrada"));
    }
}
