# ms-murales

Microservicio independiente del Sistema Escolar del Colegio Bernardo O'Higgins de Coquimbo, encargado de la gestion de Murales Digitales y del Calendario Estudiantil.

## Descripcion
ms-murales es un microservicio REST autonomo que permite publicar murales (comunicados y noticias) y administrar eventos del calendario escolar. Incluye validacion de funcionario contra ms-usuarios antes de crear o actualizar registros.

El servicio expone una API RESTful con operaciones CRUD para ambos recursos, siguiendo una separacion por capas: controller, services, repositories y models.

## Tecnologias Utilizadas

| Tecnologia | Version | Rol |
| --- | --- | --- |
| Java | 21 | Lenguaje de programacion |
| Spring Boot | 4.x | Framework principal |
| Maven | 3.x | Gestion de dependencias |
| MySQL | 8.x | Base de datos relacional |
| Spring Data JPA | Boot 4 | Persistencia con Hibernate |
| Lombok | Boot 4 | Reduccion de boilerplate |
| Jakarta Validation | Boot 4 | Validacion en DTOs |
| SpringDoc OpenAPI | 2.x | Documentacion Swagger |

## Estructura del Proyecto

```
ms-murales/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/sistemaEscolar/ms_murales/
│       │       ├── MsMuralesApplication.java
│       │       ├── config/
│       │       │   └── RestTemplateConfig.java
│       │       ├── controller/
│       │       │   ├── CalendarioEstudiantilController.java
│       │       │   └── MuralDigitalController.java
│       │       ├── models/
│       │       │   ├── dto/
│       │       │   │   ├── requests/
│       │       │   │   │   ├── CalendarioEstudiantilRequestDTO.java
│       │       │   │   │   └── MuralDigitalRequestDTO.java
│       │       │   │   └── response/
│       │       │   │       ├── CalendarioEstudiantilResponseDTO.java
│       │       │   │       └── MuralDigitalResponseDTO.java
│       │       │   ├── entities/
│       │       │   │   ├── CalendarioEstudiantil.java
│       │       │   │   └── MuralDigital.java
│       │       │   └── mappers/
│       │       │       ├── CalendarioEstudiantilMapper.java
│       │       │       └── MuralDigitalMapper.java
│       │       ├── repositories/
│       │       │   ├── CalendarioEstudiantilRepository.java
│       │       │   └── MuralDigitalRepository.java
│       │       └── services/
│       │           ├── CalendarioEstudiantilService.java
│       │           ├── MuralDigitalService.java
│       │           └── UsuarioClient.java
│       └── resources/
│           └── application.properties
└── pom.xml
```

## Modelo de Datos

### mural_digital
| Columna | Tipo | Descripcion |
| --- | --- | --- |
| id_publicacion | BIGINT (PK) | Identificador autoincremental |
| id_funcionario | BIGINT | Referencia a ms-usuarios |
| contenido | VARCHAR(1000) | Contenido del mural |
| nivel_alcance | VARCHAR(20) | INSTITUCION, CURSO, ASIGNATURA |
| fecha_publicacion | DATETIME | Fecha y hora de publicacion |

### calendario_estudiantil
| Columna | Tipo | Descripcion |
| --- | --- | --- |
| id_evento | BIGINT (PK) | Identificador autoincremental |
| id_funcionario | BIGINT | Referencia a ms-usuarios |
| titulo_evento | VARCHAR(150) | Titulo del evento |
| descrip_evento | VARCHAR(500) | Descripcion del evento |
| fecha_inicio | DATE | Fecha de inicio |
| fecha_fin | DATE | Fecha de termino |

Relacion logica:
Funcionario (ms-usuarios) 1:N MuralDigital
Funcionario (ms-usuarios) 1:N CalendarioEstudiantil

## Configuracion y Ejecucion

Requisitos:
- Java 21
- MySQL 8.x
- Maven 3.x o wrapper `mvnw`

Archivo: `src/main/resources/application.properties`

```
server.port=8083

spring.datasource.url=jdbc:mysql://localhost:3306/ms_murales_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=root

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

ms.usuarios.base-url=http://localhost:8080
```

Levantar el servicio:
```
./mvnw spring-boot:run
```

## Integracion con ms-usuarios
Se valida que `idFuncionario` exista y sea tipo funcionario (DOCENTE, DIRECTIVO, INSPECTOR) usando:

```
GET {ms.usuarios.base-url}/api/usuarios/{id}
```

Si ms-usuarios no esta disponible, las operaciones de creacion/actualizacion retornan 503.

## Endpoints Disponibles

### Murales - /api/murales
| Metodo | Endpoint | Descripcion |
| --- | --- | --- |
| GET | /api/murales | Lista todas las publicaciones |
| GET | /api/murales/{id} | Obtiene una publicacion por ID |
| GET | /api/murales/nivel/{nivelAlcance} | Filtra por nivel de alcance |
| POST | /api/murales | Crea una publicacion |
| PUT | /api/murales/{id} | Actualiza una publicacion |
| DELETE | /api/murales/{id} | Elimina una publicacion |

### Calendario - /api/calendario
| Metodo | Endpoint | Descripcion |
| --- | --- | --- |
| GET | /api/calendario | Lista todos los eventos |
| GET | /api/calendario/{id} | Obtiene un evento por ID |
| GET | /api/calendario/proximos | Lista eventos desde hoy |
| GET | /api/calendario/rango?desde=YYYY-MM-DD&hasta=YYYY-MM-DD | Lista eventos por rango |
| POST | /api/calendario | Crea un evento |
| PUT | /api/calendario/{id} | Actualiza un evento |
| DELETE | /api/calendario/{id} | Elimina un evento |

## Documentacion Swagger
http://localhost:8083/swagger-ui.html

## Ejemplos

Crear mural:
```
POST http://localhost:8083/api/murales
Content-Type: application/json

{
  "idFuncionario": 2,
  "contenido": "Comunicado institucional",
  "nivelAlcance": "INSTITUCION",
  "fechaPublicacion": "2026-05-18T12:00:00"
}
```

Crear evento calendario:
```
POST http://localhost:8083/api/calendario
Content-Type: application/json

{
  "idFuncionario": 2,
  "tituloEvento": "Dia del Estudiante",
  "descripEvento": "Acto y actividades",
  "fechaInicio": "2026-05-18",
  "fechaFin": "2026-05-18"
}
```

## Notas
- Las tablas se crean automaticamente con `spring.jpa.hibernate.ddl-auto=update`.
- `nivelAlcance` es un texto con valores sugeridos: INSTITUCION, CURSO, ASIGNATURA.
