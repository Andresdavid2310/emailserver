# Email Service API

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)

## Descripción

Esta es una API RESTFUL para un servicio de correo electrónico, construida con Spring Boot y desplegada en Heroku.

Para el desarrollo de esta aplicación se utilizó GitHubActions para CI/CD desplegando la aplicación en heroku, 
análisis de código estático con sonarcloud y pruebas unitarias con mockito

## Tecnologías Utilizadas

- Java 17
- Spring Boot
- Maven
- Heroku
- Lombok
- Spring Security
- JWT
- Flyway
- MapStruct
- PostgreSQL
- Swagger
- Postman
- SonarCloud
- GitHub Actions
- Mockito
- Junit

## Requisitos Previos

- JDK 17
- Maven
- Git

## Construcción y Despliegue

### Paso 1: Clonar el Repositorio

```sh
git clone https://github.com/tuusuario/emailservice.git
```

### Paso 2: Iniciar la aplicación:

Se debe iniciar la aplicación teniendo en cuenta el arhcivo **application-dev.yml**
donde se encuentra toda la configuración necesaria para ejecutar en el local

También es importante tener corriendo una instancia de **postgress**  en el puerto 5432

### Paso 3: Revisar la documentación de Swagger:

Puedes revisar la documentación de la Api en swagger para conocer la estructuras de las peticiones

Local
``` go
http://localhost:8080/swagger-ui/index.html
```

Heroku
``` go
https://serveremail-85ccc82553bb.herokuapp.com/swagger-ui/index.html#/autenticacion-controller/login
```
### Paso 4: Descargar la colleción de postman:

Puedes descargar e importar la colección de postman con los request para consumir la api.

Para poder ejecutar la api se debe: <br>
- Consumir el endpoint **/login** enviando usuario y contraseña para generar un token jwt
- Este token se debe enviar en el encabezado autorization para que pueda ser validado por la Api
- Se agregó un usuario en la bd por defecto que permite hacer el login.

### SonarCloud

Puedes acceder a ver el análisis de codigo estatico en sonarcloud en el siguiente enlace:
``` go
https://sonarcloud.io/project/overview?id=Andresdavid2310_emailserver
```

Collection Postman
Puedes importar la coleccion de postman desde el archivo **EmailServerPostman_collection.json**
## Contacto:
**Andres Benavides**<br>
Email: **dandavi2310@hotmail.com**