package com.sistemaEscolar.ms_murales.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import java.util.Set;

/**
 * Cliente simple para validar funcionarios en ms-usuarios.
 */
@Service
@RequiredArgsConstructor
public class UsuarioClient {

    private static final Set<String> FUNCIONARIO_TIPOS = Set.of("DIRECTIVO", "DOCENTE", "INSPECTOR");

    private final RestTemplate restTemplate;

    @Value("${ms.usuarios.base-url}")
    private String usuariosBaseUrl;

    public void assertFuncionarioExists(Long idFuncionario) {
        String url = usuariosBaseUrl + "/api/usuarios/" + idFuncionario;
        try {
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (!isFuncionario(response)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Usuario con ID " + idFuncionario + " no es funcionario");
            }
        } catch (HttpClientErrorException.NotFound ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Funcionario con ID " + idFuncionario + " no existe en ms-usuarios");
        } catch (RestClientException ex) {
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
                    "No se pudo comunicar con ms-usuarios");
        }
    }

    private boolean isFuncionario(Map<?, ?> response) {
        if (response == null) {
            return false;
        }
        Object tipoUsuario = response.get("tipoUsuario");
        if (tipoUsuario == null) {
            return false;
        }
        return FUNCIONARIO_TIPOS.contains(tipoUsuario.toString().toUpperCase());
    }
}
