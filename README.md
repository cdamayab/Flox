# Prueba Técnica Backend - Flox
Flexible Online Exchange

## Descripción

Este proyecto es una plataforma de comercio electrónico simplificada. Consiste en dos servicios clave:

1. **Product Catalog Service**: Gestión de productos (CRUD completo).
2. **Order Management Service**: Gestión de órdenes (CRUD completo).

Ambos servicios están desarrollados en **Java** usando **Spring Boot**, con documentación generada en **Swagger**. El proyecto está completamente dockerizado para facilitar la ejecución.

## Requisitos

- **Docker** y **Docker Compose** instalados.
- Puerto **8090** disponible en el sistema.

## Instrucciones para ejecutar el proyecto

1. **Clonar el repositorio**

   ```bash
   git clone https://github.com/cdamayab/Flox.git
   cd Flox
   ```

2. **Levantar los contenedores con Docker**

   ```bash
   docker-compose up 
   ```

   Esto levantará los servicios de backend y la base de datos MySQL.

3. **Compilar el proyecto dentro del contenedor**

   ```bash
   docker exec flox-app /entrypoint.sh build
   ```

4. **Iniciar el servicio**

   ```bash
   docker exec flox-app /entrypoint.sh serve
   ```

5. **Acceder a la documentación Swagger**

   Visitar la siguiente URL para visualizar la documentacion de los endpoints y enidades:

   ```
   http://localhost:8090/swagger-ui/index.html
   ```

## Servicios Implementados

### Product Catalog Service
- Gestión completa de productos.
- Operaciones CRUD: creación, lectura, actualización y eliminación.
- Validaciones para los datos de los productos.
- Documentación detallada en Swagger.

### Order Management Service
- Gestión completa de órdenes.
- CRUD con soporte para múltiples estados de orden.
- Relación con productos y cálculo automático del precio total.