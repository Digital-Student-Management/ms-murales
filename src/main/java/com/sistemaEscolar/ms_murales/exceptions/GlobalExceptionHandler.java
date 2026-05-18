package com.sistemaEscolar.ms_murales.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones.
 * Intercepta excepciones lanzadas desde cualquier controller y las convierte
 * en respuestas HTTP estructuradas y consistentes.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Maneja errores de validación de Bean Validation (@NotBlank, @NotNull, etc.)
     * Retorna 400 Bad Request con un mapa de campo → mensaje de error.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> erroresCampos = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            erroresCampos.put(error.getField(), error.getDefaultMessage());
        }

        Map<String, Object> respuesta = buildErrorResponse(
                HttpStatus.BAD_REQUEST,
                "Error de validación en los datos enviados",
                erroresCampos
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(respuesta);
    }

    /**
     * Maneja ResponseStatusException lanzadas desde el servicio (400, 404, 409, etc.).
     * Retorna el código HTTP y mensaje exactos definidos en el servicio.
     */
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> respuesta = buildErrorResponse(
                HttpStatus.valueOf(ex.getStatusCode().value()),
                ex.getReason(),
                null
        );

        return ResponseEntity.status(ex.getStatusCode()).body(respuesta);
    }

    /**
     * Captura cualquier excepción no prevista para evitar exponer stack traces al cliente.
     * Retorna siempre 500 Internal Server Error.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> respuesta = buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Error interno del servidor: " + ex.getMessage(),
                null
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respuesta);
    }

    /** Construye la estructura de respuesta de error estandarizada. */
    private Map<String, Object> buildErrorResponse(HttpStatus status, String mensaje, Object detalle) {
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("timestamp", LocalDateTime.now().toString());
        respuesta.put("status", status.value());
        respuesta.put("error", status.getReasonPhrase());
        respuesta.put("mensaje", mensaje);
        if (detalle != null) {
            respuesta.put("detalle", detalle);
        }
        return respuesta;
    }
}
