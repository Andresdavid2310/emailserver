# Etapa 1: Compila el proyecto Maven
FROM maven:3.8.4-openjdk-17 AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia los archivos de tu proyecto al directorio de trabajo
COPY . /app

# Ejecuta Maven para compilar el proyecto
RUN mvn clean package

# Etapa 2: Ejecuta la aplicación en una imagen base separada
FROM amazoncorretto:17.0.7-alpine

# Exponer el puerto que utilizará la aplicación (si es necesario)
# EXPOSE 8080

# Establece el directorio de trabajo (opcional)
WORKDIR /app

# Copia el archivo JAR construido desde la etapa de compilación
COPY --from=build /app/target/emailserver-0.0.1-SNAPSHOT.jar /app/emailserver-0.0.1-SNAPSHOT.jar

# Establece el punto de entrada para ejecutar la aplicación
CMD ["java", "-jar", "emailserver-0.0.1-SNAPSHOT.jar"]