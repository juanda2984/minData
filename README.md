# minData SuperHeroesAPI
Este proyecto es una API de Spring Boot que proporciona operaciones CRUD para gestionar superhéroes.
## Autor

- **Nombre del Autor:** Juan David Sanchez Loaiza
- **Correo Electrónico:** juanda2984@gmail.com

## Características

- Consulta todos los superhéroes
- Consulta un único superhéroe por ID
- Consulta superhéroes que contienen un valor en su nombre
- Crea un superhéroe
- Modifica un superhéroe
- Elimina un superhéroe

## Tecnologías Utilizadas

- Java
- Spring Boot
- H2 Database (base de datos en memoria)
- Maven

## Ejecución del Proyecto

### Requisitos Previos

- Java JDK 21
- Maven
- Docker

### Pasos

1. Clona el repositorio: `git clone https://github.com/juanda2984/minData.git`
2. Navega al directorio del proyecto: `cd minData`
3. Ejecuta la aplicación: `./mvnw spring-boot:run`

La aplicación estará disponible en `http://localhost:8080`.

## Configuración Adicional (Opcional)

### Base de Datos

La aplicación utiliza una base de datos H2 en memoria por defecto. Puedes acceder a la consola de la base de datos en `http://localhost:8080/h2-console`. Las credenciales por defecto son configuradas en `application.properties`.

### Docker

Si prefieres ejecutar la aplicación en un contenedor Docker:

1. Construccion de la imagen: `./mvn clean package dockerfile:build`
2. Ejecución del contenedor: `docker run -p 8080:8080 min_data`

### Ejemplos de Solicitudes

#### Consultar todos los súper héroes

```bash
curl http://localhost:8080/superHeroes
```
*Más información en la documentacion de los servicios en la url*
```bash
curl http://localhost:8080/swagger-ui/index.html#/
```
