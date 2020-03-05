#	Transbank POS SDK Java

## Requisitos para compilar

Este SDK utiliza Java 1.8. Para compilar se debe tener el JDK 1.8 y Apache Maven 3.X

Para compilar basta con el target package:

<code>mvn package</code> 

Esto generará un jar bajo el directorio target con el codigo de la librería.

### Variable de ambiente

Para utilizar el SDK del POS es necesario el archivo Transbank.dll, o Transbank.dylib del SDK de C. Esta es la librería nativa del POS integrado de Transbank, y la versión de Java la utiliza.

Para que la libreria de Java pueda encontrar la librería nativa, utiliza una variable de ambiente llamada **NATIVE_TRANSBANK_WRAP** que debe apuntar al archivo de esta variable.

Por ejemplo en MacOS se debe correr el comando export en el mismo Shell en que se ejecutará el programa que utiliza la librería.

<code>export NATIVE_TRANSBANK_WRAP=/usr/local/lib/libTransbankWrap.dylib</code>

en Windows, se debe correr este comando en la ventana de Command.com antes de ejecutar el programa que utiliza la librería.

<code>set NATIVE_TRANSBANK_WRAP=/usr/local/lib/libTransbankWrap.dylib</code>

Mas probablemente, se incluya esta variable en algun script que lanzará el programa.



## Documentación 

Puedes encontrar toda la documentación de cómo usar este SDK en el sitio https://www.transbankdevelopers.cl.

La documentación relevante para usar este SDK es:

- Documentación general sobre los productos y sus diferencias:
  [Webpay](https://www.transbankdevelopers.cl/producto/webpay) y
  [Onepay](https://www.transbankdevelopers.cl/producto/onepay).
- Documentación sobre [ambientes, deberes del comercio, puesta en producción,
  etc](https://www.transbankdevelopers.cl/documentacion/como_empezar#ambientes).
- Primeros pasos con [Webpay](https://www.transbankdevelopers.cl/documentacion/webpay) y [Onepay](https://www.transbankdevelopers.cl/documentacion/onepay).
- Referencia detallada sobre [Webpay](https://www.transbankdevelopers.cl/referencia/webpay) y [Onepay](https://www.transbankdevelopers.cl/referencia/onepay).

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

## Generar una nueva versión (con deploy automático a NuGet)

Para generar una nueva versión, se debe crear un PR (con un título "Prepare release X.Y.Z" con los valores que correspondan para `X`, `Y` y `Z`). Se debe seguir el estándar semver para determinar si se incrementa el valor de `X` (si hay cambios no retrocompatibles), `Y` (para mejoras retrocompatibles) o `Z` (si sólo hubo correcciones a bugs).

En ese PR deben incluirse los siguientes cambios:

1. Modificar el archivo `CHANGELOG.md` para incluir una nueva entrada (al comienzo) para `X.Y.Z` que explique en español los cambios **de cara al usuario del SDK**.
2. Modificar `pom.xml` para que <`Version`> sea `X.Y.{Z+1}` (de manera que los pre-releases que se generen después del release sean de la siguiente versión).

Luego de obtener aprobación del pull request, debe mezclarse a master e inmediatamente generar un release en GitHub con el tag `vX.Y.Z`. En la descripción del release debes poner lo mismo que agregaste al changelog.

