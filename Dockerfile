FROM gradle:8.12-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

FROM openjdk:21
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]