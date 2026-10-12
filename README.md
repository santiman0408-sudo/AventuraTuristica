# AventuraTurística

Aplicación móvil nativa para Android desarrollada con Kotlin y Android Studio, orientada a la gestión de reservas de una empresa de turismo de aventura. Permite registrar clientes y viajes, consultar reservas, actualizar sus estados y visualizar puntos de encuentro mediante Google Maps.

## 1. Objetivo del proyecto

Digitalizar el proceso de registro y seguimiento de reservas turísticas, reduciendo la pérdida de información y facilitando la consulta de destinos y ubicaciones.

## 2. Funcionalidades

- Pantalla Splash o bienvenida.
- Inicio de sesión con validaciones visuales.
- Pantalla principal con acceso al registro y listado de reservas.
- Registro de clientes y reservas turísticas.
- Listado de reservas mediante RecyclerView.
- Visualización del detalle de cada reserva.
- Actualización del estado de la reserva.
- Integración con Google Maps y marcadores de ubicación.
- Solicitud de permisos de ubicación del dispositivo.
- Almacenamiento local de reservas mediante SQLite.
- Interfaz con componentes de Material Design.

### Estados de las reservas

- `PENDIENTE`
- `CONFIRMADO`
- `FINALIZADO`

## 3. Tecnologías utilizadas

| Tecnología | Uso |
|---|---|
| Kotlin | Lenguaje de programación |
| Android Studio | Entorno de desarrollo |
| XML | Diseño de interfaces |
| Material Design | Componentes visuales |
| RecyclerView | Listado de reservas |
| SQLite | Almacenamiento local |
| Google Maps SDK for Android | Mapas y marcadores |
| Gradle Kotlin DSL | Configuración y compilación |
| Git y GitHub | Control de versiones |

## 4. Datos de una reserva

Cada reserva contiene los siguientes campos:

- `id`: identificador de la reserva.
- `clienteNombre`: nombre completo del cliente.
- `telefono`: número de contacto.
- `destino`: destino turístico seleccionado.
- `fechaViaje`: fecha del viaje.
- `cantidadPersonas`: número de participantes.
- `estadoReserva`: estado actual.
- `puntoEncuentro`: descripción del lugar de reunión.
- `latitud`: coordenada geográfica de latitud.
- `longitud`: coordenada geográfica de longitud.

## 5. Requisitos

- Windows, Linux o macOS compatible con Android Studio.
- Android Studio con soporte para proyectos Kotlin.
- JDK compatible con la versión de Gradle del proyecto.
- Android SDK y las herramientas de compilación requeridas.
- Android Emulator o un dispositivo Android físico.
- Conexión a Internet para descargar dependencias y cargar Google Maps.
- Proyecto de Google Cloud con Maps SDK for Android habilitado.
- Clave de API de Google Maps configurada localmente.

## 6. Instalación

1. Clonar el repositorio de GitHub o descargar el proyecto.
2. Abrir la carpeta `AventuraTuristica` desde Android Studio.
3. Esperar a que Gradle sincronice las dependencias.
4. Configurar la clave de Google Maps localmente.
5. Seleccionar un emulador o conectar un dispositivo Android.
6. Compilar el proyecto.

### Configuración de Google Maps

Crear el archivo `secrets.properties` en la raíz del proyecto, al mismo nivel que `settings.gradle.kts`.

Agregar la siguiente propiedad, sustituyendo el valor de ejemplo por la clave de API correspondiente:

```properties
MAPS_API_KEY=TU_CLAVE_REAL_DE_GOOGLE_MAPS
```

No publicar la clave de API ni incluir el archivo `secrets.properties` en el repositorio. Configurar las restricciones de la clave para la aplicación Android y habilitar Maps SDK for Android en Google Cloud.

## 7. Ejecución

1. Abrir el proyecto en Android Studio.
2. Seleccionar el módulo `app` y un dispositivo de prueba.
3. Ejecutar la aplicación mediante **Run**.
4. Esperar a que se muestre la pantalla Splash.
5. Iniciar sesión con las credenciales de demostración.
6. Acceder a la pantalla principal y seleccionar la funcionalidad deseada.

### Credenciales académicas de demostración

- Correo: `admin@aventuraturistica.com`
- Contraseña: `Aventura123`

Estas credenciales son fijas y están destinadas únicamente a pruebas académicas. El inicio de sesión actual no constituye un sistema de autenticación para producción.

## 8. Flujo de prueba

1. Abrir la aplicación y comprobar la pantalla Splash.
2. Introducir credenciales incorrectas y verificar el mensaje de validación.
3. Iniciar sesión con las credenciales de demostración.
4. Registrar una reserva con datos de prueba.
5. Consultar la reserva en el listado.
6. Abrir su detalle y comprobar la información registrada.
7. Cambiar el estado de la reserva.
8. Abrir Google Maps desde el detalle.
9. Verificar el marcador y las coordenadas del punto de encuentro.
10. Probar el permiso de ubicación del dispositivo.

Se recomienda utilizar datos ficticios durante las pruebas y comprobar que las coordenadas correspondan al punto de encuentro esperado.

## 9. Estructura del proyecto

```text
AventuraTuristica/
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com/example/aventuraturistica/
│           │       ├── activities/
│           │       ├── adapters/
│           │       ├── data/
│           │       ├── utils/
│           │       └── MainActivity.kt
│           ├── res/
│           │   ├── layout/
│           │   ├── mipmap/
│           │   └── values/
│           └── AndroidManifest.xml
├── .gitignore
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## 10. Consideraciones

- Las reservas se almacenan localmente en SQLite.
- La aplicación necesita conexión a Internet para descargar el contenido de Google Maps.
- La ubicación del dispositivo requiere los permisos correspondientes.
- La clave de Google Maps debe configurarse de manera independiente en cada entorno.
- Las coordenadas de prueba deben verificarse antes de emplearse en una actividad turística real.

## 11. Control de versiones

El proyecto se gestiona con Git y GitHub. Los cambios deben registrarse mediante commits descriptivos, evitando subir claves de API, archivos locales de configuración o credenciales privadas.

## 12. Control de versiones

El proyecto AventuraTurística ha sido desarrollado en equipo como parte de la formación académica en Desarrollo de Aplicaciones Móviles.

N.º Apellidos y nombres

1   Maria Alejandra Siri Vergara

2   Jean Paul Villasante Contreras

3   Adrian Daniel Santisteban Manrique

Información del proyecto

Nombre del proyecto: AventuraTurística

Plataforma: Android

Lenguaje de programación: Kotlin

Entorno de desarrollo: Android Studio

Base de datos local: SQLite

Integración de mapas: Google Maps SDK for Android

Propósito: Gestionar reservas turísticas, consultar información de los viajes y visualizar los puntos de encuentro mediante mapas.