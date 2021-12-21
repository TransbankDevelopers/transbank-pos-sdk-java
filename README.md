[![Build Status](https://travis-ci.org/TransbankDevelopers/transbank-pos-sdk-java.svg?branch=master)](https://travis-ci.org/TransbankDevelopers/transbank-pos-sdk-java)
[![Quality Gate](https://sonarcloud.io/api/project_badges/measure?project=com.github.transbankdevelopers%3Atransbank-sdk-pos-java&metric=alert_status)](https://sonarcloud.io/dashboard?id=com.github.transbankdevelopers%3Atransbank-sdk-pos-java)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.transbankdevelopers/transbank-sdk-pos-java/badge.svg?style=plastic)](https://search.maven.org/artifact/com.github.transbankdevelopers/transbank-sdk-pos-java/1.0.2/jar)

#	Transbank POS SDK Java

## Requisitos para compilar

Este SDK utiliza Java 1.8. Para compilar se debe tener el JDK 1.8 y Apache Maven 3.X

Para compilar basta con el target package:

<code>mvn package</code> 

Esto generará un jar bajo el directorio target con el código de la librería.

### Desarrollo

Para ejecutar localmente el proyecto y probarlo junto al [repositorio de ejemplo](https://github.com/TransbankDevelopers/transbank-pos-sdk-java-example) debe ejecutar las siguientes líneas.

Remplace &lt;DIR&gt; por el directorio donde clono el repositorio y &lt;VERSION&gt; por el que corresponda a la versión clonada según sea el caso, donde &lt;VERSION&gt; dependerá de lo especificado en la etiqueta version del archivo pom.xml, busque la línea con el código similar a &lt;version&gt;X.X.X-SNAPSHOT&lt;/version&gt;.

```sh
mvn compile && mvn package && mvn install:install-file \
   -Dfile=/<DIR>/transbank-pos-sdk-java/target/transbank-sdk-pos-java-<VERSION>.jar \
   -DgroupId=com.github.transbankdevelopers \
   -DartifactId=transbank-sdk-pos-java \
   -Dversion=<VERSION> \
   -Dpackaging=jar \
   -DgeneratePom=true
```

## Documentación 

Puedes encontrar toda la documentación de cómo usar este SDK en el sitio https://www.transbankdevelopers.cl.

La documentación relevante para usar este SDK es:

- Documentación general sobre los productos y sus diferencias:
  [POSIntegrado](https://www.transbankdevelopers.cl/producto/posintegrado)
- Primeros pasos con [POSIntegrado](https://www.transbankdevelopers.cl/documentacion/posintegrado).
- Primeros pasos con [POSAutoservicio](https://transbankdevelopers.cl/documentacion/pos-autoservicio).
- Referencia detallada sobre [POSIntegrado](https://www.transbankdevelopers.cl/referencia/posintegrado).

## Información para contribuir y desarrollar este SDK

### Standares

- Para los commits respetamos las siguientes normas: https://chris.beams.io/posts/git-commit/
- Usamos ingles, para los mensajes de commit.
- Se pueden usar tokens como WIP, en el subject de un commit, separando el token con `:`, por ejemplo:
`WIP: This is a useful commit message`
- Para los nombres de ramas también usamos ingles.
- Se asume, que una rama de feature no mezclada, es un feature no terminado.
- El nombre de las ramas va en minúsculas.
- Las palabras se separan con `-`.
- Las ramas comienzan con alguno de los short lead tokens definidos, por ejemplo: `feat/tokens-configuration`

#### Short lead tokens
##### Commits
- WIP = Trabajo en progreso.
##### Ramas
- feat = Nuevos features
- chore = Tareas, que no son visibles al usuario.
- bug = Resolución de bugs.

### Todas las mezclas a master se hacen mediante Pull Request.

## Generar una nueva versión (con deploy automático a Maven)

Para generar una nueva versión, se debe crear un PR (con un título "Prepare release X.Y.Z" con los valores que correspondan para `X`, `Y` y `Z`). Se debe seguir el estándar semver para determinar si se incrementa el valor de `X` (si hay cambios no retrocompatibles), `Y` (para mejoras retrocompatibles) o `Z` (si sólo hubo correcciones a bugs).

En ese PR deben incluirse los siguientes cambios:

1. Modificar el archivo `CHANGELOG.md` para incluir una nueva entrada (al comienzo) para `X.Y.Z` que explique en español los cambios **de cara al usuario del SDK**.
2. Modificar `pom.xml` para que <`Version`> sea `X.Y.{Z+1}` (de manera que los pre-releases que se generen después del release sean de la siguiente versión).

Luego de obtener aprobación del pull request, debe mezclarse a master e inmediatamente generar un release en GitHub con el tag `vX.Y.Z`. En la descripción del release debes poner lo mismo que agregaste al changelog.
