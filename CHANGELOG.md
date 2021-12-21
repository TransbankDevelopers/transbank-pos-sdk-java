# Changelog
Todos los cambios notables a este proyecto serán documentados en este archivo.

El formato está basado en [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
y este proyecto adhiere a [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

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