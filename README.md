# Proyecto de Mensajería Express <img alt="Java" height="40" width="40" src="https://media.giphy.com/media/KeJXqoqlUE2NSHUYER/giphy.gif">

Este es un proyecto de mensajería express que utiliza Java y Spring para crear un sistema de seguimiento y gestión de envíos. El proyecto utiliza una base de datos MySQL con un modelo entidad-relación, y documentación en Swagger para especificar los endpoints de los microservicios. También utiliza el patrón de diseño DTO, pruebas unitarias con Mockito y JUnit, Lombok, y está dividido en microservicios para Cliente, Empleado y Envío. La integración continua se realiza con GitHub y el despliegue se realiza en Railway.

<div align="center" > <img alt="logo" height="100" width="400" align="center" src="https://user-images.githubusercontent.com/115324147/233528141-8f02ce04-0c5a-49c6-9c07-072e909bec20.png">
</div>

## Tecnologías utilizadas
- Java versión: 11
- Spring Framework
- Gestor de dependencias: Gradle
- Lombok
- Bases de datos: MySQL

## Configuración 
Antes de comenzar, asegúrese de tener una base de datos configurada y actualice las credenciales de la base de datos en el archivo **application.properties**.
```java
spring.datasource.url=jdbc:mysql://localhost:3306/proyectointegrador
spring.datasource.username=root
spring.datasource.password={password}
spring.jpa.hibernate.ddl-auto=update
server.port=8080
spring.mvc.pathmatch.matching-strategy=ant-path-matcher
```
## Documentación <img align="center" alt="Pruebas" height="40" width="40" src="https://avatars.githubusercontent.com/u/7658037?s=280&v=4">
La documentación de la API se genera automáticamente con Swagger. Para acceder a la documentación, abra un navegador web y vaya a [Documentación Swagger](https://proyecto-integrador-production-4956.up.railway.app/swagger-ui/index.html).

## Patrón de Diseño
Este proyecto utiliza el patrón de diseño DTO (Data Transfer Object) para transferir datos entre las diferentes capas de la aplicación. Los DTO son objetos simples que contienen campos y métodos de acceso, y se utilizan para transferir datos entre los controladores y los servicios.

## Autenticación y autorización seguridad <img align="center" alt="Pruebas" height="60" width="60" src="https://pbs.twimg.com/profile_images/1235983944463585281/AWCKLiJh_400x400.png">
Se utiliza Spring Security para la autenticación y autorización de los usuarios. Solo los usuarios autenticados tienen acceso a las funcionalidades de Envio.
Cada usuario tiene su rol como `WRITE` o `READ`

# Diagramas:
![DiagramaDeClases drawio (2)](https://user-images.githubusercontent.com/115324147/235147788-4df623aa-3200-4d38-b44f-0464df5979cb.png)

# Diagrama UML:
<img src="https://user-images.githubusercontent.com/115324147/236378315-f2af3316-a11b-4744-be9c-7cdd337f3721.png">
<p align="center"> Modelo de Clases UML <i>(Clic en la imagen para verla en mayor detalle)</i></p>

## Diagrama del Modelo Entidad-Relación <img align="center" alt="Pruebas" height="40" width="40" src="https://user-images.githubusercontent.com/115324147/233542530-c691174d-7f63-4ea9-8126-c3ecf520b2c2.png">
Este es el diagrama del modelo entidad-relación para la base de datos MySQL del proyecto de sistema de reservas:
![diagrama](https://user-images.githubusercontent.com/115324147/233475114-f72509af-a274-4fa1-9bab-b0669dbea10e.png)

- La tabla **Cliente** contiene información sobre los clientes, como su cedula, nombre, apellido, celular, ciudad, correo electronico y dirección
- La tabla **Empleado** contiene información sobre los empleados, como su cedula, nombre, apellido, celular, ciudad, correo electronico, dirección, antiguedad, tipo de sangre y tipo de empleado.
- La tabla **Envio** contiene información sobre los envios, como el numero de guia, ciudad de destino, ciudad origen, dirección destino, estado del envio, hora de entrega, nombre de la persona, numero de la persona, valor del envio.
- La tabla **Paquete** contiene información sobre los paquetes, como el id del paquete, peso, tipo de paquete y valor declarado.

## Diagrama del proyecto por paquetes

```java
com.example.Proyecto-Integrador
├── Configurer
│   └── SwaggerConfig.java
├── Controller
│   ├── ClienteController.java
│   ├── EmpleadoController.java
│   └── EnvioController.java
├── Dto
│   ├── ClienteDto.java
│   ├── EmpleadoDto.java
│   ├── EnvioDto.java
│   ├── EnvioDtoRequest.java
│   ├── EnvioDtoUpdate.java
│   └── PaqueteDto.java
├── Exception
│   ├── ApiExceptionHandler.java
│   └── ApiRequestException.java
├── Model
│   ├── Cliente.java
│   ├── Empleado.java
│   ├── Envio.java
│   ├── Paquete.java
│   └── Persona.java
├── Repository
│   ├── ClienteRepository.java
│   ├── HabitacionRepository.java
│   ├── ReservaRepository.java
│   └── PaqueteRepository.java
├── Segurity
│   └── WebSecurityConfig.java
├── Service
│   ├── ClienteService.java
│   ├── HabitacionService.java
│   └── EnvioService.java
└── ProyectoIntegradorApplication.java
```

El proyecto está organizado en cuatro paquetes principales, cada uno correspondiente a un microservicio:

- El paquete **Configurer** contiene las clases de configuración para la base de datos y Swagger.
- El paquete **Controller** contiene las clases controladoras para los microservicios de Cliente, Habitación y Envio.
- El paquete **Dto** contiene las clases DTO (Data Transfer Object) para los objetos Cliente, Habitación y Reserva, que se utilizan para transferir datos entre la capa de presentación y la capa de servicios.
- El paquete **Exception** contiene las clases de Excepciones (ApiExceptionHandler, ApiRequestException) para el manejo de errores.
- El paquete **Model** contiene las clases de entidades JPA (Java Persistence API) para los objetos Cliente, Empleado y Envio, que se utilizan para mapear las tablas de la base de datos.
- El paquete **Repository** contiene las interfaces de repositorios JPA para los objetos Cliente, Empleado y Envio, que se utilizan para interactuar con la base de datos.
- El paquete **Security** contiene la clase de seguridad que se utiliza para autenticación y autorización de seguridad
- El paquete **Service** contiene las clases de servicios para los microservicios de Cliente, Empleado y Envio, que contienen la lógica de negocio.
La clase HotelAshirApplication es la clase principal del proyecto que se utiliza para iniciar la aplicación.

Además, hay un paquete adicional llamado common que contiene clases y utilidades compartidas por los microservicios.

## Microservicios <img align="center" alt="microservicio" height="40" width="40" src="https://user-images.githubusercontent.com/115324147/233541782-7b18ad4a-54d2-4304-945c-db24491a886e.png">

Este proyecto está dividido en tres microservicios diferentes: Cliente, Habitación y Reserva. Cada microservicio tiene su propia base de datos y API REST. La comunicación entre los microservicios se realiza a través de peticiones HTTP.

#### Cliente Microservicio
| Método Http   | EndPoint      |Descripción   |
| ------------- | ------------- |------------- |
|`POST`         | ``(http://localhost:8080/api/v1/cliente)``|Crea un nuevo cliente|
|`PUT`          | ``(http://localhost:8080/api/v1/cliente)``|Actualizar datos del cliente|
|`DELETE`         | ``(http://localhost:8080/api/v1/cliente/123456)``  |Eliminar cliente por cédula|
|`GET`       | ``(http://localhost:8080/api/v1/cliente/123456)``  |Obtener cliente por cédula|

Endpoints:
- **POST /cliente** - Crea un nuevo cliente

Ejemplo de petición:

```java
{
"cedula": 123456,
"nombre":"Mateo",
"apellido":"Zapata",
"celular": 30463,
"correoElectronico" : "mateo@gmail.com",
"direccion" :"calle 46 # 69-90",
"ciudad" : "Medellin"
}
```

- **PUT /cliente** - Actualizar datos del cliente

Ejemplo de petición:

```java
{
"cedula": 123456,
"nombre":"Camilo",
"apellido":"Restrepo",
"celular": 30463,
"correoElectronico" : "camilor@gmail.com",
"direccion" :"calle 55 # 6-90",
"ciudad" : "Cucuta"
}
```

#### Empleado Microservicio
| Método Http   | EndPoint      |Descripción   |
| ------------- | ------------- |------------- |
|`POST`         | ``(http://localhost:8080/api/v1/empleado)``|Crea un nuevo empleado|
|`PUT`          | ``(http://localhost:8080/api/v1/empleado)``|Actualizar datos del empleado|
|`DELETE`         | ``(http://localhost:8080/api/v1/cliente/123456)``  |Eliminar cliente por cédula|
|`GET`       | ``(http://localhost:8080/api/v1/cliente/123456)``  |Obtener empleado por cédula|


Endpoints:
- **POST /empleado** - Crea un nuevo empleado

Ejemplo de petición:
```java
{
    "cedula": 12345,
    "nombre":"Mateo",
    "apellido":"Zapata",
    "celular": 30466,
    "correoElectronico" : "mateo@gmail.com",
    "direccion" :"calle 46 # 69-90",
    "ciudad" : "Medellin",
    "antiguedad" : 1,
    "tipoSangre" : "o+",
    "tipoEmpleado": "REPARTIDOR"
}
```

- **PUT /empleado** - Actualizar los datos de un empleado

Ejemplo de petición:

```java
{
    "cedula": 12345,
    "nombre":"camilo",
    "apellido":"restrepo",
    "celular": 30466,
    "correoElectronico" : "camilor@gmail.com",
    "direccion" :"calle 46 # 69-90",
    "ciudad" : "Medellin",
    "antiguedad" : 1,
    "tipoSangre" : "o+",
    "tipoEmpleado": "REPARTIDOR"
}
```

#### Envio Microservicio
| Método Http   | EndPoint      |Descripción   |
| ------------- | ------------- |------------- |
|`POST`         | ``(http://localhost:8080/api/v1/envio)``|Crea un nuevo envio|
|`PUT`          | ``(http://localhost:8080/api/v1/envio)``|Actualizar datos del envio|
|`GET`       | ``(http://localhost:8080/api/v1/cliente/123456)``  |Obtener envio por número de guia|
|`GET`          | ``(http://localhost:8080/api/v1/envio/?cedula=12345&estadoEnvio=RECIBIDO)``  |Obtener envios por estado|

Endpoints:
- **POST /envio** - Crea un nuevo envio
 
Ejemplo de petición:

```java
{
"cedula":123456,
"nombreRemitente": "Mateo",
"ciudadOrigen":"Medellin",
"ciudadDestino":"Bogota",
"direccionDestino": "calle 46 # 69-90",
"nombrePersona" : "Juan Manuel",
"numeroPersona" : 30463,
"peso": 2.0,
"valorDeclarado": 15000
}
```

- **PUT /envio** - Actualizar estado de envio

Ejemplo de petición:

```java
{
"numGuia": 40,
"estadoEnvio":"ENTREGADO",
"cedula": 12345
}
```
## Postman <img align="center" alt="Pruebas" height="40" width="40" src="https://uxwing.com/wp-content/themes/uxwing/download/brands-and-social-media/postman-icon.png">
Para probar los endpoints de la aplicación, se recomienda el uso de la herramienta Postman. Esta herramienta permite hacer peticiones HTTP a la API y recibir las respuestas en tiempo real. 

![233542888-3c3ecc17-9b61-4d65-a786-6b85f3ae77b5](https://user-images.githubusercontent.com/115324147/235146943-bd0944ef-2306-42fc-a9f3-1372aadef85e.png)

## Pruebas Unitarias <img align="center" alt="Pruebas" height="40" width="40" src="https://media.giphy.com/media/1sMGC0XjA1Hk58wppo/giphy.gif">
Se han incluido pruebas unitarias utilizando Mockito y JUnit para asegurar que los microservicios de Cliente, Habitación y Reserva funcionan correctamente.
Las pruebas unitarias se encuentran en la carpeta src/test/java del proyecto.

## Integración continua con GitHub Actions <img align="center" alt="Integracion" height="50" width="50" src="https://media.giphy.com/media/Vnk8f29XU6GSZK8uGJ/giphy.gif">
Este proyecto cuenta con integración continua mediante Github Actions. Cada vez que se realiza un push al repositorio, se ejecutan las pruebas unitarias y se crea un archivo JAR ejecutable.

## Despliegue del microservicio (Railway) <img align="center" alt="Depliegue" height="40" width="40" src="https://media.giphy.com/media/tzv65Sc3tBQWNMSI3B/giphy.gif">
Este proyecto cuenta con un despliegue del microservicio mediante Railway. Conecta directamente con Github y nuestra base de datos(MySQL).
