# Pastelería Mil Sabores 🧁

Proyecto de aplicación móvil Android para la gestión y compra de productos de pastelería, desarrollado para la asignatura de Desarrollo de Aplicaciones Móviles (DSY1105).

## 👥 Integrantes del Equipo
* **[Fernanda LLaitureo]**
* **[Monzerrat]**
* **[Cristian Huenca]**
* **[Benjamin valdebenito]**

## 📱 Funcionalidades
La aplicación cuenta con las siguientes características implementadas:

### Gestión de Usuarios
* [cite_start]**Inicio de Sesión y Registro:** Validación de credenciales y creación de nuevos usuarios con reglas de negocio (ej. validación de contraseñas)[cite: 23, 22].
* [cite_start]**Perfil de Usuario:** Visualización de datos personales y descuentos activos (Edad, Cupones, Cumpleaños)[cite: 23].

### Catálogo y Compras
* [cite_start]**Catálogo de Productos:** Listado visual de productos disponibles (Tortas, Cupcakes, Donas, etc.)[cite: 24].
* [cite_start]**Detalle de Producto:** Vista individual con imagen, precio y selector de cantidad[cite: 24].
* [cite_start]**Carrito de Compras (Boleta):** Gestión de pedidos, cálculo de subtotales y aplicación automática de descuentos[cite: 24].
* [cite_start]**Historial de Compras:** Registro persistente de las órdenes realizadas anteriormente[cite: 23].

### Integraciones y Herramientas
* [cite_start]**Geolocalización (Mapa):** Visualización de la ubicación de la tienda utilizando **Mapbox SDK**[cite: 24].
* [cite_start]**Escáner QR:** Funcionalidad para escanear códigos QR utilizando **CameraX** y **ML Kit**, integrada en el flujo de la app[cite: 24].
* [cite_start]**Persistencia de Datos:** Uso de **Room Database** para el almacenamiento local de usuarios, productos, carrito e historial[cite: 22, 23].

## 🌐 Endpoints y Servicios

### APIs Externas
* [cite_start]**Mapbox SDK:** Utilizado para el despliegue de mapas y marcadores de tiendas[cite: 24].
  * *Requiere Token de acceso configurado en `build.gradle` o `gradle.properties`.*

## 🛠️ Instrucciones de Ejecución

1. **Clonar el repositorio:**
   ```bash
   git clone [URL_DE_TU_REPOSITORIO]
