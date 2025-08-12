# pocPermBus

Este proyecto es una prueba de concepto de un _permission bus_ para Android. Consta de varias aplicaciones:

- **appa**: expone un servicio que inspecciona su manifiesto para detectar los permisos solicitados y, si alguno falta, permite lanzar la actividad para concederlo. Incluye una actividad principal sencilla para iniciar la app directamente.
- **appb**: segunda aplicación de ejemplo con un servicio equivalente.
- **launcher**: cliente que se conecta a los servicios y ofrece una interfaz simple para solicitar los permisos desde otra app.

## Instalación desde Maven

1. Añade el repositorio y la dependencia en tu `build.gradle`:
   ```gradle
   repositories {
       mavenCentral()
   }
   dependencies {
       implementation "com.example:permissionflow:1.0.0"
   }
   ```
2. Sincroniza el proyecto y ya podrás utilizar el servicio.

## API pública

### Conexión al servicio

1. Crea un `Intent` con la acción `com.example.permissionflow.BIND_PERMISSION_PORTAL`.
2. Especifica el `package` de la app que expone el servicio (por ejemplo, `com.example.appa`).
3. Realiza el `bindService` y obtén una instancia de `IPermissionPortal`.
4. Invoca `getPendingPermissions()` para obtener la lista de permisos pendientes.

### Formato de `PermInfo`

Cada elemento devuelto por `getPendingPermissions()` contiene:

- `permName`: nombre del permiso pendiente.
- `uiLabel`: etiqueta que puede mostrarse al usuario.
- `fixIntentUri`: `Intent` serializado en formato URI para lanzar `PermissionFixActivity`.

### `PermissionFixActivity`

Para solicitar un permiso faltante, parsea el `fixIntentUri` y lanza la actividad:
```kotlin
val fixIntent = Intent.parseUri(info.fixIntentUri, 0)
startActivity(fixIntent)
```
La actividad mostrará el diálogo de permisos y terminará automáticamente al completarse.

## Ejemplo de integración

```kotlin
class MainActivity : AppCompatActivity() {
    private var portal: IPermissionPortal? = null

    override fun onStart() {
        super.onStart()
        val intent = Intent("com.example.permissionflow.BIND_PERMISSION_PORTAL")
            .setPackage("com.example.appa")
        bindService(intent, object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                portal = IPermissionPortal.Stub.asInterface(service)
            }
            override fun onServiceDisconnected(name: ComponentName?) { portal = null }
        }, BIND_AUTO_CREATE)
    }

    fun fixFirstPermission() {
        val first = portal?.pendingPermissions?.firstOrNull() ?: return
        val fixIntent = Intent.parseUri(first.fixIntentUri, 0)
        startActivity(fixIntent)
    }
}
```

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

## Contribuciones

Las contribuciones son bienvenidas. Para proponer cambios:

1. Haz un fork del repositorio y crea una rama descriptiva.
2. Realiza tus modificaciones siguiendo el estilo existente.
3. Abre un *pull request* con una descripción clara de los cambios.

## Licencia

Este proyecto se distribuye bajo la licencia MIT. Consulta el archivo [LICENSE](LICENSE) para más detalles.

