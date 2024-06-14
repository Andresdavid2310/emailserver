# Dockerfile
FROM adoptopenjdk:17-jdk-hotspot

WORKDIR /app

# Copiar el JAR de la aplicación
COPY target/emailserver-0.0.1-SNAPSHOT.jar app.jar

# Puerto expuesto por la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación al iniciar el contenedor
ENTRYPOINT ["java", "-jar", "app.jar"]
