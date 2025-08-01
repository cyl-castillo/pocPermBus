# pocPermBus

Este proyecto es una prueba de concepto de un _permission bus_ para Android. Consta de varias aplicaciones:

  - **appa**: expone un servicio que inspecciona su manifiesto para detectar los permisos solicitados y, si alguno falta, permite lanzar la actividad para concederlo. Incluye una actividad principal sencilla para iniciar la app directamente.
  - **appb**: segunda aplicación de ejemplo con un servicio equivalente.
  - **launcher**: cliente que se conecta a los servicios y ofrece una interfaz simple para solicitar los permisos desde otra app.

## Pasos para probar

1. Asegúrate de tener instalado el [Android SDK](https://developer.android.com/tools) y que las herramientas de `adb` y `gradle` estén en tu `PATH`.
2. Clona este repositorio y entra al directorio raíz del proyecto.
3. Compila e instala cada módulo en un dispositivo o emulador conectado:
   ```
   gradle :appa:installDebug
   gradle :appb:installDebug
   gradle :launcher:installDebug
   ```
4. Ejecuta la aplicación **LauncherPoC** en el dispositivo. Detectará los permisos que faltan en **appa** y mostrará un botón para corregirlos.
5. Opcionalmente puedes abrir la app **appa** desde el lanzador para ver una pantalla de prueba.

Con esto podrás verificar el funcionamiento básico del _permission bus_.

