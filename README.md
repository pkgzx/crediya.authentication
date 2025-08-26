# CrediYa Authentication

Proyecto base para autenticación implementando **Clean Architecture** en Java. Este repositorio sirve como ejemplo y punto de partida para aplicaciones empresariales desacopladas, escalables y mantenibles.

## Tabla de Contenidos

- [Introducción](#introducción)
- [Características](#características)
- [Arquitectura](#arquitectura)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Requisitos](#requisitos)
- [Instalación](#instalación)
- [Ejecución](#ejecución)
- [Pruebas](#pruebas)
- [Contribución](#contribución)
- [Licencia](#licencia)
- [Referencias](#referencias)

## Introducción

Este proyecto implementa una arquitectura limpia para el módulo de autenticación de CrediYa, siguiendo principios de separación de responsabilidades y desacoplamiento. El objetivo es facilitar el mantenimiento y la escalabilidad, permitiendo la integración de diferentes tecnologías y adaptadores sin afectar el núcleo de negocio.

## Características

- Separación clara entre dominio, casos de uso, infraestructura y aplicación.
- Fácil integración de nuevos adaptadores (REST, bases de datos, etc.).
- Pruebas unitarias y de integración.
- Configuración automática de dependencias.
- Ejemplo de patrones como Unit of Work y Repository.

## Arquitectura

El proyecto sigue los principios de [Clean Architecture](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a):

![Clean Architecture](https://miro.medium.com/max/1400/1*ZdlHz8B0-qu9Y-QO3AXR_w.png)

- **Domain:** Lógica y reglas de negocio.
- **Usecases:** Orquestación de flujos y lógica de aplicación.
- **Infrastructure:** Adaptadores externos y utilidades.
- **Application:** Ensamblaje, configuración y arranque.

## Estructura del Proyecto

```
authentication/
├── domain/         # Modelos y entidades del negocio
├── usecases/       # Casos de uso y lógica de aplicación
├── infrastructure/ # Adaptadores, helpers y entry points
├── application/    # Configuración y arranque
└── README.md
```

### Domain

Encapsula la lógica y reglas del negocio mediante modelos y entidades.

### Usecases

Implementa los casos de uso, define la lógica de aplicación y orquesta los flujos.

### Infrastructure

- **Helpers:** Utilidades generales para adaptadores y entry points.
- **Driven Adapters:** Implementaciones externas (REST, bases de datos, etc.).
- **Entry Points:** Puntos de entrada de la aplicación.

### Application

Ensamblaje de módulos, resolución de dependencias y arranque de la aplicación.

## Requisitos

- Java 11+
- Gradle 7+
- (Opcional) Docker para despliegue

## Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/pkgzx/crediya.authentication.git
   cd CrediYa-authentication
   ```
2. Compila el proyecto:
   ```bash
   ./gradlew build
   ```

## Ejecución

Para iniciar la aplicación:

```bash
./gradlew bootRun
```

La función principal se encuentra en el módulo `application` y se encarga de iniciar todos los componentes.

## Pruebas

Ejecuta las pruebas unitarias y de integración con:

```bash
./gradlew test
```

## Contribución

Las contribuciones son bienvenidas. Por favor, abre un issue o envía un pull request siguiendo las buenas prácticas de Clean Architecture.

## Licencia

Este proyecto está bajo la licencia de Proposito General de  GNU.

## Referencias

- [Clean Architecture — Aislando los detalles](https://medium.com/bancolombia-tech/clean-architecture-aislando-los-detalles-4f9530f35d7a)
- [Unit of Work y Repository Pattern](https://medium.com/@krzychukosobudzki/repository-design-pattern-bc490b256006)

---
