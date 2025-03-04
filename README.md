 ## 📌 Sobre este Proyecto

Este proyecto comenzó como una **actividad de la UTAD** en la que se implementaba la arquitectura **MVVM** junto con una base de datos local. Sin embargo, decidí **expandirlo** para comparar diferentes opciones de bases de datos locales y mejorar la gestión de datos en la app.

Ahora, el proyecto soporta múltiples implementaciones, incluyendo:
- **Room Database**
- **DataStore Preferences**
- **Firebase**
- **Shared Preferences**
- **Paper DB**
  

Pelix es una aplicación de películas donde puedes:
✔ Explorar películas organizadas por categorías.
✔ Realizar búsquedas específicas.
✔ Almacenar tus películas favoritas.
✔ Crear y gestionar tu perfil.
✔ Autenticarse con Firebase Authentication.



📂 Sobre este repositorio------------------------------------------------------------------------
Este repositorio implementa diferentes bases de datos locales, permitiendo probar la arquitectura 
MVVM y entender cómo se gestionan los datos en cada una de ellas.

Además, la aplicación utiliza APIs externas como TMDB e implementa herramientas como:

    - Retrofit (para la conexión con la API).
    - Koin (para la inyección de dependencias).
    - Firebase Authentication (para el inicio de sesión de los usuarios).

🌿 Estructura del Proyecto-----------------------------------------------------------------------
El proyecto está dividido en varias ramas, cada una representando una implementación diferente de 
la base de datos local utilizada.

- La rama que usa Firebase está identificada con firebase-ksp.

- Cada rama contiene un archivo libs.versions.toml con las dependencias necesarias para esa
- versión.


🔐 Autenticación con Firebase--------------------------------------------------------------------
Los usuarios pueden autenticarse en Pelix utilizando Firebase Authentication.
Para configurar Firebase en el proyecto, sigue estos pasos:

1. Crear un proyecto en Firebase siguiendo todas sus instruciones.
2. Habilitar métodos de autenticación en Firebase
3. En la consola de Firebase, ve a Authentication > Métodos de Inicio de Sesión.
4. Activa los métodos que quieras usar (correo/contraseña, Google, etc.).



🛠 Cómo clonar el repositorio--------------------------------------------------------------------
- Opción 1: Clonar una rama específica

Si deseas clonar solo una rama en particular, sigue estos pasos en Android Studio:

1️) Abre la terminal en Android Studio o usa una terminal en tu PC.
2️) Ejecuta el siguiente comando, reemplazando nombre-de-la-rama con la rama que quieres clonar:

git clone -b nombre-de-la-rama --single-branch https://github.com/tu-usuario/tu-repo.git


🛠 Opción 2: Clonar el repositorio completo------------------------------------------------------
Si prefieres descargar todas las ramas, usa:

git clone https://github.com/tu-usuario/tu-repo.git
 
Luego para cambiar de rama :
git checkout nombre-de-la-rama


Próximos pasos------------------------------------------------------------------------------------
Después de clonar el repositorio, abre el proyecto en Android Studio, agrega el 
google-services.json, y sincroniza las dependencias.

