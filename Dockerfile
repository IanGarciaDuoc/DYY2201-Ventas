####### BUILD STAGE ###########

# Utiliza una imagen base oficial de OpenJDK para el JDK de Java 20
FROM openjdk:21-jdk-slim as build

# Instala Maven
RUN apt-get update && \
    apt-get install -y maven

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo pom.xml en el directorio de trabajo
COPY pom.xml ./

# Descarga y cachea las dependencias del proyecto
RUN mvn dependency:go-offline -B

# Copia el código fuente del proyecto al directorio de trabajo
COPY src ./src

# Compila el proyecto
RUN mvn clean package

####### END BUILD STAGE ###########



####### RUNTIME STAGE ###########

# Utiliza una imagen base oficial de OpenJDK para el JRE de Java 20
FROM openjdk:21-jdk-slim as runtime

# Establece el directorio de trabajo en /app
WORKDIR /app

# Copia el archivo JAR generado en la etapa de compilación en el directorio de trabajo
COPY --from=build /app/target/*.jar app.jar

# Expone el puerto 8080 para que la aplicación sea accesible externamente
EXPOSE 8080

# Ejecuta la aplicación Spring Boot
CMD ["java", "-jar", "app.jar"]

####### END RUNTIME STAGE ###########