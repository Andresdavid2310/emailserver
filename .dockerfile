# Usa una imagen base de Amazon Corretto 22
FROM amazoncorretto:22.0.1

WORKDIR /app

COPY target/*.jar app.jar

ENV DB_HOST=$DB_HOST
ENV DB_PORT=$DB_PORT
ENV DB_NAME=$DB_NAME
ENV DB_USERNAME=$DB_USERNAME
ENV DB_PASSWORD=$DB_PASSWORD

EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
