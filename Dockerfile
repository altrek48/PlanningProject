FROM gradle:8.12-jdk21 AS build
WORKDIR /app
COPY gradlew .
COPY gradle/wrapper/gradle-wrapper.jar gradle/wrapper/
COPY gradle/wrapper/gradle-wrapper.properties gradle/wrapper/
COPY build.gradle settings.gradle ./
COPY src ./src
RUN chmod +x gradlew

RUN ./gradlew clean build -x test

FROM openjdk:21
EXPOSE 8080
WORKDIR /app
COPY --from=build /app/build/libs/PlanningProject-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]