package com.sistemaEscolar.ms_murales.controller;

import com.sistemaEscolar.ms_murales.models.dto.requests.MuralDigitalRequestDTO;
import com.sistemaEscolar.ms_murales.models.dto.response.MuralDigitalResponseDTO;
import com.sistemaEscolar.ms_murales.services.MuralDigitalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de murales digitales.
 * Solo delega en el servicio; no contiene lógica de negocio.
 */
@RestController
@RequestMapping("/api/murales")
@RequiredArgsConstructor
@Tag(name = "Murales Digitales", description = "API de gestión de murales digitales del Colegio Bernardo O'Higgins")
public class MuralDigitalController {

    private final MuralDigitalService muralDigitalService;

    @GetMapping
    @Operation(summary = "Listar todas las publicaciones de murales (más recientes primero)")
    public ResponseEntity<List<MuralDigitalResponseDTO>> getAll() {
        return ResponseEntity.ok(muralDigitalService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una publicación de mural por ID")
    public ResponseEntity<MuralDigitalResponseDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(muralDigitalService.findById(id));
    }

    @GetMapping("/nivel/{nivelAlcance}")
    @Operation(summary = "Listar publicaciones de murales por nivel de alcance (INSTITUCION, CURSO, ASIGNATURA)")
        public ResponseEntity<List<MuralDigitalResponseDTO>> getByNivelAlcance(
            @PathVariable String nivelAlcance) {
        return ResponseEntity.ok(muralDigitalService.findByNivelAlcance(nivelAlcance));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva publicación de mural")
    public ResponseEntity<MuralDigitalResponseDTO> create(@Valid @RequestBody MuralDigitalRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(muralDigitalService.create(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una publicación de mural existente")
    public ResponseEntity<MuralDigitalResponseDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody MuralDigitalRequestDTO dto) {
        return ResponseEntity.ok(muralDigitalService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una publicación de mural por ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        muralDigitalService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
