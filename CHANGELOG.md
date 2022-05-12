# Changelog
Todos los cambios notables a este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
y este proyecto adhiere a [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [3.1.1] - 2022-05-12
### Fixed
- Se añade descarte del buffer del puerto serial antes de una escritura o cuando se produce un timeout. `El desarrollado de la librería de comunicaciones serial acota que la funcionalidad para limpiar el buffer puede no funcionar en algunos dispositivos conversores USB-Serial`.

## [3.1.0] - 2022-03-03
### Added
- Se agrega método para detectar si el puerto se encuentra abierto 

### Fixed
- Se soluciona un problema que no permitía detectar todos los mensajes intermedios de forma correcta.

## [3.0.0] - 2021-23-12
### Fixed
- Se soluciona problema que impedía finalizar el hilo principal cuando se producía una excepción por timeout.
- Se soluciona problema con el método `toString()` de algunas respuestas que mostraba valores incorrectos en los campos.
- Se arregla error al utilizar mensajes intermedios y no asignar el evento para escucharlos.
- Se soluciona problema con respuestas incompletas.
- Se arregla problema que marcaba la propiedad `success` en `false` cuando se ejecutaba el comando de respuesta de inicialización del POS Autoservicio.
- Se añade parámetro faltante en el método `multiCodeLastSale` de la clase `POSIntegrado`.

## [2.0.0] - 2021-21-12
Se reimplementa el SDK para que utilice la librería jSerialComm para el manejo del puerto serial.

### Added
- Se añade soporte para multi-código
- Se añade soporte para mensajes intermedios
- Se añade soporte para POS Autoservicio

### Removed
- Se elimina el uso de la librería en C.

## [1.0.2] - 2020-28-05
### Fix
- Fix KeysResponse (#5) (https://github.com/TransbankDevelopers/transbank-pos-sdk-java/pull/5)
- Fix version log4j to 1.2.17 [#6] (https://github.com/TransbankDevelopers/transbank-pos-sdk-java/pull/6)
- Add information about local build for development[#7](https://github.com/TransbankDevelopers/transbank-pos-sdk-java/pull/7)

## [1.0.0] - 2020-17-03
### Added
- Release inicial.