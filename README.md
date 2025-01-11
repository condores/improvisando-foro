# ChallengeAlura-ForoHub

![Version](https://img.shields.io/badge/version-1.0-blue.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.1-green.svg)
![Java](https://img.shields.io/badge/Java-21-orange.svg)

ForoHub es una plataforma de foros desarrollada como parte del desafío de Alura Latam. Esta aplicación proporciona un espacio para que los desarrolladores discutan diversos temas, compartan conocimientos y resuelvan dudas en un ambiente colaborativo.

## 👨‍💻 Desarrollador

Adrian Daconte

- 🐱 GitHub: [@Adrian-Daconte](https://github.com/Adrian-Daconte)
- 💼 LinkedIn: [Adrian Daconte](https://www.linkedin.com/in/adrian-daconte/)

## 🎥 Demostración del Proyecto

### Video Demostración

Puedes ver el video de demostración aquí:

[![Video Demostración](https://img.youtube.com/vi/XWIvtwVLG_4/0.jpg)](https://youtu.be/XWIvtwVLG_4)

### 🌐 Demostración en Vivo

Puedes ver una demostración en vivo de la aplicación aquí:

[🔗 https://challengealura-forohub.onrender.com/topics/public/search_all](https://challengealura-forohub.onrender.com/topics/public/search_all)
Este enlace muestra todos los tópicos pre-cargados en la base de datos, demostrando la funcionalidad básica del foro.

> [!NOTE]
> ⏳ La aplicación está alojada en un servidor gratuito. El primer acceso puede tardar hasta 1-2 minutos mientras el servidor se inicia. Por favor, sea paciente.

Además, puedes explorar todos los endpoints de la aplicación a través de la interfaz de Swagger aquí:

[📘 https://challengealura-forohub.onrender.com/swagger-ui.html](https://challengealura-forohub.onrender.com/swagger-ui.html)

## 🌟 Características Principales

### Seguridad y Autenticación

- 👤 Autenticación y autorización de usuarios
- 🔒 Implementación de JWT (JSON Web Tokens) para autenticación
- 🔑 Uso de BCryptPasswordEncoder para encriptar contraseñas
- 🔐 Gestión de roles y permisos de usuarios
- 🔄 Arquitectura STATELESS para mayor escalabilidad

### Funcionalidades del Foro

- 📝 Creación y gestión de tópicos de discusión
- 💬 Sistema de comentarios en tópicos
- 🏷️ Uso de enums para categorías definidas dentro de las entidades

### 🛠 API y Manejo de Datos

- Endpoints específicos según roles de usuario
- Respuestas API personalizadas (DataResponse y ApiResponse)
- Manejo de excepciones y validaciones de entrada
- Gestión automática de fechas de creación/actualización (utilizando @PrePersist y @PreUpdate de JPA)

### Documentación

- 📚 Swagger para documentación de endpoints

## 🏗 ForoHub: Estructura del Proyecto

ForoHub está diseñado siguiendo una arquitectura modular

```plaintext
src
└── main
    └── java
        └── com.dako.forohub
            ├── authentication
            │   ├── controller
            │   ├── dto
            │   └── service
            ├── comment
            │   ├── controller
            │   ├── dto
            │   ├── repository
            │   └── service
            ├── course
            │   ├── domain
            │   └── repository
            ├── infra
            │   ├── config
            │   ├── docs
            │   ├── exceptions
            │   ├── responses
            │   └── security
            ├── topic
            │   ├── controllers
            │   ├── domain
            │   ├── dtos
            │   ├── repository
            │   └── services
            └── user
                ├── controller
                ├── domain
                ├── dtos
                ├── repositories
                └── service
```

Esta estructura refleja una arquitectura modular y bien organizada:

· 🔐 **authentication**: Maneja la autenticación de usuarios.
· 💬 **comment**: Gestiona la funcionalidad de comentarios.
· 📚 **course**: Contiene la lógica relacionada con los cursos.
· 🛠️ **infra**: Incluye configuraciones, documentación, manejo de excepciones, respuestas personalizadas y configuración de seguridad.
· 📝 **topic**: Gestiona los tópicos del foro.
· 👤 **user**: Maneja la información y lógica relacionada con los usuarios.

Cada módulo principal (authentication, comment, topic, user) sigue una estructura similar:
· 🎮 **controller**: Maneja las solicitudes HTTP.
· 📦 **dto**: Objetos de Transferencia de Datos para la comunicación entre capas.
· 🏛️ **domainl**: Entidades y modelos de dominio.
· 🗄️ **repository**: Interfaces para el acceso a datos.
· ⚙️ **service**: Lógica de negocio.

Esta organización facilita el mantenimiento, la escalabilidad y la separación de responsabilidades en el proyecto. 🚀

## 🛠 Tecnologías Utilizadas

- Java 21
- Spring Boot 3.4.1
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven
- Flyway para migraciones de base de datos
- Spring Boot Validation
- Lombok
- Auth0 java-jwt (versión 4.4.0)
- SpringDoc OpenAPI (para documentación de API, versión 2.8.1)
- Spring Boot DevTools
- Docker

## 📥 Instalación y Configuración

1. **Clonar el Repositorio:**
   - Clona el repositorio de ForoHub desde GitHub
   - Navega al directorio del proyecto clonado

2. **Configurar Variables de Entorno:**
   - Crea un archivo `.env` en la raíz del proyecto si no existe
   - Añade las siguientes variables (ajusta los valores según sea necesario):

     ```properties
     # Desarrollo
     POSTGRES_USER_DEV=root
     DB_DEV=dev
     PASSWORD_DB_DEV=123456

     # Producción
     POSTGRES_USER_PROD=root
     DB_PROD=prod
     PASSWORD_DB_PROD=123456

     # Pruebas
     POSTGRES_USER_TEST=root
     DB_TEST=test
     PASSWORD_DB_TEST=123456

     # Token Api secret
     API_TOKEN_SECRET=123456789

     # DataBase Real de produccion 
     DATABASE_URL=jdbc:postgresql://localhost:5434/${DB_PROD}
     ```

3. **Iniciar Servicios con Docker Compose:**
   Utiliza el mismo archivo Docker Compose para todos los entornos, especificando el perfil adecuado:

   - Para desarrollo:
     Usa el comando Docker Compose con --profile dev

   - Para producción:
     Usa el comando Docker Compose con --profile prod

   - Para pruebas:
     Usa el comando Docker Compose con --profile test

4. **Ejecutar la Aplicación:**

5. **Verificar la Aplicación:**
