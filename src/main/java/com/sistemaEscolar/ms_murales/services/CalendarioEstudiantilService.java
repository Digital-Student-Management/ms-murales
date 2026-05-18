package com.sistemaEscolar.ms_murales.services;

import com.sistemaEscolar.ms_murales.models.dto.requests.CalendarioEstudiantilRequestDTO;
import com.sistemaEscolar.ms_murales.models.dto.response.CalendarioEstudiantilResponseDTO;
import com.sistemaEscolar.ms_murales.models.entities.CalendarioEstudiantil;
import com.sistemaEscolar.ms_murales.models.mappers.CalendarioEstudiantilMapper;
import com.sistemaEscolar.ms_murales.repositories.CalendarioEstudiantilRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

/**
 * Servicio de lógica de negocio para la entidad CalendarioEstudiantil.
 */
@Service
@RequiredArgsConstructor
public class CalendarioEstudiantilService {

    private final CalendarioEstudiantilRepository calendarioRepository;
    private final UsuarioClient usuarioClient;

    // ─── Lectura ─────────────────────────────────────────────────────────────

    @Transactional(readOnly = true)
    public List<CalendarioEstudiantilResponseDTO> findAll() {
        return calendarioRepository.findAllByOrderByFechaInicioAsc().stream()
                .map(CalendarioEstudiantilMapper::toResponseDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public CalendarioEstudiantilResponseDTO findById(Long id) {
        return CalendarioEstudiantilMapper.toResponseDTO(findEventoOrThrow(id));
    }

    /** Retorna todos los eventos vigentes a partir de hoy. */
    @Transactional(readOnly = true)
    public List<CalendarioEstudiantilResponseDTO> findProximos() {
        return calendarioRepository.findByFechaInicioGreaterThanEqual(LocalDate.now()).stream()
                .map(CalendarioEstudiantilMapper::toResponseDTO)
                .toList();
    }

    /** Retorna eventos dentro de un rango de fechas. */
    @Transactional(readOnly = true)
    public List<CalendarioEstudiantilResponseDTO> findByRango(LocalDate desde, LocalDate hasta) {
        if (desde.isAfter(hasta)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha 'desde' no puede ser posterior a la fecha 'hasta'");
        }
        return calendarioRepository.findByFechaInicioBetween(desde, hasta).stream()
                .map(CalendarioEstudiantilMapper::toResponseDTO)
                .toList();
    }

    // ─── Creación ────────────────────────────────────────────────────────────

    @Transactional
    public CalendarioEstudiantilResponseDTO create(CalendarioEstudiantilRequestDTO dto) {
        validarFechas(dto.getFechaInicio(), dto.getFechaFin());
        usuarioClient.assertFuncionarioExists(dto.getIdFuncionario());
        CalendarioEstudiantil nuevo = CalendarioEstudiantilMapper.toEntity(dto);
        return CalendarioEstudiantilMapper.toResponseDTO(calendarioRepository.save(nuevo));
    }

    // ─── Actualización ───────────────────────────────────────────────────────

    @Transactional
    public CalendarioEstudiantilResponseDTO update(Long id, CalendarioEstudiantilRequestDTO dto) {
        CalendarioEstudiantil existente = findEventoOrThrow(id);
        validarFechas(dto.getFechaInicio(), dto.getFechaFin());
        usuarioClient.assertFuncionarioExists(dto.getIdFuncionario());
        CalendarioEstudiantilMapper.updateEntityFromDTO(dto, existente);
        return CalendarioEstudiantilMapper.toResponseDTO(calendarioRepository.save(existente));
    }

    // ─── Eliminación ─────────────────────────────────────────────────────────

    @Transactional
    public void delete(Long id) {
        findEventoOrThrow(id);
        calendarioRepository.deleteById(id);
    }

    // ─── Soporte privado ─────────────────────────────────────────────────────

    private CalendarioEstudiantil findEventoOrThrow(Long id) {
        return calendarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Evento de calendario con ID " + id + " no encontrado"));
    }

    /** Valida que fechaFin no sea anterior a fechaInicio cuando se informa. */
    private void validarFechas(LocalDate inicio, LocalDate fin) {
        if (fin != null && fin.isBefore(inicio)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "La fecha de término no puede ser anterior a la fecha de inicio");
        }
    }
}
