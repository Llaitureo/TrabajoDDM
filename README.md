# Pastelería Mil Sabores 🧁

Proyecto de aplicación móvil Android para la gestión y compra de productos de pastelería, desarrollado para la asignatura de Desarrollo de Aplicaciones Móviles (DSY1105).

## 👥 Integrantes del Equipo
* **[Fernanda LLaitureo]**
* **[Monzerrat Huarapil]**
* **[Cristian Huinca]**
* **[Benjamin valdebenito]**

## 📱 Funcionalidades
La aplicación cuenta con las siguientes características implementadas:

### Gestión de Usuarios
* **Inicio de Sesión y Registro:** Validación de credenciales y creación de nuevos usuarios con reglas de negocio (ej. validación de contraseñas).
* **Perfil de Usuario:** Visualización de datos personales y descuentos activos (Edad, Cupones, Cumpleaños).

### Catálogo y Compras
* **Catálogo de Productos:** Listado visual de productos disponibles (Tortas, Cupcakes, Donas, etc.).
* **Detalle de Producto:** Vista individual con imagen, precio y selector de cantidad.
* **Carrito de Compras (Boleta):** Gestión de pedidos, cálculo de subtotales y aplicación automática de descuentos.
* **Historial de Compras:** Registro persistente de las órdenes realizadas anteriormente.

### Integraciones y Herramientas
* **Geolocalización (Mapa):** Visualización de la ubicación de la tienda utilizando **Mapbox SDK**.
* **Escáner QR:** Funcionalidad para escanear códigos QR utilizando **CameraX** y **ML Kit**, integrada en el flujo de la app.
* **Persistencia de Datos:** Uso de **Room Database** para el almacenamiento local de usuarios, productos, carrito e historial.

## 🌐 Endpoints y Servicios

### APIs Externas
* **Mapbox SDK:** Utilizado para el despliegue de mapas y marcadores de tiendas.
  * *Requiere Token de acceso configurado en `build.gradle` o `gradle.properties`.*

## 🛠️ Instrucciones de Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [URL_DE_TU_REPOSITORIO]


## 📖 Manual de Uso de la Aplicación

### 1. Inicio de Sesión y Registro
* **Registro:** Si eres un usuario nuevo, selecciona la opción "¿No tienes cuenta? Regístrate aquí" en la pantalla de inicio. Completa el formulario con tu nombre de usuario, contraseña, fecha de nacimiento y código promocional (opcional).
* **Login:** Ingresa tus credenciales en la pantalla principal.
    * *Credencial de prueba (Admin):* Usuario `admin` / Contraseña `123`.

### 2. Navegación Principal (Home)
Una vez dentro, verás el **Catálogo de Productos** con imágenes y precios. En la barra superior encontrarás los accesos directos a las funciones principales:
* 🗺️ **Mapa:** Icono de mapa a la izquierda (Visualiza la ubicación de la tienda).
* 📷 **Escáner QR:** Icono de código QR (Para escanear códigos de productos o promociones).
* 👤 **Perfil:** Icono de persona (Ver tus datos y descuentos activos).
* 🛒 **Carrito:** Icono de boleta larga (Ver tus productos seleccionados).
* 📜 **Historial:** Icono de recibo (Ver tus compras anteriores).

### 3. Cómo Realizar una Compra
1. **Seleccionar Producto:** Toca cualquier producto del catálogo (ej. "Torta de Chocolate") para ver su detalle.
2. **Agregar al Carrito:** En la pantalla de detalle, usa los botones `+` y `-` para definir la cantidad y presiona "Confirmar Pedido".
3. **Revisar Carrito:** Ve al icono del carrito (🛒). Aquí podrás:
    * Aumentar o disminuir cantidades de cada ítem.
    * Eliminar productos individuales (Icono de basura rojo).
    * Vaciar todo el carrito (Icono de basura en la barra superior).
4. **Pagar:** Presiona el botón "Pagar ahora". Se mostrará un cuadro de confirmación con el total. Al confirmar, la orden se guardará en tu historial.

### 4. Funciones Adicionales
* **Mapa de Tiendas:** Utiliza Mapbox para mostrarte la ubicación exacta de la pastelería.
* **Perfil y Descuentos:** En la sección "Mi Perfil" podrás ver si tienes descuentos aplicados automáticamente por edad (50%), por código promocional o por cumpleaños.
* **Escáner QR:** Al presionar el icono, se abrirá la cámara para escanear códigos QR relevantes para la tienda.
