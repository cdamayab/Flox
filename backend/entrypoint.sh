#!/bin/bash

# Path to compiled jar
JAR_FILE=/app/build/libs/Flox-0.0.1-SNAPSHOT.jar 

# Without args
if [ "$RUN_MODE" == "dev" ]; then
    echo "Iniciando en modo desarrollo con bootRun..."
    ./gradlew bootRun --continuous -Dspring-boot.run.jvmArguments="-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
elif [ -f "$JAR_FILE" ]; then
    echo "Archivo $JAR_FILE encontrado. Iniciando aplicación..."
    java -jar "$JAR_FILE"
else
    echo "Archivo $JAR_FILE no encontrado."
    echo "Iniciando en modo producción, compilando JAR..."
    ./gradlew clean build --no-daemon
    java -jar "$JAR_FILE"
fi
