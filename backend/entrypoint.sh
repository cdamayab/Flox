#!/bin/bash

# Path to compiled jar
JAR_FILE=/app/build/libs/Flox-0.0.1-SNAPSHOT.jar 

if [ "$1" == "build" ]; then
    echo "Compilando la aplicación..."
    ./gradlew build --no-daemon
    echo "Compilación completada."
    exit 0
elif [ "$1" == "serve" ]; then
    if [ -f "$JAR_FILE" ]; then
        echo "Archivo $JAR_FILE encontrado. Iniciando aplicación..."
        java -jar "$JAR_FILE"
    else
        echo "Archivo $JAR_FILE no encontrado. Por favor compile la aplicación usando 'build'."
        exit 1
    fi
else
    # Without args
    if [ "$RUN_MODE" == "dev" ]; then
        echo "Iniciando en modo desarrollo con bootRun..."
        ./gradlew bootRun --no-daemon
    elif [ -f "$JAR_FILE" ]; then
        echo "Archivo $JAR_FILE encontrado. Iniciando aplicación..."
        java -jar "$JAR_FILE"
    else
        echo "Archivo $JAR_FILE no encontrado. Contenedor creado, listo para construir la aplicación."
        tail -f /dev/null
    fi
fi
