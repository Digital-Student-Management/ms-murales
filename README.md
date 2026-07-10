# 📌 ms-murales — Mural Digital y Calendario

Microservicio encargado del **mural digital** (comunicados/anuncios) y del **calendario
estudiantil** (eventos y fechas importantes).

> Parte del proyecto GED. Para ejecutar **todo el sistema**, ver el [README raíz](../README.md).

---

## ⚙️ Datos técnicos

| | |
|---|---|
| **Puerto** | `8087` |
| **Base de datos** | `ms_murales_db` (MySQL, se crea automáticamente) |
| **Stack** | Spring Boot 4 · Java 21 · Spring Data JPA · RestTemplate · springdoc (Swagger) |

---

## 📡 Endpoints principales

### Mural Digital — `/api/murales`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/murales` | Lista las publicaciones (más recientes primero) |
| GET | `/api/murales/{id}` | Publicación por ID |
| GET | `/api/murales/nivel/{nivelAlcance}` | Publicaciones por alcance (INSTITUCION/CURSO/ASIGNATURA) |
| POST | `/api/murales` | Crea una publicación |
| PUT | `/api/murales/{id}` | Actualiza una publicación |
| DELETE | `/api/murales/{id}` | Elimina una publicación |

### Calendario Estudiantil — `/api/calendario`
| Método | Ruta | Descripción |
|--------|------|-------------|
| GET | `/api/calendario` | Lista todos los eventos |
| GET | `/api/calendario/{id}` | Evento por ID |
| GET | `/api/calendario/proximos` | Eventos próximos |
| GET | `/api/calendario/rango?inicio=&fin=` | Eventos en un rango de fechas |
| POST | `/api/calendario` | Crea un evento |
| PUT | `/api/calendario/{id}` | Actualiza un evento |
| DELETE | `/api/calendario/{id}` | Elimina un evento |

---

## ▶️ Ejecución

```bash
mvnw.cmd spring-boot:run     # Windows
./mvnw spring-boot:run       # Linux / macOS
```

- Documentación Swagger: **http://localhost:8087/swagger-ui.html**

---

## 🔗 Relación con otros servicios

Valida el funcionario/publicador consultando **ms-usuarios** (`:8089`).
La URL de ms-usuarios se configura en `application.properties` (`ms.usuarios.base-url`).
