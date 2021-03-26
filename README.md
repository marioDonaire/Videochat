# videochat

Servidor hecho en java y Spring y la parte cliente en javascript, el cual tiene: chats privados, chat generales y llamadas de video para la asignatura de Tecnologías y sistemas web.


# Comenzando 🚀


### Pre-requisitos 📋

Añadir en el archivo application.propeties la direccion de la base de datos junto a los datos del usuario y en el archivo Email la cuenta con sus credenciales que quieras usar.


### Instalación 🔧

Ejecuta el archivo lanzadora.java y se te descargarán las dependencias con Maven.

## Pruebas ⚙️

_Hay 2 tipos de pruebas implementadas: una para la inserción de datos en la base de datos de usuarios con Jmeter y otra de funcionamiento del servidor y cliente con Selenium_

### Prueba de inserción de usuarios en la base de datos 🔩

_Esta prueba consiste en comprobar que una cantidad de usuarios considerable se crean un usuario en nuestro servidor para así comprobar su funcionamiento con un flujo alto de demandas_

Para la realizacion de esta prueba se necesita:

```
Tener descargado Jmeter, abrir el archivo  Registro20.jmx en la carpeta Jmeter.
Una vez abierto hay que añadir la ruta csv con los datos de los usuarios, el archivo DatosRegistro.txt 
es un ejemplo de la estructura que tiene que tener el csv.
```

Para simular las fotos se han obtenido de la web https://thispersondoesnotexist.com/image y se ha usado el archivo Conversora.java en la carpeta src/test/java/edu/uclm/esi/videochat para pasar las fotos a base64


### Prueba de funcionamiento de videollamada ⌨️

_Esta prueba consiste en verificar que las videollamadas de efectúan correctamente_

Para la realización de esta prueba se necesita:

```
Tener el servidor corriendo, añadir la ruta donde se encuntra nuestro webdriver Selenium 
con Google Chrome en el archivo TestRegistro.java en la carpeta src/test/java/edu/uclm/esi/videochat y ejecutarlo.
```
Esta prueba nos abrirá varios navegadores que realizarán las pruebas,para las pruebas se necesitan que haya 3 usuarios creados llamados: Ana,Pepe y Lucas con las contraseñas: Ana123, Pepe123 y Lucas123 respectivamente.

## Construido con 🛠️

* Java - Parte del servidor
* Javascript,CSS y HTML - Parte del cliente
* [MySQL](https://www.mysql.com/) - Base de datos
* [Selenium](https://www.selenium.dev/) - Usado para la prueba del servidor y cliente
* [Apache JMeter](https://jmeter.apache.org/) - Usado para la prueba de insercción de datos
* [Spring](https://spring.io/) - Framework usado para el servidor
* [Maven](https://maven.apache.org/) - Manejador de dependencias


## Autores ✒️

* **Mario Donaire Becerra** - *Cocreador* - [marioDonaire](https://github.com/marioDonaire)
* **Jose Luis Puebla Mero** - *Cocreador* - [JoseLuisP11](https://github.com/JoseLuisP11)
* **Fernando Torres Palomares** - *Cocreador* - [FernandoTorresp](https://github.com/FernandoTorresp)

## Licencia 📄

Este proyecto está bajo la Licencia CC - mira el archivo [LICENSE.md](LICENSE.md) para detalles
