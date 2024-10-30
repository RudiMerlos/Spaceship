# Spaceship

API Rest desarrollada en Java 21 y SpringBoot 3.3.5 que permite un mantenimiento CRUD de naves espaciales de series y películas en una base
de datos H2 en memoria.

### Objetivos desarrollados

- Consultar todas las naves utilizando paginación.
- Consultar una única nave por id.
- Consultar todas las naves que contienen, en su nombre, el valor de un parámetro enviado en la petición.
- Crear una nueva nave.
- Modificar una nave existente.
- Eliminar una nave existente.
- Gestión centralizada de excepciones.
- Desarrollo de un @Aspect que añade una línea de log cuando se busca una nave con ID negativo.
- Test unitario.
- Utilizar cachés de algún tipo (caffeine).
- Utilizar alguna librería que facilite el mantenimiento de los scripts DDL de base de datos (flyway).
- Seguridad de la API.
- Documentación de la API (swagger).
- Test de integración.
- Presentar la aplicación dockerizada.