FROM maven:3.8.5-openjdk-17-slim AS build
COPY src /src
COPY pom.xml ./pom.xml
RUN mvn -f pom.xml clean package

FROM openjdk:17-jdk-slim
WORKDIR api
COPY --from=build target/*.jar api.jar
ENTRYPOINT ["java", "-jar", "api.jar"]
EXPOSE 8080