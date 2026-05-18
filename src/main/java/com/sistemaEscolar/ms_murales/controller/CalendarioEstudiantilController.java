package com.sistemaEscolar.ms_murales.controller;

import com.sistemaEscolar.ms_murales.models.dto.requests.CalendarioEstudiantilRequestDTO;
import com.sistemaEscolar.ms_murales.models.dto.response.CalendarioEstudiantilResponseDTO;
import com.sistemaEscolar.ms_murales.services.CalendarioEstudiantilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para la gestión del calendario estudiantil.
 * Solo delega en el servicio; no contiene lógica de negocio.
 */
@RestController
@RequestMapping("/api/calendario")
@RequiredArgsConstructor
@Tag(name = "Calendario Estudiantil", description = "API de gestión del calendario de eventos del Colegio Bernardo O'Higgins")
public class CalendarioEstudiantilController {

    private final CalendarioEstudiantilService calendarioService;

    @GetMapping
    @Operation(summary = "Listar todos los eventos del calendario (orden cronológico)")
    public ResponseEntity<List<CalendarioEstudiantilResponseDTO>> getAll() {
        return ResponseEntity.ok(calendarioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un evento por ID")
    public ResponseEntity<CalendarioEstudiantilResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(calendarioService.findById(id));
    }

    @GetMapping("/proximos")
    @Operation(summary = "Listar eventos próximos (desde hoy en adelante)")
    public ResponseEntity<List<CalendarioEstudiantilResponseDTO>> getProximos() {
        return ResponseEntity.ok(calendarioService.findProximos());
    }

    @GetMapping("/rango")
    @Operation(summary = "Listar eventos dentro de un rango de fechas")
    public ResponseEntity<List<CalendarioEstudiantilResponseDTO>> getByRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {
        return ResponseEntity.ok(calendarioService.findByRango(desde, hasta));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo evento en el calendario")
    public ResponseEntity<CalendarioEstudiantilResponseDTO> create(
            @Valid @RequestBody CalendarioEstudiantilRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(calendarioService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un evento existente del calendario")
    public ResponseEntity<CalendarioEstudiantilResponseDTO> update(@PathVariable Long id,
                                                                   @Valid @RequestBody CalendarioEstudiantilRequestDTO dto) {
        return ResponseEntity.ok(calendarioService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un evento del calendario por ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        calendarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
