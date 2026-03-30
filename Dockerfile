# Etapa 1: Construcción (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar el archivo de configuración de Maven y descargar dependencias (optimiza el caché)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el código fuente y compilar el proyecto
COPY src ./src
RUN mvn clean package

# Etapa 2: Ejecución (Runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiar solo el archivo JAR desde la etapa anterior
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto por defecto de Spring Boot
EXPOSE 8080

# Comando para iniciar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]