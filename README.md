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
