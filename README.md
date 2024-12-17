> # ACERCA
> este es un proyecto desarrollado por Diego Ipurre como parte de un desafio de AgendaPro
>
## Arquitectura
* Arquitectura Hexagonal
* Generacion del contrato de la API mediante OpenApi (first design)
* Event driven
* Programación reactiva
## Stack
* Java 17
* Spring 3.4
* Spring WebFlux
* Spring Security
* Spring Data R2DBC
* Postgres
* JUnit 5
## Documentación de la API
OpenAPI [http://localhost:8085/webjars/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html) mediante Swagger.
## Seguridad
La API esta securizada por **Basic Auth**.  
Las credenciales son:
- **Usuario**: agendapro
- **Password**: agendapro
## Ejecutar la Aplicación
Puedes ejecutar la aplicación con Docker de la siguiente manera, esto en la raiz del proyecto:

- **ejecutar la aplicacion**: `docker compose up`
- **apagar la aplicacion**: `docker compose down`

## Pruebas unitarias
`./mvnw test`

## OpenAPI
Puedes autogenerar las clases mediante el contrato de la API con el comando
`./mvnw clean compile`

## Postman
en la raiz del proyecto puedes encontrar la colección postman para probar la API