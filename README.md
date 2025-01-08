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
   Para modo de producción:
   ```bash
   docker-compose up
   ```
   Esto compilará y levantará automáticamente los servicios de backend y la base de datos MySQL.

   Para modo de desarrollo:
   ```bash
   RUN_MODE=dev docker-compose up
   ```
   Esto permitirá trabajar en modo desarrollo con `bootRun`.

3. **Comandos auxiliares para compilación y desarrollo**

   Para compilar el proyecto dentro del contenedor:
   ```bash
   docker exec flox-app ./gradlew build
   ```

   Para ejecutar la aplicación en modo desarrollo dentro del contenedor:
   ```bash
   docker exec flox-app ./gradlew bootRun
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

### Excel Report Service
- Endpoint: `/api/reports/products`
- Descarga de informes en formato Excel con los productos actuales.

## Ejemplos de Uso

**Crear un Producto**
```http
POST http://localhost:8090/api/products
Content-Type: application/json

{
    "name": "parlantes",
    "description": "Parlantes estéreo de alta calidad",
    "stock": 20,
    "price": 250000.00,
    "supplier": "LogyTech.",
    "category": "Electrónica"
}
```

**Registrar un usuario**
```
curl -X POST http://localhost:8090/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "userName": "testuser2",
    "lastName": "user2",
    "email": "testuser2@example.com",
    "password": "testuser2",
    "userStatus": "active",
    "role": "USER"
  }'
```

**Autenticacion**
```
curl -X POST http://localhost:8090/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username": "testuser", "password": "testuser"}'
```

# Ejemplos de uso con autorización

    Se debe realizar el proceso de autenticación de manera previa para obtener el token jwt y reemplazarlo en la solicitud correspondiente.

**Crear una orden**
```
curl -X POST http://localhost:8090/api/orders \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTczNTg4NTA4NCwiZXhwIjoxNzM1ODkyMjg0fQ.3Ectw7TPZx1Cpppnx_EW1_imR6IRr6gysivbjgFa2Rw" \
     -d '{
           "customerId": 3,
           "totalPrice": 1250000.0,
           "status": "PENDING",
           "orderItems": [
             {
               "productId": 4,
               "quantity": 1,
               "unitPrice": 700000.0,
               "totalPrice": 700000.0
             },
             {
               "productId": 5,
               "quantity": 1,
               "unitPrice": 550000.0,
               "totalPrice": 550000.0
             }
           ]
         }'
```

**Listar ordenes**
```
curl -X GET "http://localhost:8090/api/orders?status=PENDING&customerId=3" \
     -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0dXNlciIsImlhdCI6MTczNTg4NTA4NCwiZXhwIjoxNzM1ODkyMjg0fQ.3Ectw7TPZx1Cpppnx_EW1_imR6IRr6gysivbjgFa2Rw"
```