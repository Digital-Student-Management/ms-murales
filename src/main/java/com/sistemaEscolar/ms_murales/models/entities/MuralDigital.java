package com.sistemaEscolar.ms_murales.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

/**
 * Entidad que representa un Mural Digital del colegio.
 * Permite publicar información, noticias y anuncios a nivel de
 * institución, curso o asignatura.
 *
 * Campos del modelo lógico:
 *  - id_publicacion  → PK autoincremental
 *  - id_funcionario  → referencia al funcionario creador (ms-usuarios)
 *  - contenido       → texto del mural
 *  - nivel_alcance   → alcance de la publicación (INSTITUCION, CURSO, ASIGNATURA), opcional
 *  - fecha_publicacion → cuándo se publicó
 */
@Entity
@Table(name = "mural_digital")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MuralDigital {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPublicacion;

    /** ID del funcionario responsable de la publicación (ms-usuarios). */
    @Column(name = "id_funcionario", nullable = false)
    private Long idFuncionario;

    /** Texto o contenido completo de la publicación del mural. */
    @Column(nullable = false, length = 1000)
    private String contenido;

    /**
     * Nivel de alcance de la publicación.
     * INSTITUCION: visible para todo el colegio.
     * CURSO: visible para un curso específico.
     * ASIGNATURA: visible para una asignatura específica.
     * Es opcional: si es null, se considera de alcance institucional.
     */
    @Column(name = "nivel_alcance", nullable = true, length = 20)
    private String nivelAlcance;

    /** Fecha y hora exacta en que se realizó la publicación. */
    @Column(nullable = false)
    private LocalDateTime fechaPublicacion;
}
